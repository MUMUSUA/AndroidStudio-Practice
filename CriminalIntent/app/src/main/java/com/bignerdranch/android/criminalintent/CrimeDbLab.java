package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ASUS on 2022/6/21.
 */

public class CrimeDbLab {
    private static  CrimeDbLab ourInstance;
    private SQLiteDatabase mDatabase;

    public static CrimeDbLab getInstance(Context context) {
        if(ourInstance==null){
            ourInstance=new CrimeDbLab(context);
        }

        return ourInstance;
    }

    private CrimeDbLab(Context context) {
        mDatabase=new CrimeBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public void addCrime(Crime crime){
        Log.i("CrimeDatabase","------------------>addStart");
        ContentValues values=new ContentValues();
        values.put("uuid",crime.getId().toString());
        values.put("title",crime.getTitle().toString());
        values.put("date",crime.getDate().getTime());
        values.put("solved",crime.isSolved()?1:0);
        Log.i("CrimeDatabase","------------------>addBefore");
        mDatabase.insert("crimes",null,values);
        Log.i("CrimeDatabase","------------------>addAfter");

    }


    public Cursor queryCrimes(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query("crimes",
                new String[]{"uuid","title","date","solved"},
                whereClause,whereArgs,
                null,null,null
                );


        return cursor;

    }


    public Cursor queryCrime(Crime crime){
        Cursor cursor=mDatabase.query("crimes",
                new String[]{"uuid","title","date","solved"},
                "uuid=?",
                new String[]{crime.getId().toString()},
                null,null,null
        );


        return cursor;

    }

    public void updateCrime(Crime crime){

        String uuidString=crime.getId().toString();

        ContentValues values=new ContentValues();
        values.put("title",crime.getTitle().toString());
        values.put("date",crime.getDate().getTime());
        values.put("solved",crime.isSolved()?1:0);

        mDatabase.update("crimes",values,"uuid=?",new String[]{uuidString});
    }

    public void deleteCrime(String id){

        mDatabase.delete("crimes","uuid=?",new String[]{id});
    }
}
