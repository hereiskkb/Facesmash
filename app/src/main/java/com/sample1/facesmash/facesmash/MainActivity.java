package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to play video
        VideoView videoView = (VideoView)findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splashscreen));
        videoView.requestFocus();
        videoView.start();

        //delay to start homepage activity
        final Thread mThread = new Thread(){
            @Override
            public void run() {
                try
                {
                    sleep(4000);
                    Intent intent = new Intent(MainActivity.this,Home.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
    }
}
