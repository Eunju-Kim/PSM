package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShowMyVideo extends AppCompatActivity {

    HttpURLConnection connection;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayList<String> urlList;
    ArrayAdapter adapter;
    ImageButton home1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_my_video);

        Intent intent = getIntent();
        final int club = intent.getExtras().getInt("club");

        home1 = (ImageButton)findViewById(R.id.home1);
        home1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(it);
            }
        });
        listView = (ListView)findViewById(R.id.listview);
        arrayList = new ArrayList<String>();
        urlList = new ArrayList<String>();
        RequestTask requestTask = new RequestTask();
        String urls = "https://cushines.xyz/psm/select_url.php?club=" + String.valueOf(club);
        requestTask.execute(urls);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                String str = (String) parent.getItemAtPosition(position) ;
//                String[] split = str.split("/");
//                String year = split[4].substring(0, 4);
//                String month = split[4].substring(5, 7);
//                String day = split[4].substring(8, 10);
//                String hour = split[4].substring(11, 13);
//                String minute = split[4].substring(14, 16);
//                String sec = split[4].substring(17, 19);
//                String text = year + "년 " +month + "월 "+ day + "일 " + hour + "시 " + minute + "분 " + sec + "초 ";

               //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplication(),PracticeSwing.class);
                intent.putExtra("uri",urlList.get(position));
                startActivity(intent);
            }
        }) ;

    }
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
                for (int i = 0; i < responseArray.length(); i++) {
                    try {
                        JSONObject responseJSON = (JSONObject)responseArray.get(i);
                        String url = responseJSON.getString("URL");
                        urlList.add(url);
                        String[] split = url.split("/");
                        String year = split[4].substring(0, 4);
                        String month = split[4].substring(5, 7);
                        String day = split[4].substring(8, 10);
                        String hour = split[4].substring(11, 13);
                        String minute = split[4].substring(14, 16);
                        String sec = split[4].substring(17, 19);
                        String text = year + "년 " +month + "월 "+ day + "일 " + hour + "시 " + minute + "분 " + sec + "초 ";
                        arrayList.add(text);
                       // resultURL = arrayList.get(0);
                        //      Toast.makeText(getApplicationContext(), resultURL,Toast.LENGTH_LONG ).show();
                        //      Log.d("resultURL", resultURL);
                        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList) ;
                        listView.setAdapter(adapter) ;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
