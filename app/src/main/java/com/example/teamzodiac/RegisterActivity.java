package com.example.teamzodiac;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle App) {
        super.onCreate(App);

        setContentView(R.layout.activity_register);
        Button regButton = (Button)findViewById(R.id.regBut);
//        regButton.setActivated(false);
        EditText e1 = (EditText)findViewById(R.id.editText1);
        EditText e2 = (EditText)findViewById(R.id.editText2);
        EditText e3 = (EditText)findViewById(R.id.editText3);
        EditText pw = (EditText) findViewById(R.id.editTextTextPassword);
        EditText date = findViewById(R.id.editTextDate);
        RadioGroup r = findViewById(R.id.radio);
        int sl = r.getCheckedRadioButtonId();
        CheckBox cb = (CheckBox) findViewById(R.id.check);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flag = true;
                if(e1.getText().toString().length()==0) {
                    e1.setError("Must Not be Empty");
                    flag = false;
                }
                if(e2.getText().toString().length()==0) {
                    e2.setError("Must Not be Empty");
                    flag = false;
                }
                if(e3.getText().toString().length()==0) {
                    e3.setError("Must Not be Empty");
                    flag = false;
                }
                if(pw.getText().toString().length()==0) {
                    pw.setError("Must Not be Empty");
                    flag = false;
                }
                if(!cb.isChecked()) {
                    cb.setError("Must Be Accepted");
                    flag = false;
                }
                if(!flag) return;
                String t = "";
                t += e1.getText().toString()+"\t";
                t += e2.getText().toString()+"\t";
                t += e3.getText().toString()+"\t";
                Toast.makeText(RegisterActivity.this,t,Toast.LENGTH_SHORT).show();
            }
        });
        Button teamBut  = (Button)findViewById(R.id.teamBut);
        teamBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamIntent = new Intent(RegisterActivity.this,TeamPage.class);
                startActivity(teamIntent);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        date.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }
}
