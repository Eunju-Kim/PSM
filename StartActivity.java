package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Eunju on 2017-04-24.
 */

public class StartActivity extends ActionBarActivity{
    ImageButton practiceSwing;
    ImageButton right_attitude;
    ImageButton showMyVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start);  // layout xml 과 자바파일을 연결
        right_attitude = (ImageButton)findViewById(R.id.button3);
        practiceSwing = (ImageButton) findViewById(R.id.button) ;
        showMyVideo = (ImageButton)findViewById(R.id.button2);
        practiceSwing.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectClub.class);
                startActivity(intent);
            }
        }) ;

        right_attitude.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(StartActivity.this, LookRightPosture2.class);
                startActivity(it);
            }
        });
        showMyVideo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(StartActivity.this, SelectClub2.class);
                startActivity(it);
            }
        });
    }
}
