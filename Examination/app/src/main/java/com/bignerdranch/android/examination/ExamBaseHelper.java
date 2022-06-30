package com.bignerdranch.android.examination;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ASUS on 2022/6/26.
 */

public class ExamBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="exam.db";
    private Question[] mQuestionsBank=new Question[]{
            new Question("微型计算机的运算器、控制器及内存储器的总称是____。","CPU","ALU","MPU ","主机","主机","CPU是中央处理器的简称，包括MPU和ALU；MPU是微处理器的简称；ALU是算术逻辑单元的简称；CPU和内存储器的总称为主机，它是微型机核心部分。",21,6,1),
            new Question("长城386微机”中的“386”指的是____。","CPU的型号","CPU的速度 ","内存的容量 ","运算器的速度","CPU的型号","CPU的品质直接决定了微机的档次，在奔腾出现之前，微机名称中直接使用微机中的CPU型号，386机表示了它们使用的CPU芯片为80386。",40,30,1),
            new Question("一个完整的计算机系统包括____。","主机、键盘、显示器","计算机及其外部设备","系统软件与应用软件","计算机的硬件系统和软件系统","计算机的硬件系统和软件系统","一个完整的计算机系统是由硬件系统和软件系统组成的。计算机的硬件是一个物质基础，而计算机软件是使硬件功能得以充分发挥的不可缺少的一部分。因此，对于一个完整的计算机系统，这两者缺一不可。",10,5,1),
            new Question("在微型计算机中，微处理器的主要功能是进行____。","算术逻辑运算及全机的控制","逻辑运算","算术逻辑运算","算术运算","算术逻辑运算及全机的控制","微处理器是计算机一切活动的核心，它的主要功能是实现算术逻辑运算及全机的控制",50,45,1),
            new Question("反映计算机存储容量的基本单位是____。","二进制位","字节","字","双字","字节","存储容量大小是计算机的基本技术指标之一。通常不是以二进制位、字或双字来表示，因为这些表示不规范，一般约定以字节作为反映存储容量大小的基本单位。",10,7,1),
            new Question("在微机中，应用最普遍的字符编码是____。","ASCII码 ","BCD码","汉字编码","补码","ASCII码 ","字符编码是指对英文字母、符号和数字的编码，应用最广泛的是美国国家信息交换标准字符码，简称为ASCII码。BCD码是二—十进制编码。汉字编码是对汉字不同表示方法的各种汉字编码的总称。补码是带符号数的机器数的编码。",100,27,1),
            new Question("DRAM存储器的中文含义是____。","静态随机存储器","动态只读存储器","静态只读存储器","动态随机存储器","动态随机存储器","动态随机存储器的原文是(Dynamic Random Access Memory：DRAM)。随机存储器有静态随机存储器和动态随机存储器之分。半导体动态随机存储器DRAM的存储速度快，存储容量大，价格比静态随机存储器便宜。通常所指的64MB或128MB内存，多为动态随机存储器DRAM。",50,45,1),
            new Question("微型计算机的发展是以____的发展为表征的。","微处理器","软件","主机","控制器","微处理器","微处理器是计算机一切活动的核心，因此微型计算机的发展是以微处理器的发展为表征的。",50,45,1),
            new Question("世界上公认的第一台电子计算机诞生在____。","1945年","1946年","1948年","1952年","1946年","世界上公认的第一台电子计算机ENIAC(埃尼阿克)于1946年在美国诞生。",50,45,1),
            new Question("硬盘连同驱动器是一种____。","内存储器","外存储器","只读存储器","半导体存储器","内存储器","内存储器访问速度快，但是价格较责，存储容量比外存储器小。外存储器单位存储容量的价格便宜，存储容量大，但是存取速度较慢。微信公众号：高校学习墙 硬盘连同驱动器是磁性随机存储器，由于它的价格便宜，存储容量大，存取速度较慢，所以通常作为外存储器使用。",50,45,1),
    };
    public ExamBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        Log.i("ExamBaseHelper","------------------>ExamBaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("ExamDatabase","------------------>Before");
        sqLiteDatabase.execSQL("create table exam(_id integer primary key " +
                "autoincrement,question text,A text,B text,C text,D text,answer text," +
                "explaination text,hint integer,correct integer,type integer)"
        );
        Log.i("ExamDatabase","------------------>After");

        ContentValues values=new ContentValues();
        for(int i=0;i<mQuestionsBank.length;i++){
        Log.i("Database","------------------>addStart");

            values.put("question",mQuestionsBank[i].getQuestion().toString());
            values.put("A",mQuestionsBank[i].getAnswerA().toString());
            values.put("B",mQuestionsBank[i].getAnswerB().toString());
            values.put("C",mQuestionsBank[i].getAnswerC().toString());
            values.put("D",mQuestionsBank[i].getAnswerD().toString());
            values.put("answer",mQuestionsBank[i].getAnswer().toString());
            values.put("explaination",mQuestionsBank[i].getExplaination().toString());
            values.put("hint",mQuestionsBank[i].getHint());
            values.put("correct",mQuestionsBank[i].getCorrect());
            values.put("type",mQuestionsBank[i].getType());


        Log.i("CrimeDatabase","------------------>addBefore");
            sqLiteDatabase.insert("exam",null,values);
        Log.i("CrimeDatabase","------------------>addAfter");}
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
