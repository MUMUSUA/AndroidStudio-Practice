package com.bignerdranch.android.mycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.id.list;

public class HistoryActivity extends AppCompatActivity {
    ArrayList<String> list;
    ArrayList<String> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView listView = (ListView) this.findViewById(R.id.listView);


        list = new ArrayList<>();
//        list.add("lisi");
        list1 = new ArrayList<>();
//        list1.add("25");

        Intent intent=getIntent();
        list=intent.getStringArrayListExtra("Exp");
        list1=intent.getStringArrayListExtra("Res");


        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < list .size(); i++){
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("exp", list.get(i));
            item.put("res", list1.get(i));
            data.add(item);
        }
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(HistoryActivity.this, data, R.layout.item,
                new String[]{"exp", "res"}, new int[]{R.id.exp, R.id.res});
        //实现列表的显示
        listView.setAdapter(adapter);
    }
    }

