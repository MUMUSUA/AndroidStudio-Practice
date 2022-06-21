package com.bignerdranch.android.criminalintent;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity {
private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        String uri="android.resource://"+getPackageName()+"/"+R.raw.going;
        mVideoView=(VideoView) findViewById(R.id.VideoPlay);
        mVideoView.setVideoURI(Uri.parse(uri));
        mVideoView.setMediaController(new MediaController(VideoPlayActivity.this));
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }
}
