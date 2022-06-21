package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    private static final String TAG = "CheatActivity";
    public static final String EXTRA_ANSWER_SHOWN = "tfquiz.ANSWER_SHOWN";

    boolean mAnswerIsTrue;
    TextView mwarningTextView;
    TextView mAnswerTextView;
    Button mShowAnswer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_cheat);


        mwarningTextView=(TextView)findViewById(R.id.warningTextView);

        Intent intent=getIntent();

        final int questionID=intent.getIntExtra("Question",0);

        mAnswerIsTrue = getIntent().getBooleanExtra("ANSWER", false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mwarningTextView.setText(questionID);
                mAnswerTextView.setText(Boolean.toString(mAnswerIsTrue));
            }
        });
    }

}
