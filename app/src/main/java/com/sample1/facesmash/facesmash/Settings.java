package com.sample1.facesmash.facesmash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.contract.Emotion;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    Toolbar toolbar;

    Spinner spinnerHappy, spinnerSad, spinnerFear, spinnerAngry, spinnerNeutral;

    String[] Emotions = {
            "Choose Song Emotion" ,
            "HAPPY",
            "SAD",
            "ANGRY",
            "FEAR",
            "NEUTRAL"
    };

    int count, count1, count2, count3, count4;

    String item = "HAPPY", item1 = "SAD", item2 = "ANGRY", item3 = "FEAR", item4 = "NEUTRAL";
    Boolean serverstate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        count = 0;
        count1 = 0;
        count2 = 0;
        count3 = 0;
        count4 = 0;

        Switch server = (Switch) findViewById(R.id.server);


        SharedPreferences settings = getSharedPreferences("set", Context.MODE_PRIVATE);
        serverstate = settings.getBoolean("serverstate", false);
        String DHAPPY = settings.getString("HAPPY", "");
        String DSAD = settings.getString("SAD", "");
        String DANGRY = settings.getString("ANGRY", "");
        String DFEAR = settings.getString("FEAR", "");
        String DNEUTRAL = settings.getString("NEUTRAL", "");
        if (!DHAPPY.equals("")) {
            TextView tsad = (TextView) findViewById(R.id.thappy);
            tsad.setText(DHAPPY);
        }
        if (!DSAD.equals("")) {
            TextView tsad = (TextView) findViewById(R.id.tsad);
            tsad.setText(DSAD);
        }
        if (!DANGRY.equals("")) {
            TextView tangry = (TextView) findViewById(R.id.tangry);
            tangry.setText(DANGRY);
        }
        if (!DFEAR.equals("")) {
            TextView tfear = (TextView) findViewById(R.id.tfear);
            tfear.setText(DFEAR);
        }
        if (!DNEUTRAL.equals("")) {
            TextView tneutral = (TextView) findViewById(R.id.tneutral);
            tneutral.setText(DNEUTRAL);
        }

        if (serverstate)
            server.setChecked(true);
        else
            server.setChecked(false);




        spinnerHappy = (Spinner) findViewById(R.id.spinner_Happy);
        spinnerSad = (Spinner) findViewById(R.id.spinner_Sad);
        spinnerAngry = (Spinner) findViewById(R.id.spinner_Angry);
        spinnerFear = (Spinner) findViewById(R.id.spinner_Fear);
        spinnerNeutral = (Spinner) findViewById(R.id.spinner_Neutral);


        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, Emotions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHappy.setAdapter(adapter);
        spinnerHappy.setOnItemSelectedListener(onItemSelectedListener);

        spinnerSad.setAdapter(adapter);
        spinnerAngry.setAdapter(adapter);
        spinnerFear.setAdapter(adapter);
        spinnerNeutral.setAdapter(adapter);

        spinnerSad.setOnItemSelectedListener(onItemSelectedListener);
        spinnerAngry.setOnItemSelectedListener(onItemSelectedListener);
        spinnerFear.setOnItemSelectedListener(onItemSelectedListener);
        spinnerNeutral.setOnItemSelectedListener(onItemSelectedListener);


    }

    AdapterView.OnItemSelectedListener onItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    switch (parent.getId()) {
                        case R.id.spinner_Happy:

                            if (count > 0) {
                                if(position>0) {
                                    item = parent.getItemAtPosition(position).toString();
                                    TextView thappy = (TextView) findViewById(R.id.thappy);
                                    thappy.setText(item);

                                }

                            }
                            count++;


                            break;
                        case R.id.spinner_Sad:


                            if (count1 > 0) {
                                if(position>0) {
                                    item1 = parent.getItemAtPosition(position).toString();
                                    TextView tsad = (TextView) findViewById(R.id.tsad);
                                    tsad.setText(item1);
                                }

                            }
                            count1++;

                            break;
                        case R.id.spinner_Angry:

                            if (count2 > 0) {
                                if(position>0) {
                                    item2 = parent.getItemAtPosition(position).toString();
                                    TextView tangry = (TextView) findViewById(R.id.tangry);
                                    tangry.setText(item2);
                                }
                            }
                            count2++;
                            break;


                        case R.id.spinner_Fear:

                            if (count3 > 0) {
                                if(position>0) {
                                    item3 = parent.getItemAtPosition(position).toString();
                                    TextView tfear = (TextView) findViewById(R.id.tfear);
                                    tfear.setText(item3);
                                }
                            }
                            count3++;
                            break;
                        case R.id.spinner_Neutral:

                            if (count4 > 0) {
                                if(position>0) {
                                    item4 = parent.getItemAtPosition(position).toString();
                                    TextView tneutral = (TextView) findViewById(R.id.tneutral);
                                    tneutral.setText(item4);
                                }
                            }
                            count4++;
                            break;
                        default:
                            break;
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };


    public void save(View view) {

        Switch server = (Switch) findViewById(R.id.server);
        serverstate = server.isChecked();

        SharedPreferences settings = getSharedPreferences("set", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(count>1)
        editor.putString("HAPPY", item);
        if(count1>1)
        editor.putString("SAD", item1);
        if(count2>1)
        editor.putString("ANGRY", item2);
        if(count3>1)
        editor.putString("FEAR", item3);
        if(count4>1)
        editor.putString("NEUTRAL", item4);
        editor.putBoolean("serverstate", serverstate);
        editor.apply();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        Toast.makeText(Settings.this, "Settings Saved", Toast.LENGTH_SHORT).show();
    }
}
