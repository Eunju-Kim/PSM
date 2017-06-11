package com.example.eunju.perfectswingmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kangho on 2017. 5. 8..
 */

public class ShowCamera extends AppCompatActivity implements SurfaceHolder.Callback{

    private TimerTask m_Task;
    private Timer m_timer;
    private CameraDevice camera;
    private SurfaceView mCameraView;
    private SurfaceHolder mCameraHolder;
    private Camera mCamera;
    private Button mStart;
    private boolean recording = false;
    private MediaRecorder recorder;
    private static String EXTERNAL_STORAGE_PATH = "";
    private static String filename = "";
    private String fileuri="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_camera);

        Intent intent = getIntent();
        final int club = intent.getExtras().getInt("club");

        mCameraView = (SurfaceView)findViewById(R.id.cameraView);
        mCamera = Camera.open();

        // surfaceview setting
        mCameraHolder = mCameraView.getHolder();
        mCameraHolder.addCallback(this);
        mCameraHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        m_Task = new TimerTask() {
            @Override
            public void run() {


                if (recorder == null)
                    return;
                // 녹화 중지
                recorder.stop();
                // 영상 재생에 필요한 메모리를 해제한다.
                recorder.release();
                recorder = null;

                ContentValues values = new ContentValues(10);

                //values.put(MediaStore.MediaColumns.TITLE, "RecordedVideo");
                values.put(MediaStore.Audio.Media.ALBUM, "Video Album");
                values.put(MediaStore.Audio.Media.ARTIST, "Mike");
                values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Video");
                values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                values.put(MediaStore.Audio.Media.DATA, filename);

                Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

                if (videoUri == null) {
                    Log.d("SampleVideoRecorder", "Video insert failed.");
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), PracticeSwing.class);
                intent.putExtra("club", club);
                intent.putExtra("uri",videoUri.toString());
                startActivity(intent);
                finish();
            }
        };
        m_timer = new Timer();
        m_timer.schedule(m_Task, 3000);

    }
    public void surfaceCreated(SurfaceHolder holder) {
        /*try {
            if (mCamera == null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
        }*/
        try {
            // 녹화 시작을 위해  MediaRecorder 객체 recorder를 생성한다.
            if (recorder == null) {

                recorder = new MediaRecorder();
            }
            // 오디오와영상 입력 형식 설정
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            // 오디오와영상 인코더 설정
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

            // 저장될 파일 지정
            filename = createFilename();
            recorder.setOutputFile(filename);

            // 녹화도중에 녹화화면을 뷰에다가 출력하게 해주는 설정
            recorder.setPreviewDisplay(holder.getSurface());

            // 녹화 준비,시작
            recorder.prepare();
            recorder.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            recorder.release();
            recorder = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        /*// View 가 존재하지 않을 때
        if (mCameraHolder.getSurface() == null) {
            return;
        }

        // 작업을 위해 잠시 멈춘다
        try
            mCamera.stopPreview();
        } catch (Exception e) {
            // 에러가 나더라도 무시한다.
        }

        // 카메라 설정을 다시 한다.
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        mCamera.setParameters(parameters);

        // View 를 재생성한다.
        try {
            mCamera.setPreviewDisplay(mCameraHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }
    protected void onDestroy() {
        Log.i("test", "onDestroy()dfd");
        m_timer.cancel();





        super.onDestroy();
    }

    private String createFilename() {
        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);

        String state = Environment.getExternalStorageState();
        // Environment.MEDIA_MOUNTED 외장메모리가 마운트 flog
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "외장 메모리가 마운트 되지않았습니다.", Toast.LENGTH_LONG).show();
        } else {
            EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        String newFilename = "";
        if (EXTERNAL_STORAGE_PATH == null || EXTERNAL_STORAGE_PATH.equals("")) {
            // 내장 메모리를 사용합니다.
            newFilename = formatDate + ".mp4";
        } else {
            // 외장 메모리를 사용합니다.
            newFilename = EXTERNAL_STORAGE_PATH + "/" + formatDate + ".mp4";
        }
        Toast.makeText(getApplicationContext(), newFilename, Toast.LENGTH_LONG).show();
        return newFilename;
    }
}