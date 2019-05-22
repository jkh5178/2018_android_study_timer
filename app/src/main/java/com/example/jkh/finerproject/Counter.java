package com.example.jkh.finerproject;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class Counter extends AppCompatActivity {

    Calendar c;
    NumberPicker np1,np2,np3;
    int thour,tmin,tsec;
    int hour,min,sec;
    Button start_btn,puse_btn,reset_btn;
    boolean check=false;
    java.util.Timer timer =new java.util.Timer();

    database dbHelper= new database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        c=Calendar.getInstance();

        start_btn=findViewById(R.id.c2_btn1);
        puse_btn=findViewById(R.id.c2_btn2);
        reset_btn=findViewById(R.id.c2_btn3);
        np1=findViewById(R.id.c2_numberPicker1);
        np2=findViewById(R.id.c2_numberPicker2);
        np3=findViewById(R.id.c2_numberPicker3);
        np1.setMinValue(0);
        np1.setMaxValue(24);
        np2.setMinValue(0);
        np2.setMaxValue(59);
        np3.setMinValue(0);
        np3.setMaxValue(59);

        hour=np1.getValue();
        min=np2.getValue();
        sec=np3.getValue();

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puse_btn.setVisibility(View.VISIBLE);
                np1.setEnabled(false);
                np2.setEnabled(false);
                np3.setEnabled(false);
                start_btn.setVisibility(View.GONE);
                thour =hour;
                tmin =min;
                tsec =sec;
                tempTask();
            }
        });

        puse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puse_btn.setVisibility(View.GONE);
                start_btn.setVisibility(View.VISIBLE);
                timer.cancel();
            }
        });

        reset_btn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                dbHelper.update(thour,tmin,tsec,c.get(Calendar.DAY_OF_WEEK));
                hour=0;
                min=0;
                sec=0;
                reset_btn.setVisibility(View.GONE);
                np1.setValue(0);
                np2.setValue(0);
                np3.setValue(0);
                np1.setEnabled(true);
                np2.setEnabled(true);
                np3.setEnabled(true);
                start_btn.setVisibility(View.VISIBLE);

                return false;
            }
        });


        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour=newVal;

            }
        });
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min=newVal;
            }
        });
        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                sec=newVal;

            }
        });

    }

    public void tempTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Counter.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(sec==0){
                            if(min==0){
                                if(hour==0){
                                    reset_btn.setVisibility(View.VISIBLE);
                                    puse_btn.setVisibility(View.GONE);
                                    timer.cancel();

                                }
                                hour=hour-1;
                                min=60;
                            }
                            min=min-1;
                            sec=59;
                        }
                        else{
                            sec=sec-1;
                        }
                        np1.setValue(hour);
                        np2.setValue(min);
                        np3.setValue(sec);

                    }
                });


            }

        };
        timer = new Timer();
        timer.schedule(task, 0, 1000);
    }
    protected void onPause(){
        super.onPause();
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        if (keyguardManager.inKeyguardRestrictedInputMode()) {
            // lock screen
            start_btn.performClick();
        } else {
            // lock screen 이 아님
            puse_btn.performClick();
        }

    }


}
