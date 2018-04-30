package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;


public class Home extends AppCompatActivity {
    int choice;
    private boolean bb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }


    public void music(View view) {
        choice = 1;
        Intent intent = new Intent(Home.this, HomepageActivity.class);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }


    public void crowd(View view) {
        choice = 2;
        Intent intent = new Intent(Home.this, HomepageActivity.class);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }

    public void security(View view) {
        choice = 3;
        Intent intent = new Intent(Home.this, HomepageActivity.class);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }

    @Override
    //pressing back button twice to exit
    public void onBackPressed() {
        if (!bb) {
            final Toast toast = Toast.makeText(getApplicationContext(), "press back again to exit", Toast.LENGTH_SHORT);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 800);
            bb = true;
        } else {
            super.onBackPressed();
        }
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                bb = false;
            }
        }.start();
    }


}