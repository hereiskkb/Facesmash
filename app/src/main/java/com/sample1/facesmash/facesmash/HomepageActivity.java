package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class HomepageActivity extends AppCompatActivity {
    private boolean bb;
    ImageView imageView;
    int k;
    int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Button cam =(Button) findViewById(R.id.cam);
        imageView = (ImageView)findViewById(R.id.imageView);




        //camera button c

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
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //displays image captured on home page
        if (requestCode == 0) {
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
        } else if (requestCode == 1) {

            if (resultCode == 1) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.happy1));
                k = 1;
            } else if (resultCode == 2) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.happy2));
                k = 1;
            }else if (resultCode == 3) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.sad1));
                k = 2;
            }else if (resultCode == 4) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.sad2));
                k = 2;
            }
            else if (resultCode == 5) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.scared1));
                k = 4;
            }else if (resultCode == 6) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.scared2));
                k = 4;
            }else if (resultCode == 7) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.angry1));
                k = 3;
            }else if (resultCode == 8) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.angry2));
                k = 3;
            }else if (resultCode == 9) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.neutral1));
                k = 5;
            }else if (resultCode == 2) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.neutral2));
                k = 5;
            }
            //faking to process XD ..with a delay of 3s
            Toast.makeText(this, "processing.....", Toast.LENGTH_LONG).show();
            final Thread hThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);

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
    }




    public void menu(View v)
    {
        startActivity(new Intent(this,Main2Activity.class));
    }

    public void gallery(View v)
    {
        Intent intent = new Intent(HomepageActivity.this,gallery.class);
        startActivityForResult(intent,1);

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
