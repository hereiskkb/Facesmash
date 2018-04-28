package com.sample1.facesmash.facesmash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class crowd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd);
        int no_faces=getIntent().getExtras().getInt("detectionResult");
        int anger= (int) getIntent().getExtras().getFloat("anger");
        int happy=(int) getIntent().getExtras().getFloat("happy");
        int sad=(int) getIntent().getExtras().getFloat("sad");
        int fear= (int) getIntent().getExtras().getFloat("fear");
        int neutral= (int) getIntent().getExtras().getFloat("neutral");
        int male=(int)getIntent().getExtras().getFloat("male");
        int female=(int)getIntent().getExtras().getFloat("female");
        int n1=(int)getIntent().getExtras().getFloat("n1");
        int n2=(int)getIntent().getExtras().getFloat("n2");
        int n3=(int)getIntent().getExtras().getFloat("n3");
        String emo="";

        List<Integer> list = new ArrayList<>();
        String emotionType = "";
        list.add(anger);
        list.add(happy);
        list.add(fear);
        list.add(neutral);
        list.add(sad);
        Collections.sort(list);
        int max = list.get(list.size() - 1);
        if(max==anger)
            emo="ANGRY";
        else if (max==happy)
            emo="HAPPY";
        else if (max==sad)
            emo="SAD";
        else if (max==fear)
            emo="SCARED";
        else if (max==neutral)
            emo="NEUTRAL";


        String message = ""+ emo;
        TextView emotion = (TextView) findViewById(R.id.emotions);
        emotion.setText(message);

        String msg1 =""+no_faces;
        TextView people = (TextView) findViewById(R.id.faces);
        people.setText(msg1);

        String msg2 = ""+happy+"%";
        TextView h = (TextView) findViewById(R.id.happy);
        h.setText(msg2);

        String msg3 = ""+sad+"%";
        TextView s = (TextView) findViewById(R.id.sad);
        s.setText(msg3);

        String msg4 = ""+anger+"%";
        TextView a = (TextView) findViewById(R.id.angry);
        a.setText(msg4);

        String msg5 = ""+fear+"%";
        TextView f = (TextView) findViewById(R.id.fear);
        f.setText(msg5);

        String msg6 = ""+neutral+"%";
        TextView n = (TextView) findViewById(R.id.neutral);
        n.setText(msg6);

        String msg7 = ""+male+"%";
        TextView a1 = (TextView) findViewById(R.id.male);
        a1.setText(msg7);

        String msg8 = ""+female+"%";
        TextView a2 = (TextView) findViewById(R.id.female);
        a2.setText(msg8);

        String msg9 = ""+n1+"%";
        TextView a3 = (TextView) findViewById(R.id.n1);
        a3.setText(msg9);

        String msg10 = ""+n2+"%";
        TextView a4 = (TextView) findViewById(R.id.n2);
        a4.setText(msg10);

        String msg11 = ""+n3+"%";
        TextView a5 = (TextView) findViewById(R.id.n3);
        a5.setText(msg11);
    }
}
