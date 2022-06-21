package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PicActivity extends AppCompatActivity {

    private ImageView mSheepImageView;
    private  Picture[] mPics=Picture.pics;

//    final int[] imageResID=new int[]{
//            R.drawable.beautiful,
//            R.drawable.clever,
//            R.drawable.lazy,
//            R.drawable.slow,
//            R.drawable.strong,
//            R.drawable.warm
//
//    };

    int mCurrentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        mSheepImageView=(ImageView)findViewById(R.id.sheepImageView);
        mSheepImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mSheepImageView.setImageResource(mPics[(++mCurrentIndex)%mPics.length].getPicResID());
            }
        });
    }
}
