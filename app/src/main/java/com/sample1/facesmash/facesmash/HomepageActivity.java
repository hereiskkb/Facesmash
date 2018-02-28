package com.sample1.facesmash.facesmash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        image = findViewById(R.id.image1234);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please Wait");
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

    public void menu(View v) {
        startActivity(new Intent(this, Main2Activity.class));
    }

    public void gallery(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
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
        } else {
            detectionResult = "No Faces Detected";
        }
        //here we redirect to music player activity with the emotion stored in detection result
        Toast.makeText(HomepageActivity.this, detectionResult, Toast.LENGTH_LONG).show();

        mImageUri = null;
        mBitmap = null;
    }

    private String getEmotion(Emotion emotion) {
        String emotionType = "";
        double emotionValue = 0.0;
        if (emotion.anger > emotionValue) {
            emotionValue = emotion.anger;
            emotionType = "Anger";
        }
        if (emotion.contempt > emotionValue) {
            emotionValue = emotion.contempt;
            emotionType = "Contempt";
        }
        if (emotion.disgust > emotionValue) {
            emotionValue = emotion.disgust;
            emotionType = "Disgust";
        }
        if (emotion.fear > emotionValue) {
            emotionValue = emotion.fear;
            emotionType = "Fear";
        }
        if (emotion.happiness > emotionValue) {
            emotionValue = emotion.happiness;
            emotionType = "Happiness";
        }
        if (emotion.neutral > emotionValue) {
            emotionValue = emotion.neutral;
            emotionType = "Neutral";
        }
        if (emotion.sadness > emotionValue) {
            emotionValue = emotion.sadness;
            emotionType = "Sadness";
        }
        if (emotion.surprise > emotionValue) {
            emotionValue = emotion.surprise;
            emotionType = "Surprise";
        }
        return emotionType;
    }
}
