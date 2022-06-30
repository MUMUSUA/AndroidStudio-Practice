package com.bignerdranch.android.crimerecord;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by ASUS on 2022/6/15.
 */

public class MusicPlayService extends Service {
    private MediaPlayer mMediaPlayer;

    public MusicPlayService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        mMediaPlayer=MediaPlayer.create(MusicPlayService.this,R.raw.melody);
        mMediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       throw new UnsupportedOperationException("Not Yet Implemented");
    }
}
