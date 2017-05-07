package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Eunju on 2017-04-24.
 */

public class PracticeSwing extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.practice_swing);  // layout xml 과 자바파일을 연결
        Intent intent = getIntent();
        int age = intent.getExtras().getInt("club");
//        if(age == 1){
//
//        }else{
//
//        }
    }
}
