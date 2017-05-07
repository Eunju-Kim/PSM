package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Eunju on 2017-04-24.
 */

public class StartActivity extends ActionBarActivity{
    Button practiceSwing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.start);  // layout xml 과 자바파일을 연결
        practiceSwing = (Button) findViewById(R.id.button) ;
        practiceSwing.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectClub.class);
                startActivity(intent);
            }
        }) ;


    }
}
