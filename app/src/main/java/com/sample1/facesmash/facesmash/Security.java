package com.sample1.facesmash.facesmash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Security extends AppCompatActivity {
    ImageView faceidentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        String personName =  getIntent().getExtras().getString("personName");
        int Age = 0;
        int Cr = 0;

        TextView age = (TextView) findViewById(R.id.age);
        TextView cr = (TextView) findViewById(R.id.cr);

        faceidentity = findViewById(R.id.faceidentity);
        if(personName.equals("Karuna")) {
            faceidentity.setImageResource(R.drawable.kar);
           Age=30;
           Cr=5;
        }
       else if(personName.equals("Salman")) {
            faceidentity.setImageResource(R.drawable.sal);
            Age=25;
            Cr=7;
        }
        else if(personName.equals("Ransom")) {
            faceidentity.setImageResource(R.drawable.ran);
            Age = 29;
            Cr = 10;
        }
       else if(personName.equals("Drithiman")) {
            faceidentity.setImageResource(R.drawable.dman);
            Age = 27;
            Cr = 420;
        }


        String message = "Name : " + personName;
        TextView personname = (TextView) findViewById(R.id.personname);
        personname.setText(message);
        String a = "Age : "+Age;
        String b = "Criminal Records Found : "+Cr;
        age.setText(a);
        cr.setText(b);



    }
}
