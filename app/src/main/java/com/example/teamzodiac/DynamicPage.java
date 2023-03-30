package com.example.teamzodiac;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class DynamicPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_page);
        String a = getIntent().getStringExtra("route").toString();
        Toast.makeText(this,"In "+ a + " Page", Toast.LENGTH_SHORT).show();
        Resources res = getResources();
        String[] details = {};
        Integer im = 0;
        if(new String(a).equals(new String("a"))) {
            details = res.getStringArray(R.array.a);
            im = R.drawable.a;
        }
        if(new String(a).equals(new String("s"))) {
            details = res.getStringArray(R.array.s);
            im = R.drawable.s;
        }
        if(new String(a).equals(new String("v"))) {
            details = res.getStringArray(R.array.v);
            im = R.drawable.v;
        }
        for(String s:details){
            Log.d("myTag", s);
        }
        Log.d("myTag", "HELLLLO");
        Log.d("myTag", a);
        TextView pn = (TextView) findViewById(R.id.profileName);
        TextView pc = (TextView) findViewById(R.id.profileContent);
        TextView pr = (TextView) findViewById(R.id.profileRole);
        ImageView iv = (ImageView) findViewById(R.id.profilePic);
        pn.setText(details[0]);
        pr.setText(details[1]);
        pc.setText(getString(R.string.lorem));
        iv.setImageResource(im);
    }
}

