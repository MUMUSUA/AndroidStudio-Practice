package com.bignerdranch.android.examination;

/**
 * Created by ASUS on 2022/4/21.
 */



public class Question {

    public int question;
    public String answerA;
    public String answerB;
    public String answerC;
    public String answerD;
    public String answer;
    public String explaination;
    public int ID ;
    //用户选择的答案
    public int selectedAnswer;

    private int mTextResID;
    private boolean mAnswerTrue;

    public Question(int textResID, boolean answerTrue){
        mTextResID=textResID;
        mAnswerTrue=answerTrue;
    }

    public Question(int textResID, String A,
                    String B,
                    String C,
                    String D,
                    String ans,
                    String exp){

        mTextResID=textResID;
        answerA=A;
        answerB=B;
        answerC=C;
        answerD=D;
        answer=ans;
        explaination=exp;
    }


    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }


    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTextResID() {
        return mTextResID;
    }

    public void setTextResID(int textResID) {
        mTextResID = textResID;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

}