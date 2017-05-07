package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Eunju on 2017-04-24.
 */

public class SelectClub extends ActionBarActivity {
    Button btndriver, btniron;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.select_club);  // layout xml 과 자바파일을 연결
        btndriver = (Button) findViewById(R.id.btnDriver) ;
        btniron = (Button)findViewById(R.id.btnIron);
        btndriver.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PracticeSwing.class);
                intent.putExtra("club", 1);
                startActivity(intent);
            }
        }) ;
        btniron.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PracticeSwing.class);
                intent.putExtra("club", 2);
                startActivity(intent);
            }
        }) ;



    }
}
