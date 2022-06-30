package com.bignerdranch.android.crimerecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

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
        values.put("painter",crime.getPainter());
        values.put("desc",crime.getDesc());
        values.put("photo",convertToBase64(crime.getPhotoId()));
        Log.i("CrimeDatabase","------------------>addBefore");
        mDatabase.insert("ideas",null,values);
        Log.i("CrimeDatabase","------------------>addAfter");

    }


    public Cursor queryCrimes(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query("ideas",
                new String[]{"uuid","title","painter","date","desc","photo"},
                whereClause,whereArgs,
                null,null,null
                );


        return cursor;

    }


    public Cursor queryCrime(Crime crime){
        Cursor cursor=mDatabase.query("ideas",
                new String[]{"uuid","title","painter","date","desc","photo"},
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
        values.put("painter",crime.getPainter());
        values.put("desc",crime.getDesc());
        values.put("photo",convertToBase64(crime.getPhotoId()));
        mDatabase.update("ideas",values,"uuid=?",new String[]{uuidString});
    }

    public void deleteCrime(String id){

        mDatabase.delete("ideas","uuid=?",new String[]{id});
    }


    public String convertToBase64(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);

        byte[] byteArray = os.toByteArray();

        return Base64.encodeToString(byteArray, 0);

    }


}
