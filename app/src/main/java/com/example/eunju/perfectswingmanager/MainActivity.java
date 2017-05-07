package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TimerTask m_Task;
    private Timer m_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_Task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
            }
        };
        m_timer = new Timer();
        m_timer.schedule(m_Task, 2000);
    }
    protected void onDestroy(){
        Log.i("test","onDestroy()");
        m_timer.cancel();
        super.onDestroy();
    }
}
