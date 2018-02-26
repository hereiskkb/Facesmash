package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class gallery extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

        @Override
        public void onBackPressed() {
//on pressing back button goes to home activity
            finish();
        }

    public void happy1(View view) {

        Intent i = new Intent();
        setResult(1, i);
        finish();

    }

    public void happy2(View view) {
        Intent i = new Intent();
        setResult(2, i);
        finish();
    }

    public void sad1(View view) {
        Intent i = new Intent();
        setResult(3, i);
        finish();
    }

    public void sad2(View view) {
        Intent i = new Intent();
        setResult(4, i);
        finish();
    }

    public void scared1(View view) {
        Intent i = new Intent();
        setResult(5, i);
        finish();
    }

    public void scared2(View view) {
        Intent i = new Intent();
        setResult(6, i);
        finish();
    }

    public void angry1(View view) {
        Intent i = new Intent();
        setResult(7, i);
        finish();
    }

    public void angry2(View view) {
        Intent i = new Intent();
        setResult(8, i);
        finish();
    }

    public void neutral1(View view) {
        Intent i = new Intent();
        setResult(9, i);
        finish();

    }
    public void neutral2(View view) {
        Intent i = new Intent();
        setResult(10, i);
        finish();

    }
}





