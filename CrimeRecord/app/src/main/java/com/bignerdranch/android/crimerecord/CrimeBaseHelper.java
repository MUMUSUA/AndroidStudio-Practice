package com.bignerdranch.android.crimerecord;

/**
 * Created by ASUS on 2022/6/19.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="Thinking.db";
    public CrimeBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        Log.i("CrimeBaseHelper","------------------>CrimeBaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("CrimeDatabase","------------------>Before");
        sqLiteDatabase.execSQL("create table ideas(_id integer primary key " +
                "autoincrement,uuid text,title text,painter text,date text,desc text,photo text)"
        );
        Log.i("CrimeDatabase","------------------>After");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}