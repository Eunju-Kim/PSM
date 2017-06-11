package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Eunju on 2017-04-24.
 */

public class PracticeSwing extends ActionBarActivity {

    class ProData {
        int sequence;
        int left;
        int right;

        ProData(int sequence, int left, int right) {
            this.sequence = sequence;
            this.left = left;
            this.right = right;
        }
    }

    HttpURLConnection connection;
    ArrayList<ProData> arrayList;

    class RequestTask extends AsyncTask<String, Void, Boolean> {

        JSONArray responseArray;

        @Override
        protected Boolean doInBackground(String... urls) {
            // TODO: attempt authentication against a network service.

            try {
                Log.d("Hello", "Hello");
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setDefaultUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Cache-Control", "no-cache");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;
                    int nLength = 0;

                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1)
                        baos.write(byteBuffer, 0, nLength);

                    byteData = baos.toByteArray();

                    String response = new String(byteData);
                    responseArray = new JSONArray(response);

                    if (responseArray.length() > 0)
                        return true;
                }
            }

            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {

                Log.d("Hellooooooooooooooooooo", "Hellooooooooooooooooooo");
                // ArrayList에 값 넣기
                for (int i = 0; i < responseArray.length(); i++) {
                    try {

                        JSONObject responseJSON = (JSONObject)responseArray.get(i);
                        ProData proData = new ProData((Integer.parseInt(responseJSON.getString("SEQ"))), Integer.parseInt(responseJSON.getString("LEFT")), Integer.parseInt(responseJSON.getString("RIGHT")));

                        arrayList.add(proData);
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//    private Button btnTest;


    private Handler handler;
    private int index = 0;
    private int proindex = 160;
    ArrayList<String> labels;
    ArrayList<BarEntry> group1, group2;
    BarDataSet barDataSet1,barDataSet2;
    BarChart barChart;
    ArrayList<BarDataSet> dataset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_swing);  // layout xml 과 자바파일을 연결

        Intent intent = getIntent();
        int club = intent.getExtras().getInt("club");
        String uri = intent.getExtras().getString("uri");
//        if(club == 1){
//
//        }else{
//
//        }

//        btnTest = (Button)findViewById(R.id.btnTest);
//
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                index = 0;
//                handler.sendEmptyMessage(0);
//            }
//        });
        arrayList = new ArrayList<ProData>();
        final VideoView videoView = (VideoView) findViewById(R.id.End_Show_Video);
        // 받아오는 부분 //
        RequestTask requestTask = new RequestTask();
        requestTask.execute("https://cushines.xyz/psm/select_weight.php?proID=p0001");

        RequestTask requestTask2 = new RequestTask();
        requestTask2.execute("https://cushines.xyz/psm/select_weight.php?proID=p0002");

        barChart = (BarChart) findViewById(R.id.chart);
        labels = new ArrayList<String>();
        labels.add("왼발");
        labels.add("오른발");

        group1 = new ArrayList<BarEntry>();//나의데이터
        group1.add(new BarEntry(20, 0));
        group1.add(new BarEntry(40, 1));

        group2 = new ArrayList<BarEntry>();//선수데이터
        group2.add(new BarEntry(40, 0));
        group2.add(new BarEntry(80, 1));

        barDataSet1 = new BarDataSet(group1, "나의 데이터");
        barDataSet1.setColor(Color.rgb(33,107,61));
        //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        barDataSet2 = new BarDataSet(group2, "선수 데이터");
        barDataSet2.setColor(Color.rgb(255,94,0));
        //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataset = new ArrayList<>();
        dataset.add(barDataSet1);
        dataset.add(barDataSet2);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaxValue(100);
        yAxis.setAxisMinValue(0);
        yAxis = barChart.getAxisRight();
        yAxis.setAxisMaxValue(100);
        yAxis.setAxisMinValue(0);

        BarData data = new BarData(labels, dataset);
        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.setData(data);
        //barChart.animateY(5000);

        handler = new Handler() { //0.02초마다 150번 데이터 가져와서 그래프 새로 만든다.
            public void handleMessage(Message msg) {
                if (index < 160) {
                    ProData myData = arrayList.get(index);
                    ProData proData = arrayList.get(proindex);

                    ArrayList<BarEntry> g1 = new ArrayList<>();//나의데이터
                    g1.add(new BarEntry(myData.left, 0));
                    g1.add(new BarEntry(myData.right, 1));

                    ArrayList<BarEntry> g2 = new ArrayList<>();//나의데이터
                    g2.add(new BarEntry(proData.left, 0));
                    g2.add(new BarEntry(proData.right, 1));

                    BarDataSet barDataSet1 = new BarDataSet(g1, "나의데이터");
                    barDataSet1.setColor(Color.rgb(33,107,61));
                    //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                    BarDataSet barDataSet2 = new BarDataSet(g2, "선수데이터");
                    barDataSet2.setColor(Color.rgb(255,94,0));
                    //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

                    ArrayList<BarDataSet> dataset = new ArrayList<>();
                    dataset.add(barDataSet1);
                    dataset.add(barDataSet2);

                    BarData data = new BarData(labels, dataset);
                    // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                    barChart.setData(data);
                    barChart.invalidate();
                    //barChart.animateY(5000);

                    ++index;
                    ++proindex;
                    handler.sendEmptyMessageDelayed(0, 1); // 메세지 값 설정 필요
                }
            }
        };

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        index = 0;
        handler.sendEmptyMessage(0);

        final MediaController mediaController = new MediaController(this);
        videoView.requestFocus();

        //videoView.setVideoURL(url);
        videoView.setMediaController(mediaController);
        String path = intent.getExtras().getString("uri");
                //Environment.getExternalStorageDirectory().getAbsolutePath();+"/DCIM/Camera/20170303_134429.mp4"
        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
        videoView.setVideoPath(path);
        videoView.start();

    }


}
