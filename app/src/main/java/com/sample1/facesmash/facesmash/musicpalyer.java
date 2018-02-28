package com.sample1.facesmash.facesmash;

import android.app.FragmentManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class musicpalyer extends AppCompatActivity {
    Button playbtn;
    private boolean bb;
    SeekBar positionBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    int totalTime;

    int k;

    public void stop()
    {
        if (mp!=null){
            mp.release();
            mp=null;
        }
    }
    @Override
    public void onBackPressed() {
        //stops the music and goes back to previous activity
        stop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on receiving the value of k from home activty plays music accordingly
        setContentView(R.layout.activity_musicpalyer);
        k=getIntent().getExtras().getInt("key");
        playbtn = (Button)findViewById(R.id.playbtn);
        elapsedTimeLabel=(TextView)findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel=(TextView)findViewById(R.id.remainingTimeLabel);

        //happy
     if(k==1)
     {
         mp = MediaPlayer.create(this, R.raw.a);
         mp.start();
     }

     //sad
        else if(k==2) {
            mp = MediaPlayer.create(this, R.raw.b);
            mp.start();        }
     //angry
     else if(k==3) {
         mp = MediaPlayer.create(this, R.raw.c);
         mp.start();
     }

     //fear
     else if(k==4) {
         mp = MediaPlayer.create(this, R.raw.d);
         mp.start();
     }

     //neutral
     else if(k==5) {
         mp = MediaPlayer.create(this, R.raw.e);
         mp.start();
     }

//to manage seek bar according music time duration
    mp.seekTo(0);
    totalTime = mp.getDuration();
    positionBar=(SeekBar)findViewById(R.id.positionBar);
    positionBar.setMax(totalTime);
    positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                mp.seekTo(progress);
                positionBar.setProgress(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });
    new Thread(new Runnable() {
        @Override
        public void run() {
            while (mp != null) {

                try{
                    Message msg = new Message();
                    msg.what = mp.getCurrentPosition();
                   handler.sendMessage(msg);
                    Thread.sleep(1000);

                }catch(InterruptedException e){}
            }
        }
    }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int currentposition = msg.what;
            positionBar.setProgress(currentposition);
            String elapsedTime = createTimeLabel(currentposition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime-currentposition);
            remainingTimeLabel.setText("- " + remainingTime);

        }
    };

    public String createTimeLabel(int time){
        String timeLabel = "";
        int min = time/1000/60;
        int sec = time/1000%60;

        timeLabel = min + ":";
        if(sec < 10)timeLabel += "0";
        timeLabel +=sec;

        return timeLabel;
    }

    //to change play to pause button or vice-versa on pressed
    public void playbtn(View v)
    {
        if(mp.isPlaying()) {
            mp.pause();
            playbtn.setBackgroundResource(R.drawable.play);
        }
        else
        { mp.start();
            playbtn.setBackgroundResource(R.drawable.pause);

        }
    }
}
