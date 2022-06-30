package com.bignerdranch.android.examination;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
    private SQLiteDatabase mDatabase;

    private Question[] mQuestionsBank=new Question[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_exam);

        mDatabase=new ExamBaseHelper(this.getApplicationContext()).getWritableDatabase();
        allExam();
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
        String question = mQuestionsBank[mCurrentIndex].getQuestion();
        mTextView.setText(String.valueOf(mCurrentIndex+1)+"."+question);

        mAnswerText= (TextView) findViewById(R.id.question_text_tip);
        mAllButton=(RadioGroup)findViewById(R.id.all_button);


        mA_button = (Button) findViewById(R.id.A_button);
        mA_button.setText("A:"+mQuestionsBank[mCurrentIndex].getAnswerA());
        mA_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsBank[mCurrentIndex].setHint(mQuestionsBank[mCurrentIndex].getHint()+1);
                update(mQuestionsBank[mCurrentIndex]);
                checkAnswer(mQuestionsBank[mCurrentIndex].getAnswerA(),mA_button,"A");
            }
        });

        mB_button = (Button) findViewById(R.id.B_button);
        mB_button.setText("B:"+mQuestionsBank[mCurrentIndex].getAnswerB());
        mB_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsBank[mCurrentIndex].setHint(mQuestionsBank[mCurrentIndex].getHint()+1);
                update(mQuestionsBank[mCurrentIndex]);
                checkAnswer(mQuestionsBank[mCurrentIndex].getAnswerB(),mB_button,"B");
            }
        });

        mC_button = (Button) findViewById(R.id.C_button);
        mC_button.setText("C:"+mQuestionsBank[mCurrentIndex].getAnswerC());
        mC_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsBank[mCurrentIndex].setHint(mQuestionsBank[mCurrentIndex].getHint()+1);
                update(mQuestionsBank[mCurrentIndex]);
                checkAnswer(mQuestionsBank[mCurrentIndex].getAnswerC(),mC_button,"C");
            }
        });

        mD_button = (Button) findViewById(R.id.D_button);
        mD_button.setText("D:"+mQuestionsBank[mCurrentIndex].getAnswerD());
        mD_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsBank[mCurrentIndex].setHint(mQuestionsBank[mCurrentIndex].getHint()+1);
                update(mQuestionsBank[mCurrentIndex]);
                checkAnswer(mQuestionsBank[mCurrentIndex].getAnswerD(),mD_button,"D");
            }
        });

        mPrevImageButton = (ImageButton) findViewById(R.id.image_prev_Button);
        mPrevImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) mCurrentIndex = mQuestionsBank.length;
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                String question = mQuestionsBank[mCurrentIndex].getQuestion();
                mTextView.setText(String.valueOf(mCurrentIndex+1)+"."+question);
                mAnswerText.setText("");
                mAllButton.clearCheck();
                mA_button.setTextColor(Color.parseColor("#000000"));
                mB_button.setTextColor(Color.parseColor("#000000"));
                mC_button.setTextColor(Color.parseColor("#000000"));
                mD_button.setTextColor(Color.parseColor("#000000"));
                mA_button.setText("A:"+mQuestionsBank[mCurrentIndex].getAnswerA());
                mB_button.setText("B:"+mQuestionsBank[mCurrentIndex].getAnswerB());
                mC_button.setText("C:"+mQuestionsBank[mCurrentIndex].getAnswerC());
                mD_button.setText("C:"+mQuestionsBank[mCurrentIndex].getAnswerD());
            }
        });

        mNextImageButton = (ImageButton) findViewById(R.id.image_next_Button);
        mNextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next();
            }
        });
    }

    private void next(){
        mCurrentIndex=(mCurrentIndex+1)%mQuestionsBank.length;
        String question=mQuestionsBank[mCurrentIndex].getQuestion();
        mTextView.setText(String.valueOf(mCurrentIndex+1)+"."+question);
        mAnswerText.setText("");
        mAllButton.clearCheck();
        mA_button.setTextColor(Color.parseColor("#000000"));
        mB_button.setTextColor(Color.parseColor("#000000"));
        mC_button.setTextColor(Color.parseColor("#000000"));
        mD_button.setTextColor(Color.parseColor("#000000"));
        mA_button.setText("A:"+mQuestionsBank[mCurrentIndex].getAnswerA());
        mB_button.setText("B:"+mQuestionsBank[mCurrentIndex].getAnswerB());
        mC_button.setText("C:"+mQuestionsBank[mCurrentIndex].getAnswerC());
        mD_button.setText("D:"+mQuestionsBank[mCurrentIndex].getAnswerD());

    }

    private void checkAnswer(String userPressed,Button btn,String choice){
        String answer=mQuestionsBank[mCurrentIndex].getAnswer();
//        String quesID=mQuestionsBank[mCurrentIndex].getQuestion();
//        System.out.println("答案是："+answerIsTrue);
        int messageResId=0;

        if(userPressed.equals(answer)){
            btn.setTextColor(Color.parseColor("#157924"));
            messageResId=R.string.dd;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数 ， 其实这里还可以这样写 DecimalFormat("0.00%");   这样就不用在最后输出时还要加。
            String s = df.format((float) mQuestionsBank[mCurrentIndex].getCorrect()*100/mQuestionsBank[mCurrentIndex].getHint())+"%";
            mAnswerText.setText("正确答案："+answer+"\n"+"正确率："+s+"\n"+"解析："+mQuestionsBank[mCurrentIndex].getExplaination());
            mQuestionsBank[mCurrentIndex].setCorrect(mQuestionsBank[mCurrentIndex].getCorrect()+1);
            update(mQuestionsBank[mCurrentIndex]);

        }
        else{
            btn.setTextColor(Color.parseColor("#b60b30"));
            messageResId=R.string.ee;

            if(mA_button.getText().toString().equals("A:"+answer))
                mA_button.setTextColor(Color.parseColor("#157924"));
            if(mB_button.getText().toString().equals("B:"+answer))
                mB_button.setTextColor(Color.parseColor("#157924"));
            if(mC_button.getText().toString().equals("C:"+answer))
                mC_button.setTextColor(Color.parseColor("#157924"));
            if(mD_button.getText().toString().equals("D:"+answer))
                mD_button.setTextColor(Color.parseColor("#157924"));


            DecimalFormat df = new DecimalFormat("0.00");//格式化小数 ， 其实这里还可以这样写 DecimalFormat("0.00%");   这样就不用在最后输出时还要加。
            String s = df.format((float) mQuestionsBank[mCurrentIndex].getCorrect()*100/mQuestionsBank[mCurrentIndex].getHint())+"%";
            mAnswerText.setText("正确答案："+answer+"\n"+"正确率："+s+"\n"+"解析："+mQuestionsBank[mCurrentIndex].getExplaination());
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
public void allExam(){
    Cursor cursor=queryExams(null,null);
    if(cursor==null)
        Toast.makeText(this.getApplication(),"cursor is null!",Toast.LENGTH_SHORT).show();
    else{
        int i=0;
        Toast.makeText(this.getApplication(),"题库中共有"+cursor.getCount()+"道题，随机抽取5道。",Toast.LENGTH_SHORT).show();
        Question[] mQuestions=new Question[cursor.getCount()];
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            Log.i("Query","------------------>date");
            Question c=new Question(
                    cursor.getString(cursor.getColumnIndex("question")),
                    cursor.getString(cursor.getColumnIndex("A")),
                    cursor.getString(cursor.getColumnIndex("B")),
                    cursor.getString(cursor.getColumnIndex("C")),
                    cursor.getString(cursor.getColumnIndex("D")),
                    cursor.getString(cursor.getColumnIndex("answer")),
                    cursor.getString(cursor.getColumnIndex("explaination")),
                    cursor.getInt(cursor.getColumnIndex("hint")),
                    cursor.getInt(cursor.getColumnIndex("correct")),
                    cursor.getInt(cursor.getColumnIndex("type"))
            );
            mQuestions[i]=c;
//            mQuestionsBank[i]=c;
            i=i+1;
            cursor.moveToNext();

        }

        Random r = new Random();
        for (int k = 0; k < mQuestions.length; k++) {

            int randomIndex = r.nextInt(mQuestions.length);
            Question temp = mQuestions[k];
            mQuestions[k] = mQuestions[randomIndex];
            mQuestions[randomIndex] = temp;



        }

        for(int j=0;j<mQuestionsBank.length;j++){
            String[] answers=new String[]{mQuestions[j].getAnswerA(),mQuestions[j].getAnswerB(),mQuestions[j].getAnswerC(),mQuestions[j].getAnswerD()};
            Random r2 = new Random();
            for (int k2 = 0; k2 < answers.length; k2++) {
                int randomIndex2 = r2.nextInt(answers.length);
                String temp2 = answers[k2];
                answers[k2] = answers[randomIndex2];
                answers[randomIndex2] = temp2;
            }
            mQuestions[j].setAnswerA(answers[0]);
            mQuestions[j].setAnswerB(answers[1]);
            mQuestions[j].setAnswerC(answers[2]);
            mQuestions[j].setAnswerD(answers[3]);


            mQuestionsBank[j]=mQuestions[j];
        }


    }

}

    public Cursor queryExams(String whereClause, String[] whereArgs){

        Cursor cursor=mDatabase.query("exam",
                new String[]{"question","A","B","C","D", "answer","explaination",
       "hint","correct","type"},
                whereClause,whereArgs,
                null,null,null
        );


        return cursor;

    }

    public void update(Question Q){

        String qid=String.valueOf(Q.getId());
        ContentValues values=new ContentValues();
        values.put("question",Q.getQuestion().toString());
        values.put("A",Q.getAnswerA().toString());
        values.put("B",Q.getAnswerB().toString());
        values.put("C",Q.getAnswerC().toString());
        values.put("D",Q.getAnswerD().toString());
        values.put("answer",Q.getAnswer().toString());
        values.put("explaination",Q.getExplaination().toString());
        values.put("hint",Q.getHint());
        values.put("correct",Q.getCorrect());
        values.put("type",Q.getType());
        mDatabase.update("exam",values,"_id=?",new String[]{qid});
        Log.i("Query","------------------>updateHint"+Q.getHint());
        Log.i("Query","------------------>updateCorrect"+Q.getCorrect());
    }
}
