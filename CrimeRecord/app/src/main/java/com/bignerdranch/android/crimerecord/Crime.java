package com.bignerdranch.android.crimerecord;




import android.graphics.Bitmap;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private Date mDate;
    private String mTitle;
    private String painter;
    private String desc;
    private Bitmap photoId;
    public void setId(UUID id) {
        mId = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Bitmap photoId) {
        this.photoId = photoId;
    }

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();

    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }
}
