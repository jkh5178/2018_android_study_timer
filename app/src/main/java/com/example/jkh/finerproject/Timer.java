package com.example.jkh.finerproject;


import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import java.util.Calendar;

import java.util.TimerTask;

public class Timer extends AppCompatActivity {
    Calendar c=Calendar.getInstance();
    NumberPicker np1,np2,np3;
    int hour,min,sec;
    Button start_btn,puse_btn,reset_btn;
    boolean check=false;
    java.util.Timer timer = new java.util.Timer();
    database dbHelper= new database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        start_btn=findViewById(R.id.c1_btn1);
        puse_btn=findViewById(R.id.c1_btn2);
        reset_btn=findViewById(R.id.c1_btn3);
        np1=findViewById(R.id.c1_numberPicker1);
        np2=findViewById(R.id.c1_numberPicker2);
        np3=findViewById(R.id.c1_numberPicker3);
        np1.setMinValue(0);
        np1.setMaxValue(24);
        np2.setMinValue(0);
        np2.setMaxValue(59);
        np3.setMinValue(0);
        np3.setMaxValue(59);
        np1.setEnabled(false);
        np2.setEnabled(false);
        np3.setEnabled(false);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_btn.setVisibility(View.GONE);
                puse_btn.setVisibility(View.VISIBLE);
                start_btn.setVisibility(View.GONE);
                tempTask();
            }
        });
        puse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puse_btn.setVisibility(View.GONE);
                start_btn.setVisibility(View.VISIBLE);
                reset_btn.setVisibility(View.VISIBLE);
                timer.cancel();
            }
        });
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.update(hour,min,sec,c.get(Calendar.DAY_OF_WEEK));
                reset_btn.setVisibility(View.GONE);
                puse_btn.setVisibility(View.GONE);
                sec=0;
                min=0;
                hour=0;
                np1.setValue(0);
                np2.setValue(0);
                np3.setValue(0);
                np1.setEnabled(false);
                np2.setEnabled(false);
                np3.setEnabled(false);
                start_btn.setVisibility(View.VISIBLE);
            }
        });

    }
    public void tempTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Timer.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (sec == 59) {
                            if (min == 59) {
                                if (hour == 24) {
                                    reset_btn.setVisibility(View.VISIBLE);
                                    puse_btn.setVisibility(View.GONE);
                                    dbHelper.update(hour,min,sec,c.get(Calendar.DAY_OF_WEEK));
                                    timer.cancel();
                                }
                                else{
                                    hour = hour + 1;
                                    min = 00;}
                            }
                            min = min + 1;
                            sec = 00;
                        } else {
                            sec = sec + 1;
                        }
                        np1.setValue(hour);
                        np2.setValue(min);
                        np3.setValue(sec);

                    }
                });
            }
        };
        timer = new java.util.Timer();
        timer.schedule(task, 0, 1000);
    }
    protected void onResume() {

        super.onResume();


    }
    protected void onPause(){
        super.onPause();
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        if (keyguardManager.inKeyguardRestrictedInputMode()) {
            start_btn.performClick();
            // lock screen
        } else {
            // lock screen 이 아님
            puse_btn.performClick();
        }

    }
}