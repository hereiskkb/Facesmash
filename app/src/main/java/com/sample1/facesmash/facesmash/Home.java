package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
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
            Toast.makeText(this, "press back again to exit", Toast.LENGTH_LONG).show();
            //not saving pictures and deleting whole folder
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
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