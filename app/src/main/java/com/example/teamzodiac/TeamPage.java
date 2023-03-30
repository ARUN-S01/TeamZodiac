package com.example.teamzodiac;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
public class TeamPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);
        int[] ibArray = {R.id.ib1,R.id.ib2,R.id.ib3};
        int[] bArray = {R.id.vv,R.id.va,R.id.vs};
        String[] route = {"v","a","s"};
        for (int i = 0; i< ibArray.length;i++) {
            ImageButton ib = (ImageButton)findViewById(ibArray[i]);
            int finalI = i;
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TeamPage.this, route[finalI], Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(TeamPage.this,DynamicPage.class);
                    in.putExtra("route",route[finalI]);
                    startActivity(in);
                }
            });
            Button b = (Button) findViewById(bArray[i]);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkFiles()) createFiles();
                    checkFiles();
                    incrementVote(route[finalI]);
                }
            });
        }
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFiles();
                Toast.makeText(TeamPage.this, "Reseted All Votes", Toast.LENGTH_SHORT).show();
            }
        });
        Button star = (Button) findViewById(R.id.starOfWeek);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent i = new Intent(TeamPage.this,StarOfTheWeek.class);
                i.putExtra("person",maxVotedPerson);
                startActivity(i);
            }
        });
    }
    public int getVotes(String person){
        Context context = getApplicationContext();
        String filename = person;
        int votes = 0;
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
    public void createFiles(){
        Context context = getApplicationContext();
        for(String s:  new String[] {"v","a","s"}){
            try (FileOutputStream fos = context.openFileOutput(s, Context.MODE_PRIVATE)) {
                fos.write("0".getBytes());
                Log.d("Content","Created File");
            }catch(Exception e){
                Log.e("Error",e.getMessage());
            }
        }
    }
    public boolean checkFiles(){
        Context context = getApplicationContext();
        String files[] = context.fileList();
        for(String s:files) {
            Log.d("Files",s);
        }
        Log.d("contect","In here");
        return files.length>3;
    }
    public void incrementVote(String person){
        Context context = getApplicationContext();
        int votes = getVotes(person);
        String fileContents = "" + ++votes;
        try (FileOutputStream fos = context.openFileOutput(person, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
            Log.d("Content","Current vote for " + person + " : " + fileContents);
            Toast.makeText(context, "Current vote for " + person + " : " + fileContents, Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Log.e("Eroor","ee");
        }
    }
}

