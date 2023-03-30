package com.example.teamzodiac.RecMembers;

import android.graphics.drawable.Drawable;

public class Members {

    String name;
    int id;
    String number;
    String position_name;
    private int position;

    public Members(String name, String number, int id, String position_name){
        this.name = name;
        this.id = id;
        this.number = number;
        this.position_name = position_name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getPosition_name() {
        return position_name;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
