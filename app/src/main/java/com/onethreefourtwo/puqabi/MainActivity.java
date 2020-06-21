package com.onethreefourtwo.puqabi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ImageView Image1,Image1_2;
    private TextView Text1, Text2, Text3,Text1_2,Text2_2,Text3_2;
    private Animation FromBottomStart, FromBottomEnd, FromTopStart, FromTopEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Image1 = (ImageView) findViewById(R.id.image1);
        Text1 = (TextView) findViewById(R.id.text1);
        Text2 = (TextView) findViewById(R.id.text2);
        Text3 = (TextView) findViewById(R.id.text3);

        Image1_2 = (ImageView)findViewById(R.id.image1_2);
        Text1_2 = (TextView)findViewById(R.id.text1_2);
        Text2_2 = (TextView)findViewById(R.id.text2_2);
        Text3_2 = (TextView)findViewById(R.id.text3_2);

        FromBottomEnd = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.frombottom_end);
        FromBottomStart = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.frombottom_start);
        FromTopStart = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fromtop_start);
        FromTopEnd = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fromtop_end);

        Image1.setAnimation(FromTopStart);
        Text1.setAnimation(FromTopStart);
        Text2.setAnimation(FromTopStart);
        Text3.setAnimation(FromBottomStart);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Image1.setVisibility(View.GONE);
                Text1.setVisibility(View.GONE);
                Text2.setVisibility(View.GONE);
                Text3.setVisibility(View.GONE);

                Image1_2.setVisibility(View.VISIBLE);
                Text1_2.setVisibility(View.VISIBLE);
                Text2_2.setVisibility(View.VISIBLE);
                Text3_2.setVisibility(View.VISIBLE);

                Image1_2.setAnimation(FromTopEnd);
                Text1_2.setAnimation(FromTopEnd);
                Text2_2.setAnimation(FromTopEnd);
                Text3_2.setAnimation(FromBottomEnd);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                            Intent i = new Intent(MainActivity.this, Activity_2.class);
                            startActivity(i);
                            finish();
                        }else{
                            Intent i =new Intent(getApplicationContext(),FirstMainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }, 1000);
            }
        }, 8000);
    }
}
