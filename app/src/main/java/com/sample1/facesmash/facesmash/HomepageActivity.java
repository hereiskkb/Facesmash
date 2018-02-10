package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class HomepageActivity extends AppCompatActivity {
    private boolean bb;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //camera button code
        Button cam =(Button)findViewById(R.id.cam);
        imageView = (ImageView)findViewById(R.id.imageView);

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //opens default camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //displays image captured on home page
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        //faking to process XD ..with a delay of 3s
        Toast.makeText(this, "processing.....", Toast.LENGTH_LONG).show();
        final Thread hThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    int k = 1;
                    Intent intent = new Intent(HomepageActivity.this, musicpalyer.class);
                    intent.putExtra("key", k);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        hThread.start();



    }



    public void menu(View v)
    {
        startActivity(new Intent(this,Main2Activity.class));
    }

    public void gallery(View v)
    {
        startActivity(new Intent(this,HomeActivity.class));
    }


    @Override
    //pressing back button twice to exit
    public void onBackPressed() {
        if(!bb)
        {
            Toast.makeText(this,"press back again to exit",Toast.LENGTH_LONG).show();
            bb=true;
        }
        else {
            super.onBackPressed();
        }
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                bb=false;
            }
        }.start();
    }

}
