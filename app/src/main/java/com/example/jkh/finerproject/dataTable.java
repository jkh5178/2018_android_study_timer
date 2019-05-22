package com.example.jkh.finerproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class dataTable extends AppCompatActivity {

    database dbHelper= new database(this);
    String timetable[]={"","","","","","","",""};
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_table);
        timetable=dbHelper.getResult();

        tv1=findViewById(R.id.total);
        tv2=findViewById(R.id.sun);
        tv3=findViewById(R.id.mon);
        tv4=findViewById(R.id.tue);
        tv5=findViewById(R.id.wed);
        tv6=findViewById(R.id.thu);
        tv7=findViewById(R.id.fri);
        tv8=findViewById(R.id.sat);

        tv1.setText(timetable[0]);
        tv2.setText(timetable[1]);
        tv3.setText(timetable[2]);
        tv4.setText(timetable[3]);
        tv5.setText(timetable[4]);
        tv6.setText(timetable[5]);
        tv7.setText(timetable[6]);
        tv8.setText(timetable[7]);
    }
    protected void onResume() {
        super.onResume();
        timetable=dbHelper.getResult();
        tv1.setText(timetable[0]);
        tv2.setText(timetable[1]);
        tv3.setText(timetable[2]);
        tv4.setText(timetable[3]);
        tv5.setText(timetable[4]);
        tv6.setText(timetable[5]);
        tv7.setText(timetable[6]);
        tv8.setText(timetable[7]);

    }
}
