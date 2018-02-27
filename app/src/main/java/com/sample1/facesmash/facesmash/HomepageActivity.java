package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class HomepageActivity extends AppCompatActivity {
    private boolean bb;
    ImageView imageView;
    private static final int REQUEST_TAKE_PHOTO = 1888;
    private static final int REQUEST_SELECT_IMAGE = 1;

    // The URI of photo taken with camera
    private Uri mUriPhotoTaken;

    // The URI of gallery image
    private Uri mImageUri;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        imageView = findViewById(R.id.imageView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    mImageUri = data.getData();
                   mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mImageUri, getContentResolver());
                    if (mBitmap != null) {
                        imageView.setImageBitmap(mBitmap);
                    }
                }
                break;

            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mUriPhotoTaken, getContentResolver());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageView.setImageBitmap(mBitmap);
                }
                break;


            default:
                break;
        }
    }

    public void menu(View v)
    {
        startActivity(new Intent(this,Main2Activity.class));
    }

    public void gallery(View v)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    public void cam(View v)
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File file = File.createTempFile("IMG_", ".jpg", storageDir);
            mUriPhotoTaken = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            //setInfo(e.getMessage());
        }
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
