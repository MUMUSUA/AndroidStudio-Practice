package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG="QuizActivity";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;
    private ImageButton mPrevImageButton;
    private ImageButton mNextImageButton;
    private TextView mTextView;
    private int mCurrentIndex=0;
    private Button mCheatButton;
    private static final String KEY_INDEX="index";

    private Question[] mQuestionsBank=new Question[]{
            new Question(R.string.q1,true),
            new Question(R.string.q2,true),
            new Question(R.string.q3,false),
            new Question(R.string.q4,true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            Log.d(TAG,"mCurrentIndex"+String.valueOf(mCurrentIndex));
        }

        mTrueButton=(Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
//                Toast.makeText(QuizActivity.this,R.string.dd,Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

        mFalseButton=(Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
//                Toast.makeText(QuizActivity.this,R.string.ee,Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

        mTextView=(TextView)findViewById(R.id.question_text_view);
        int question=mQuestionsBank[mCurrentIndex].getTextResID();
        mTextView.setText(question);

        mPrevButton=(Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0) mCurrentIndex=mQuestionsBank.length;
                mCurrentIndex=(mCurrentIndex-1)%mQuestionsBank.length;
                int question=mQuestionsBank[mCurrentIndex].getTextResID();
                mTextView.setText(question);
            }
        });

        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionsBank.length;
                int question=mQuestionsBank[mCurrentIndex].getTextResID();
                mTextView.setText(question);
            }
        });


        mPrevImageButton=(ImageButton)findViewById(R.id.image_prev_Button);
        mPrevImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0) mCurrentIndex=mQuestionsBank.length;
                mCurrentIndex=(mCurrentIndex-1)%mQuestionsBank.length;
                int question=mQuestionsBank[mCurrentIndex].getTextResID();
                mTextView.setText(question);
            }
        });

        mNextImageButton=(ImageButton)findViewById(R.id.image_next_Button);
        mNextImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionsBank.length;
                int question=mQuestionsBank[mCurrentIndex].getTextResID();
                mTextView.setText(question);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "cheat button clicked");
                Intent intent=new Intent(QuizActivity.this,CheatActivity.class);
                intent.putExtra("Question",mQuestionsBank[mCurrentIndex].getTextResID());
                intent.putExtra("ANSWER",mQuestionsBank[mCurrentIndex].isAnswerTrue());
                startActivity(intent);
                Log.d(TAG, "intent created");

            }
        });
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId=0;

        if(userPressedTrue==answerIsTrue){
            messageResId=R.string.dd;
        }
        else{
            messageResId=R.string.ee;
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        Log.d(TAG,"ZZLM");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }



}
