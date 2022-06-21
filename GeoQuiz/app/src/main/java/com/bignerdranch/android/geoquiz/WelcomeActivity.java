package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    private ImageButton mWelcomeImageButton;
    private ImageButton mPicImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.d("WelcomeActivity","Welcome------------>onCreate");
        mWelcomeImageButton=(ImageButton)findViewById(R.id.Welcome_imageButton);
        mPicImageButton=(ImageButton)findViewById(R.id.Pic_imageButton);
        mWelcomeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WelcomeActivity","WelcomeImageButton onCreate");
                startActivity(new Intent(WelcomeActivity.this,QuizActivity.class));

            }
        });

        mPicImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WelcomeActivity","WelcomeImageButton onCreate");
                startActivity(new Intent(WelcomeActivity.this,PicActivity.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("WelcomeActivity","Welcome------------>onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("WelcomeActivity","Welcome------------>onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("WelcomeActivity","Welcome------------>onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("WelcomeActivity","Welcome------------>onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WelcomeActivity","Welcome------------>onResume");
    }
}
