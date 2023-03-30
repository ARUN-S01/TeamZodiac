package com.example.teamzodiac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StarOfTheWeek extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_of_the_week);
        String a = getIntent().getStringExtra("person").toString();
        Toast.makeText(this, getIntent().getStringExtra("person"), Toast.LENGTH_SHORT).show();
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
        TextView pr = (TextView) findViewById(R.id.profileRole);
        ImageView iv = (ImageView) findViewById(R.id.profilePic);
        TextView pn = (TextView) findViewById(R.id.profileName);
        pn.setText(details[0]);
        pr.setText(details[1]);
        iv.setImageResource(im);
    }
}
