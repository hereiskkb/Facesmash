package com.sample1.facesmash.facesmash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class crowd extends AppCompatActivity {
    TextView thappy, tsad, tangry, tfear, tneutral, tmale, tfemale, tage10, tage20, tage40;
    ProgressBar phappy, psad, pangry, pfear, pneutral, pmale, pfemale, page10, page20, page40;
    int shappy = 0;
    int ssad = 0;
    int sangry = 0;
    int sfear = 0;
    int sneutral = 0;
    int smale = 0;
    int sfemale = 0;
    int sage10 = 0;
    int sage20 = 0;
    int sage40 = 0;

    private Handler handler_happy = new Handler();
    private Handler handler_sad = new Handler();
    private Handler handler_angry = new Handler();
    private Handler handler_fear = new Handler();
    private Handler handler_neutral = new Handler();
    private Handler handler_male = new Handler();
    private Handler handler_female = new Handler();
    private Handler handler_age10 = new Handler();
    private Handler handler_age20 = new Handler();
    private Handler handler_age40 = new Handler();
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd);
        int no_faces = getIntent().getExtras().getInt("detectionResult");
        final int anger = (int) getIntent().getExtras().getFloat("anger");
        final int happy = (int) getIntent().getExtras().getFloat("happy");
        final int sad = (int) getIntent().getExtras().getFloat("sad");
        final int fear = (int) getIntent().getExtras().getFloat("fear");
        final int neutral = (int) getIntent().getExtras().getFloat("neutral");
        final int male = (int) getIntent().getExtras().getFloat("male");
        final int female = (int) getIntent().getExtras().getFloat("female");
        final int n1 = (int) getIntent().getExtras().getFloat("n1");
        final int n2 = (int) getIntent().getExtras().getFloat("n2");
        final int n3 = (int) getIntent().getExtras().getFloat("n3");
        String emo = "";

        List<Integer> list = new ArrayList<>();
        String emotionType = "";
        list.add(anger);
        list.add(happy);
        list.add(fear);
        list.add(neutral);
        list.add(sad);
        Collections.sort(list);
        int max = list.get(list.size() - 1);
        if (max == anger)
            emo = "ANGRY";
        else if (max == happy)
            emo = "HAPPY";
        else if (max == sad)
            emo = "SAD";
        else if (max == fear)
            emo = "SCARED";
        else if (max == neutral)
            emo = "NEUTRAL";


        String message = "GENERAL EMOTION : " + emo;
        TextView emotion = (TextView) findViewById(R.id.generalemotion);
        emotion.setText(message);

        String msg1 = "PEOPLE COUNT            : " + no_faces;
        TextView people = (TextView) findViewById(R.id.peoplecount);
        people.setText(msg1);


        thappy = (TextView) findViewById(R.id.happy);
        phappy = (ProgressBar) findViewById(R.id.progressBar_happy);


        psad = (ProgressBar) findViewById(R.id.progressBar_sad);
        tsad = (TextView) findViewById(R.id.sad);

        tangry = (TextView) findViewById(R.id.angry);
        pangry = (ProgressBar) findViewById(R.id.progressBar_angry);
        tfear = (TextView) findViewById(R.id.scared);
        pfear = (ProgressBar) findViewById(R.id.progressBar_scared);
        tneutral = (TextView) findViewById(R.id.neutral);
        pneutral = (ProgressBar) findViewById(R.id.progressBar_neutal);

        tmale = (TextView) findViewById(R.id.male_perc);
        pmale = (ProgressBar) findViewById(R.id.progressBar_male);

        tfemale = (TextView) findViewById(R.id.female_perc);
        pfemale = (ProgressBar) findViewById(R.id.progressBar_female);
        tage10 = (TextView) findViewById(R.id.age10);
        page10 = (ProgressBar) findViewById(R.id.progressBar_age10);
        tage20 = (TextView) findViewById(R.id.age20);
        page20 = (ProgressBar) findViewById(R.id.progressBar_age20);

        tage40 = (TextView) findViewById(R.id.age40);
        page40 = (ProgressBar) findViewById(R.id.progressBar_age40);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (smale <= male) {

                    handler_male.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pmale.setProgress(smale);
                            pmale.setSecondaryProgress(100);
                        }
                    });
                    if(male<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(male>3&&male<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (male>10 && male<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    smale++;
                }
                smale = 0;
            }

        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (sfemale <= female) {

                    handler_female.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pfemale.setProgress(sfemale);
                            pfemale.setSecondaryProgress(100);

                        }
                    });
                    if(female<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(female>3&&female<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (female>10 && female<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sfemale++;
                }
                sfemale = 0;
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (sage10 <= n1) {

                    handler_age10.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            page10.setProgress(sage10);
                            page10.setSecondaryProgress(100);

                        }
                    });
                    if(n1<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                   else if(n1>3&&n1<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (n1>10 && n1<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sage10++;
                }
                sage10 = 0;
            }
        }).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (sage20 <= n2) {

                    handler_age20.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            page20.setProgress(sage20);
                            page20.setSecondaryProgress(100);

                        }
                    });
                    if(n2<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(n2>3&&n2<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (n2>10 && n2<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    sage20++;
                }
                sage20=0;
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (sage40 <= n3) {

                    handler_age40.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            page40.setProgress(sage40);
                            page40.setSecondaryProgress(100);

                        }
                    });
                    if(n3<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(n3>3&&n3<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (n3>10 && n3<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    sage40++;
                }
                sage40=0;
            }
        }).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (shappy <= happy) {

                    handler_happy.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            phappy.setProgress(shappy);
                            phappy.setSecondaryProgress(100);
                            thappy.setText(shappy + "%");
                        }
                    });
                    if(happy<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(happy>3&&happy<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (happy>10 && happy<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    shappy += 1;
                }
                shappy=0;
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (ssad <= sad) {

                    handler_sad.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            psad.setProgress(ssad);
                            psad.setSecondaryProgress(100);
                            tsad.setText(ssad + "%");
                        }
                    });
                    if(n1<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(sad>3&&sad<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (sad>10 && sad<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ssad += 1;
                }
                ssad=0;
            }
        }).start();


        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (sangry <= anger) {

                    handler_angry.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pangry.setProgress(sangry);
                            pangry.setSecondaryProgress(100);
                            tangry.setText(sangry + "%");
                        }
                    });
                    if(anger<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(anger>3&&anger<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (anger>10 && anger<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sangry += 1;
                }
                sangry=0;
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (sfear <= fear) {

                    handler_fear.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pfear.setProgress(sfear);
                            pfear.setSecondaryProgress(100);
                            tfear.setText(sfear + "%");
                        }
                    });
                    if(fear<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(fear>3&&fear<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (fear>10 && fear<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sfear += 1;
                }
                sfear=0;
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (sneutral <= neutral) {

                    handler_neutral.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pneutral.setProgress(sneutral);
                            pneutral.setSecondaryProgress(100);
                            tneutral.setText(sneutral + "%");
                        }
                    });
                    if(neutral<3)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }



                    else if(neutral>3&&neutral<10) {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (neutral>10 && neutral<20)
                    {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {

                            // Just to display the progress slowly
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sneutral += 1;
                }
                sneutral=0;
            }
        }).start();

        tmale.setText(male + "%");
        tfemale.setText(female + "%");
        tage40.setText(n3 + "%");
        tage20.setText(n2 + "%");
        tage10.setText(n1 + "%");


        save = (Button) findViewById(R.id.floatingActionButton);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save.setVisibility(View.GONE);
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
                save.setVisibility(View.VISIBLE);

            }
        });

    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Facesmash");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        //  Log.i(TAG, "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(crowd.this, "Results Saved to Gallery", Toast.LENGTH_SHORT).show();
    }



}



