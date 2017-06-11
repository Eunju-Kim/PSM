package com.example.eunju.perfectswingmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Eunju on 2017-04-24.
 */

public class LookRightPosture extends ActionBarActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);

        VideoView vv = (VideoView) findViewById(R.id.videoView);
        MediaController cont = new MediaController(LookRightPosture.this);

        Intent it = getIntent();
        String uri = it.getExtras().getString("uri");
        Uri uri_path = Uri.parse(uri);


        cont.setAnchorView(vv);
        vv.setMediaController(cont);
        vv.setVideoURI(uri_path);
        vv.requestFocus();
        vv.start();
    }
}
