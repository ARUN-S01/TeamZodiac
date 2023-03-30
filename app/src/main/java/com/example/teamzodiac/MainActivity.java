package com.example.teamzodiac;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.teamzodiac.RecMembers.Members;
import com.example.teamzodiac.RecMembers.MembersAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ShimmerFrameLayout shimmerFrameLayout;
    public RecyclerView recyclerView;
    public MembersAdapter membersAdapter;
    public ArrayList<Members> members;
    String[] route = {"v","a","s"};
    ProgressDialog progressDoalog;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerFrameLayout = findViewById(R.id.ShimmerLayoutChat);
        recyclerView = findViewById(R.id.Recycle);
        registerForContextMenu(recyclerView);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        AddData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.top_menu, menu);
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.AboutUs:
                Intent intent = new Intent(MainActivity.this,TeamPage.class);
                startActivity(intent);
                break;
            case R.id.TeamDetails:
                int maxVotes = -1;
                String maxVotedPerson = "v";
                for (int i = 0; i < route.length; i++) {
                    Log.d("Votes",getVotes(route[i])  + " "+maxVotes);
                    int currentVote = getVotes(route[i]);
                    if(currentVote > maxVotes) {
                        maxVotes = currentVote;
                        maxVotedPerson = route[i];
                    }
                }
                Intent i = new Intent(MainActivity.this,StarOfTheWeek.class);
                i.putExtra("person",maxVotedPerson);
                startActivity(i);
                break;
            case R.id.Register:
                Intent intent_r = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent_r);
                break;
            case R.id.Help:
                break;
            case R.id.Location:
                getLocation();
                break;
        }

        return true;
    }

    @Override
    public void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }


    public void updateUi(){
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        membersAdapter = new MembersAdapter((androidx.recyclerview.widget.RecyclerView) recyclerView, members,MainActivity.this);
        recyclerView.setAdapter(membersAdapter);
    }

    public void AddData(){
        members = new ArrayList<>();
        members.add(new Members("Shreeram","+916385635056",R.drawable.s,"Leader"));
        members.add(new Members("Vigneswer Raja","+919842563253",R.drawable.v,"Human Resource"));
        members.add(new Members("Arun","+918122230060",R.drawable.a,"Developer"));

        updateUi();
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Call_menu:
                run_Dialog();
                break;
            case R.id.message_menu:
                issueNotification();
                Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void issueNotification() {
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            makeNotificationChannel(
                    "CHANNEL_1", "Example channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }
        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(this,
                "CHANNEL_1");

        notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Request")
                .setContentText("There is a message request")
                .setNumber(3);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    // Helper method to create a notification channel for
    // Android 8.0+
    private void makeNotificationChannel(String channelId,
                                         String channelName,
                                         int importance)
    {
        NotificationChannel channel
                = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
            channelId, channelName, importance);
            NotificationManager notificationManager
                    = (NotificationManager)getSystemService(
                    NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(
                    channel);
        }
    }

    public void run_Dialog(){

        final int[] votes = {0};

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Creating Call Request...");
        progressDoalog.setTitle("Verifying the Details...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();
        Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDoalog.incrementProgressBy(1);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDoalog.getProgress() <= progressDoalog
                            .getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDoalog.getProgress() == progressDoalog
                                .getMax()) {
                            progressDoalog.dismiss();

                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public int getVotes(String person){
        int votes = 0;
        Context context = getApplicationContext();
        String filename = person;

        StringBuilder stringBuilder = new StringBuilder();
        try  {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (Exception e) {
            Log.e("Eroor",e.getMessage());
        } finally {
            String contents = stringBuilder.toString();
            votes = Integer.parseInt(contents.trim());
        }

        return votes;
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Toast.makeText(MainActivity.this, "Latitude :" + latitude +"\n" +
                    "Longitude :" + longitude, Toast.LENGTH_SHORT).show();
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}

