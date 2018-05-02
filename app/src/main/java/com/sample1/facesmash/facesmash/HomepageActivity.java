package com.sample1.facesmash.facesmash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import static java.lang.System.exit;

public class HomepageActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {


    private boolean bb;
    ImageView image;


    private static final int REQUEST_TAKE_PHOTO = 1888;
    private static final int REQUEST_SELECT_IMAGE = 1;

    // The URI of photo taken with camera
    private Uri mUriPhotoTaken;

    // The URI of gallery image
    private Uri mImageUri;

    private Bitmap mBitmap;
    int k;
    float l = 0;
    int option = 1;
    float anger, happy, sad, fear, neutral;
    float age = 0;
    String title;
    ProgressDialog mProgressDialog;
    Button resultbutton;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    int detectionResult1;
    String Age1 ;
    String Gender1 ;
    float male , female;
    float  n1 , n2 , n3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        resultbutton = (Button) findViewById(R.id.button2);
        resultbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent results = new Intent(HomepageActivity.this, crowd.class);
                results.putExtra("detectionResult", detectionResult1);
                results.putExtra("anger", anger);
                results.putExtra("happy", happy);
                results.putExtra("sad", sad);
                results.putExtra("fear", fear);
                results.putExtra("neutral", neutral);
                results.putExtra("male", male);
                results.putExtra("female", female);
                results.putExtra("age", age);
                results.putExtra("n1", n1);
                results.putExtra("n2", n2);
                results.putExtra("n3", n3);
                startActivity(results);
            }
        });

        option = getIntent().getExtras().getInt("choice");


        image = findViewById(R.id.image1234);
        if (option == 1) {
            resultbutton.setVisibility(View.GONE);
            title = "Music Player";
            image.setImageResource(R.drawable.mmark);
        } else if (option == 2) {
            resultbutton.setVisibility(View.GONE);
            title = "Crowd Management";
            image.setImageResource(R.drawable.cmark);
        } else if (option == 3) {
             resultbutton.setVisibility(View.GONE);
            title = "Security";
            image.setImageResource(R.drawable.smark);
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please Wait");
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativelayout1);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        finish();
                        break;

                    case R.id.gallery:
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.tutorial:
                        Intent i = new Intent(HomepageActivity.this, under_construction.class);
                        startActivity(i);
                        break;
                    case R.id.settings:
                        Intent sett = new Intent(HomepageActivity.this, under_construction.class);
                        startActivity(sett);
                        break;

                    case R.id.about:
                        Intent a = new Intent(HomepageActivity.this, under_construction.class);
                        startActivity(a);
                        break;
                }
                return false;
            }
        });
        setUpToolbar();


    }

    private void setUpToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
        private boolean mSucceed = true;


        @Override
        protected void onPreExecute() {
            mProgressDialog.setTitle("Detecting");
            mProgressDialog.setMessage("Please Wait");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (isCancelled())
                        mProgressDialog.dismiss();
                }

                @Override
                public void onFinish() {
                    mProgressDialog.setMessage("Slow Internet Connection....Please Wait");
                }
            }.start();
            new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (isCancelled())
                        mProgressDialog.dismiss();
                }

                @Override
                public void onFinish() {
                    mProgressDialog.setMessage("Its Taking Longer then expected....Please Connect to a Faster Network ");
                }
            }.start();
            new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (isCancelled())
                        mProgressDialog.dismiss();
                }

                @Override
                public void onFinish() {
                    mProgressDialog.cancel();
                }
            }.start();


        }

        @Override
        protected Face[] doInBackground(InputStream... params) {


            FaceServiceClient faceServiceClient = new FaceServiceRestClient(getString(R.string.endpoint), getString(R.string.subscription_key));
            try {
                // Start detection.

                return faceServiceClient.detect(params[0], true, true,
                        new FaceServiceClient.FaceAttributeType[]{
                                FaceServiceClient.FaceAttributeType.Age,
                                FaceServiceClient.FaceAttributeType.Gender,
                                FaceServiceClient.FaceAttributeType.Emotion,
                        });


            } catch (Exception e) {
                Log.d("HI", e.getMessage());
                mSucceed = false;

                return null;
            }
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
                        final DetectionTask d = new DetectionTask();
                        d.execute(inputStream);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (d.getStatus() == AsyncTask.Status.RUNNING)
                                    d.cancel(true);
                            }
                        }, 30000);

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
                        final DetectionTask d = new DetectionTask();
                        d.execute(inputStream);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (d.getStatus() == AsyncTask.Status.RUNNING) {
                                    d.cancel(true);
                                    resultbutton.setVisibility(View.GONE);

                                }
                            }
                        }, 30000);


                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.gallery) {
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


    private void setUiAfterDetection(Face[] result, boolean succeed) {
         Age1 = "";
        Gender1 = "";
        male = 0; female = 0;
        n1 = 0; n2 = 0; n3 = 0;


        mProgressDialog.dismiss();

        String detectionResult = "Emotion(s) Detected:";


        if (succeed) {
            if (result != null) {
                List<Face> faces;

                faces = Arrays.asList(result);
                if (faces.isEmpty()) {
                    resultbutton.setVisibility(View.GONE);
                    NoFacesDetected();
                }
                else {
                    happy = 0;
                    sad = 0;
                    fear = 0;
                    anger = 0;
                    neutral = 0;
                    detectionResult1 = result.length;
                    for (Face face : faces) {
                        Age1 = String.format("%s", face.faceAttributes.age);
                        age = Float.valueOf(Age1);
                        if (age >= 0 && age <= 19)
                            n1++;
                        else if (age >= 20 && age <= 40)
                            n2++;
                        else if (age > 40)
                            n3++;
                        Gender1 = String.format("%s", face.faceAttributes.gender);
                        detectionResult += getEmotion(face.faceAttributes.emotion) + ", ";//adds a comma after every emotion
                        if (Gender1.equals("male")) {
                            male++;
                        } else if (Gender1.equals("female")) {
                            female++;
                        }


                    }

                    detectionResult = detectionResult.substring(0, detectionResult.length() - 2) + ".";//removes last comma and adds a fullstop
                    image.setImageBitmap(ImageHelper.drawFaceRectanglesOnBitmap(
                            mBitmap, result, false));


                    //here we redirect to music player activity with the emotion stored in detection result
                    if (option == 1) {
                        Intent intent = new Intent(HomepageActivity.this, musicpalyer.class);
                        intent.putExtra("key", k);

                        startActivity(intent);
                        Toast.makeText(HomepageActivity.this, detectionResult, Toast.LENGTH_LONG).show();
                    } else if (option == 2)

                    {
                        n1 = n1 / result.length * 100;
                        n2 = n2 / result.length * 100;
                        n3 = n3 / result.length * 100;
                        male = male / result.length * 100;
                        female = female / result.length * 100;

                        happy = happy / result.length * 100;
                        sad = sad / result.length * 100;
                        neutral = neutral / result.length * 100;
                        anger = anger / result.length * 100;
                        fear = fear / result.length * 100;
                        Intent intent = new Intent(HomepageActivity.this, crowd.class);
                        intent.putExtra("detectionResult", detectionResult1);
                        intent.putExtra("anger", anger);
                        intent.putExtra("happy", happy);
                        intent.putExtra("sad", sad);
                        intent.putExtra("fear", fear);
                        intent.putExtra("neutral", neutral);
                        intent.putExtra("male", male);
                        intent.putExtra("female", female);
                        intent.putExtra("age", age);
                        intent.putExtra("n1", n1);
                        intent.putExtra("n2", n2);
                        intent.putExtra("n3", n3);
                        startActivity(intent);
                        resultbutton.setVisibility(View.VISIBLE);
                    }
                }


            }
        }


        mImageUri = null;
        mBitmap = null;
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
            k = 3;

            ++anger;
        } else if (maxNum == emotion.happiness) {
            emotionType = "Happiness";
            k = 1;

            ++happy;
        } else if (maxNum == emotion.contempt) {

            emotionType = "Contempt";

        } else if (maxNum == emotion.disgust)

        {
            emotionType = "Disgust";

        } else if (maxNum == emotion.fear) {
            emotionType = "Fear";
            k = 4;
            ++fear;


        } else if (maxNum == emotion.neutral) {
            emotionType = "Neutral";
            k = 5;
            ++neutral;
        } else if (maxNum == emotion.sadness) {
            emotionType = "Sadness";
            k = 2;
            ++sad;
        } else if (maxNum == emotion.surprise) {
            emotionType = "Surprise";
        }

        return emotionType;
    }


    @Override
    protected void onResume() {
        super.onResume();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);

        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {


        runTask(isConnected);


    }


    public void runTask(final boolean isConnected) {

        if (!isConnected) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("No Internet Connection");
            builder.setMessage("You need to have Mobile Data or wifi to access this.");


            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    boolean isConnectedRetry = ConnectivityReceiver.isConnected();
                    runTask(isConnectedRetry);
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            final Toast toast = Toast.makeText(getApplicationContext(), "Network Unavailable!", Toast.LENGTH_SHORT);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 700);
        }

    }

    public void NoFacesDetected() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Sorry, No Faces Detected");
        builder.setMessage("Please select image with human faces, also ensure image quality is high for efficient results");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();


        dialog.show();

    }


}

