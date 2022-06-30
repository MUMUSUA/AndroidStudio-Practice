package com.bignerdranch.android.mediaplayer;


import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment implements View.OnClickListener{

    private VideoView mVideoView;

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        mVideoView = (VideoView) view.findViewById(R.id.video_view);
        Button play = (Button) view.findViewById(R.id.play);
        Button pause = (Button) view.findViewById(R.id.pause);
        Button replay = (Button) view.findViewById(R.id.replay);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        String uri="android.resource://"+view.getContext().getPackageName()+"/"+R.raw.going;
        mVideoView.setVideoURI(Uri.parse(uri));
        mVideoView.setMediaController(new MediaController(getActivity()));
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        return view;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!mVideoView.isPlaying()) {
                    mVideoView.start(); // 开始播放
                }
                break;
            case R.id.pause:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause(); // 暂停播放
                }
                break;
            case R.id.replay:
                if (mVideoView.isPlaying()) {
                    mVideoView.resume(); // 重新播放
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.suspend();
        }
    }


}
