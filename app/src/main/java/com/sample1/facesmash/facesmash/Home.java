package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;


public class Home extends AppCompatActivity {
           int choice;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_home);

            }


    public void music(View view)
    {
        choice=1;
        Intent intent = new Intent(Home.this, HomepageActivity.class);
        intent.putExtra("choice",choice);
    }


    public void crowd(View view) {
        choice=2;
        Intent intent = new Intent(Home.this, HomepageActivity.class);
        intent.putExtra("choice",choice);
    }

    public void security(View view)
    {
        choice=1;
        Intent intent = new Intent(Home.this,HomepageActivity.class);
        intent.putExtra("choice",choice);
    }
}
