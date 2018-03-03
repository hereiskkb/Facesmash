package com.sample1.facesmash.facesmash;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.microsoft.projectoxford.face.contract.Face;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomepageActivity extends AppCompatActivity {


    private boolean bb;
    ImageView image;
    private static final int REQUEST_TAKE_PHOTO = 1888;
    private static final int REQUEST_SELECT_IMAGE = 1;

    // The URI of photo taken with camera
    private Uri mUriPhotoTaken;

    // The URI of gallery image
    private Uri mImageUri;

    private Bitmap mBitmap;

    ProgressDialog mProgressDialog;

    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if(netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
        else return false;
        }

        else
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        if(!isConnected(HomepageActivity.this))
            Toast.makeText(HomepageActivity.this,"No Internet Connection!!!", Toast.LENGTH_SHORT).show();
        image = findViewById(R.id.image1234);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please Wait");
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.gallery:
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
                        break;


                }
                return  false;
            }
        });
        setUpToolbar();

    }

    private void setUpToolbar()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }



    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
        private boolean mSucceed = true;


        @Override
        protected Face[] doInBackground(InputStream... params) {
            FaceServiceClient faceServiceClient = new FaceServiceRestClient(getString(R.string.endpoint), getString(R.string.subscription_key));
            try {
                // Start detection.
                return faceServiceClient.detect(params[0], true, true,
                        new FaceServiceClient.FaceAttributeType[]{
                                FaceServiceClient.FaceAttributeType.Emotion,
                        });
            } catch (Exception e) {
                Log.d("HI", e.getMessage());
                mSucceed = false;
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.setMessage("Detecting");
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Face[] result) {
            setUiAfterDetection(result, mSucceed);
        }

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    mImageUri = data.getData();
                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mImageUri, getContentResolver());
                    if (mBitmap != null) {
                        image.setImageBitmap(mBitmap);
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
                        new DetectionTask().execute(inputStream);
                    }
                }
                break;

            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mUriPhotoTaken, getContentResolver());
                    if (mBitmap != null) {
                        image.setImageBitmap(mBitmap);
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
                        new DetectionTask().execute(inputStream);
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.gallery)
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_SELECT_IMAGE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void cam(View v) {
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

    private void setUiAfterDetection(Face[] result, boolean succeed) {
        mProgressDialog.dismiss();
        if(!isConnected(HomepageActivity.this))
            Toast.makeText(HomepageActivity.this,"No Internet Connection!!!", Toast.LENGTH_SHORT).show();
        else {
            String detectionResult = "Emotion(s) Detected:";
            if (succeed) {
                if (result != null) {
                    List<Face> faces;

                    faces = Arrays.asList(result);
                    if (faces.isEmpty())
                        detectionResult = "No Faces Detected";
                    else {
                        for (Face face : faces) {
                            detectionResult += getEmotion(face.faceAttributes.emotion) + ", ";//adds a comma after every emotion
                        }
                        detectionResult = detectionResult.substring(0, detectionResult.length() - 2) + ".";//removes last comma and adds a fullstop
                        image.setImageBitmap(ImageHelper.drawFaceRectanglesOnBitmap(
                                mBitmap, result, false));
                    }
                }
            }
            //here we redirect to music player activity with the emotion stored in detection result
            Toast.makeText(HomepageActivity.this, detectionResult, Toast.LENGTH_LONG).show();

            mImageUri = null;
            mBitmap = null;
        }
    }

    private String getEmotion(Emotion emotion)

    {
        List<Double> list = new ArrayList<>();
        String emotionType = "";
        list.add(emotion.anger);
        list.add(emotion.happiness);
        list.add(emotion.contempt);
        list.add(emotion.disgust);
        list.add(emotion.fear);
        list.add(emotion.neutral);
        list.add(emotion.sadness);
        list.add(emotion.surprise);

        Collections.sort(list);

        double maxNum = list.get(list.size() - 1);
        if (maxNum == emotion.anger) {
            emotionType = "Anger";
            int k = 3;
            Intent intent = new Intent(HomepageActivity.this, musicpalyer.class);
            intent.putExtra("key", k);
            startActivity(intent);
        } else if (maxNum == emotion.happiness) {
            emotionType = "Happiness";
            int k = 1;
            Intent intent = new Intent(HomepageActivity.this, musicpalyer.class);
            intent.putExtra("key", k);
            startActivity(intent);
        }
        else if (maxNum == emotion.contempt)
        {

            emotionType = "Contempt";

        } else if (maxNum == emotion.disgust)

        {
            emotionType = "Disgust";

        }
        else if (maxNum == emotion.fear)
        {
            emotionType = "Fear";
            int k=4;
            Intent intent = new Intent(HomepageActivity.this,musicpalyer.class);
            intent.putExtra("key",k);
            startActivity(intent);

        }
        else if (maxNum == emotion.neutral)
        {
            emotionType = "Neutral";
            int k = 5;
            Intent intent = new Intent(HomepageActivity.this, musicpalyer.class);
            intent.putExtra("key", k);
            startActivity(intent);
        }
        else if (maxNum == emotion.sadness)
        {
            emotionType = "Sadness";
            int k = 2;
            Intent intent = new Intent(HomepageActivity.this, musicpalyer.class);
            intent.putExtra("key", k);
            startActivity(intent);
        }
        else if (maxNum == emotion.surprise)
        {
            emotionType = "Surprise";
        }
        else
            {
            emotionType = "Neutral";
            int k = 5;
            Intent intent = new Intent(HomepageActivity.this, musicpalyer.class);
            intent.putExtra("key", k);
            startActivity(intent);
        }

       return emotionType;
    }
}
