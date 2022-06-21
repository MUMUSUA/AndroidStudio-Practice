package com.bignerdranch.android.examination;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class ExamActivity extends AppCompatActivity {



    private static final String TAG="QuizActivity";

    private ImageButton mPrevImageButton;
    private ImageButton mNextImageButton;
    private TextView mTextView;
    private int mCurrentIndex=0;
    private RadioGroup mAllButton;
    private Button mCheatButton;
    private static final String KEY_INDEX="index";
    private Button mA_button;
    private Button mB_button;
    private Button mC_button;
    private Button mD_button;
    private TextView mAnswerText;
    private Question[] mQuestionsBank=new Question[]{
            new Question(R.string.q1,"A:正确","B:错误","C:","D:","A","111111"),
            new Question(R.string.q2,"A:正确","B:错误","C:无效","D:","A","22222"),
            new Question(R.string.q3,"A:正确","B:错误","C:","D:无效","B","尼罗河的尽头不在埃及"),
            new Question(R.string.q4,"A:正确","B:错误","C:","D:","A","4444"),
//            new Question(R.string.q1,true),
//            new Question(R.string.q2,true),
//            new Question(R.string.q3,false),
//            new Question(R.string.q4,true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_exam);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        TabHost th = (TabHost) findViewById(R.id.tabhost);
        th.setup();            //初始化TabHost容器

        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        th.addTab(th.newTabSpec("tab1").setIndicator("单选", null).setContent(R.id.tab1));
        th.addTab(th.newTabSpec("tab2").setIndicator("多选", null).setContent(R.id.tab2));
        th.addTab(th.newTabSpec("tab3").setIndicator("判断", null).setContent(R.id.tab3));
        th.addTab(th.newTabSpec("tab4").setIndicator("填空", null).setContent(R.id.tab4));

        mTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionsBank[mCurrentIndex].getTextResID();
        mTextView.setText(question);

        mAnswerText= (TextView) findViewById(R.id.question_text_tip);
        mAllButton=(RadioGroup)findViewById(R.id.all_button);


        mA_button = (Button) findViewById(R.id.A_button);
        mA_button.setText(mQuestionsBank[mCurrentIndex].getAnswerA());
        mA_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("A",mA_button);
            }
        });

        mB_button = (Button) findViewById(R.id.B_button);
        mB_button.setText(mQuestionsBank[mCurrentIndex].getAnswerB());
        mB_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("B",mB_button);
            }
        });

        mC_button = (Button) findViewById(R.id.C_button);
        mC_button.setText(mQuestionsBank[mCurrentIndex].getAnswerC());
        mC_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("C",mC_button);
            }
        });

        mD_button = (Button) findViewById(R.id.D_button);
        mD_button.setText(mQuestionsBank[mCurrentIndex].getAnswerD());
        mD_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("D",mD_button);
            }
        });

        mPrevImageButton = (ImageButton) findViewById(R.id.image_prev_Button);
        mPrevImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) mCurrentIndex = mQuestionsBank.length;
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                int question = mQuestionsBank[mCurrentIndex].getTextResID();
                mTextView.setText(question);
                mAllButton.clearCheck();
                mA_button.setText(mQuestionsBank[mCurrentIndex].getAnswerA());
                mB_button.setText(mQuestionsBank[mCurrentIndex].getAnswerB());
                mC_button.setText(mQuestionsBank[mCurrentIndex].getAnswerC());
                mD_button.setText(mQuestionsBank[mCurrentIndex].getAnswerD());
            }
        });

        mNextImageButton = (ImageButton) findViewById(R.id.image_next_Button);
        mNextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
//                int question = mQuestionsBank[mCurrentIndex].getTextResID();
//                mTextView.setText(question);
//                mA_button.setText(mQuestionsBank[mCurrentIndex].getAnswerA());
//                mB_button.setText(mQuestionsBank[mCurrentIndex].getAnswerB());
//                mC_button.setText(mQuestionsBank[mCurrentIndex].getAnswerC());
//                mD_button.setText(mQuestionsBank[mCurrentIndex].getAnswerD());
                next();
            }
        });
    }

    private void next(){
        mCurrentIndex=(mCurrentIndex+1)%mQuestionsBank.length;
        int question=mQuestionsBank[mCurrentIndex].getTextResID();
        mTextView.setText(question);
        mAnswerText.setEnabled(false);
        mAllButton.clearCheck();
        mA_button.setTextColor(Color.parseColor("#000000"));
        mB_button.setTextColor(Color.parseColor("#000000"));
        mC_button.setTextColor(Color.parseColor("#000000"));
        mD_button.setTextColor(Color.parseColor("#000000"));
        mA_button.setText(mQuestionsBank[mCurrentIndex].getAnswerA());
        mB_button.setText(mQuestionsBank[mCurrentIndex].getAnswerB());
        mC_button.setText(mQuestionsBank[mCurrentIndex].getAnswerC());
        mD_button.setText(mQuestionsBank[mCurrentIndex].getAnswerD());

    }

    private void checkAnswer(String userPressed,Button btn){
        String answerIsTrue=mQuestionsBank[mCurrentIndex].getAnswer();
        int quesID=mQuestionsBank[mCurrentIndex].getTextResID();
        System.out.println("答案是："+answerIsTrue);
        int messageResId=0;

        if(userPressed==answerIsTrue){
            btn.setTextColor(Color.parseColor("#157924"));
            messageResId=R.string.dd;
            next();
        }
        else{
            btn.setTextColor(Color.parseColor("#b60b30"));
            messageResId=R.string.ee;
            switch (answerIsTrue) {

                case "A":mA_button.setTextColor(Color.parseColor("#157924"));break;
                case "B":mB_button.setTextColor(Color.parseColor("#157924"));break;
                case "C":mC_button.setTextColor(Color.parseColor("#157924"));break;
                case "D":mD_button.setTextColor(Color.parseColor("#157924"));break;

            }
            mAnswerText.setText("正确答案："+answerIsTrue+"\n"+"解析："+mQuestionsBank[mCurrentIndex].getExplaination());
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();

    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
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
