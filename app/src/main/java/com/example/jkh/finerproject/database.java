package com.example.jkh.finerproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper{
    static final String DBNAME="data.db";
    static final int VER=1;


    public database(Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TIME (_id INTEGER , name TEXT, recode TEXT);");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(0, 'total', '00:00:00');");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(1, 'sun', '00:00:00');");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(2, 'mon', '00:00:00');");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(3, 'tue', '00:00:00');");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(4, 'wed', '00:00:00');");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(5, 'thu', '00:00:00');");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(6, 'fri', '00:00:00');");
        db.execSQL("INSERT INTO TIME (_id, name, recode) VALUES(7, 'sat', '00:00:00');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void update(int hour, int min, int sec, int weekday) {
        int tmph, tmpm, tmps;
        String inserttime, from;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TIME WHERE _id=0", null);
        cursor.moveToNext();
        from = cursor.getString(2);
        tmph =Integer.parseInt(from.split(":")[0]);
        tmpm =Integer.parseInt(from.split(":")[1]);
        tmps =Integer.parseInt(from.split(":")[2]);

        tmps=tmps+sec;
        tmpm=tmpm+min+tmps/60;
        tmph=tmph+hour+tmpm/60;

        tmps=tmps%60;
        tmpm=tmpm%60;
        tmph=tmph%60;

        inserttime=Integer.toString(tmph)+":"+Integer.toString(tmpm)+":"+Integer.toString(tmps);
        db.execSQL("UPDATE TIME SET recode='"+inserttime+"' WHERE _id=0");

        cursor = db.rawQuery("SELECT * FROM TIME WHERE _id="+Integer.toString(weekday)+";" , null);
        cursor.moveToNext();
        from = cursor.getString(2);
        tmph =Integer.parseInt(from.split(":")[0]);
        tmpm =Integer.parseInt(from.split(":")[1]);
        tmps =Integer.parseInt(from.split(":")[2]);

        tmps=tmps+sec;
        tmpm=tmpm+min+tmps/60;
        tmph=tmph+hour+tmpm/60;

        tmps=tmps%60;
        tmpm=tmpm%60;
        tmph=tmph%60;

        inserttime=Integer.toString(tmph)+":"+Integer.toString(tmpm)+":"+Integer.toString(tmps);

        db.execSQL("UPDATE TIME SET recode='"+inserttime+"' WHERE _id="+Integer.toString(weekday));

    }
    public String[] getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result[]={"","","","","","","",""} ;
        int i=0;
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM TIME", null);
        while (cursor.moveToNext()) {
            result[i]=cursor.getString(2);
            i++; }

        return result;
    }




}
