package com.example.teamzodiac;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class DashBoard extends AppCompatActivity {
    @Override
    public void onCreate(Bundle App) {
        super.onCreate(App);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.top_menu, menu);
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        return false;
    }
}
