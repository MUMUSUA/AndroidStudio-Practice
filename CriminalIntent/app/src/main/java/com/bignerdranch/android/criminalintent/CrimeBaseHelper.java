package com.bignerdranch.android.criminalintent;

/**
 * Created by ASUS on 2022/6/19.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="crimeDatabase.db";
    public CrimeBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        Log.i("CrimeBaseHelper","------------------>CrimeBaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("CrimeDatabase","------------------>Before");
        sqLiteDatabase.execSQL("create table crimes(_id integer primary key " +
                "autoincrement,uuid text,title text,date text,solved text)"
        );
        Log.i("CrimeDatabase","------------------>After");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}