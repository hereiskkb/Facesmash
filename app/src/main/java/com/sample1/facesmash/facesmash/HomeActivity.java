package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private boolean bb;
    private MediaPlayer mplayer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }

    //stops music
    public void stop()
    {
        if (mplayer!=null){
            mplayer.release();
            mplayer=null;
        }
    }
    //when happy
    public void a(View v)
    {
        //passing value of k to music player
        int k=1;
        Intent intent = new Intent(HomeActivity.this,musicpalyer.class);
        intent.putExtra("key",k);
        startActivity(intent);
    }

    //when sad
    public void b(View v)
    {   Toast.makeText(this,"sad",Toast.LENGTH_LONG).show();
        int k=2;
        Intent intent = new Intent(HomeActivity.this,musicpalyer.class);
        intent.putExtra("key",k);
        startActivity(intent);
    }

    //angry
    public void c(View v)
    {   Toast.makeText(this,"angry",Toast.LENGTH_LONG).show();
        int k=3;
        Intent intent = new Intent(HomeActivity.this,musicpalyer.class);
        intent.putExtra("key",k);
        startActivity(intent);
    }

    //fear
    public void d(View v)
    {
        Toast.makeText(this,"fear",Toast.LENGTH_LONG).show();
        int k=4;
        Intent intent = new Intent(HomeActivity.this,musicpalyer.class);
        intent.putExtra("key",k);
        startActivity(intent);;
    }

    //neutral
    public void e(View v)
    {    Toast.makeText(this,"neutral",Toast.LENGTH_LONG).show();
        int k=5;
        Intent intent = new Intent(HomeActivity.this,musicpalyer.class);
        intent.putExtra("key",k);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//on pressing back button goes to home activity
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

   //settings
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.settings_id)
        {
            startActivity(new Intent(this,Main2Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
