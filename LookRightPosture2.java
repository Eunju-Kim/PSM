package com.example.eunju.perfectswingmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Kangho on 2017. 5. 22..
 */

public class LookRightPosture2 extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_main);


        ImageButton btn1 = (ImageButton)findViewById(R.id.btn1);
        ImageButton btn2 = (ImageButton)findViewById(R.id.btn2);
        ImageButton btn3 = (ImageButton)findViewById(R.id.btn3);
        ImageButton btn4 = (ImageButton)findViewById(R.id.btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String uri_path = "";
        Intent it = new Intent(LookRightPosture2.this, LookRightPosture.class);

        switch (v.getId()) {
            case R.id.btn1:
                uri_path = "android.resource://com.example.eunju.perfectswingmanager/"+R.raw.basic;
                it.putExtra("uri", uri_path);
                startActivity(it);
                break;
            case R.id.btn2:
                uri_path = "android.resource://com.example.eunju.perfectswingmanager/"+R.raw.grip;
                it.putExtra("uri", uri_path);
                startActivity(it);
                break;
            case R.id.btn3:
                uri_path = "android.resource://com.example.eunju.perfectswingmanager/"+R.raw.iron_grip;
                it.putExtra("uri", uri_path);
                startActivity(it);
                break;
            case R.id.btn4:
                uri_path = "android.resource://com.example.eunju.perfectswingmanager/"+R.raw.driver_grip;
                it.putExtra("uri", uri_path);
                startActivity(it);
                break;
        }
    }
}
