package com.bignerdranch.android.geoquiz;

/**
 * Created by ASUS on 2022/5/2.
 */

public final class Picture {
    private String PicName;
    private int PicResID;
    public Picture(String name,int ID){
        this.PicName=name;
        this.PicResID=ID;
    }


    public static final Picture[] pics={

            new Picture("beautiful",R.drawable.beautiful),
            new Picture("clever",R.drawable.clever),
            new Picture("lazy",R.drawable.lazy),
            new Picture("slow",R.drawable.slow),
            new Picture("strong",R.drawable.strong),
            new Picture("warm",R.drawable.warm)


    };

    public String getPicName() {
        return PicName;
    }

    public void setPicName(String picName) {
        PicName = picName;
    }

    public int getPicResID() {
        return PicResID;
    }

    public void setPicResID(int picResID) {
        PicResID = picResID;
    }
}
