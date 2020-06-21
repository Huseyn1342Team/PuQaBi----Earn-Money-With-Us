package com.onethreefourtwo.puqabi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class FirstMainActivity extends AppCompatActivity implements Animation.AnimationListener {
    boolean blnButtonRotation;
    int intNumber = 6;
    long lngDegrees = 0;
    private ImageView Image1;
    private Button Submit;
    private TextView Text1,Text2;
    SharedPreferences sharedPreferences;
    private String Task;
    private int TaskId;
    private Button Submit2;
    private BottomNavigationView Bottom;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(1024);
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);

        Text1 = (TextView)findViewById(R.id.text1);
        Text2 = (TextView)findViewById(R.id.text2);
        Image1 = (ImageView)findViewById(R.id.image1);
        Submit = (Button)findViewById(R.id.submit);
        Submit2 = (Button)findViewById(R.id.submit2);

        Submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToTaskDo();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRotation();
            }
        });
        Bottom = (BottomNavigationView)findViewById(R.id.bottom);
        Bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.home:
                        Intent a =new Intent(getApplicationContext(),FirstMainActivity.class);
                        startActivity(a);
                        finish();
                        return false;
                    case R.id.money:
                        Intent b =new Intent(getApplicationContext(),Payment.class);
                        startActivity(b);
                        finish();
                        return false;
                    case R.id.profile:
                        Intent c= new Intent(getApplicationContext(), profil.class);
                        startActivity(c);
                        finish();
                        return false;
                    case R.id.chat:
                        Intent d = new Intent(getApplicationContext(),Chat.class);
                        startActivity(d);
                        finish();
                        return false;
                    case R.id.settings:
                        Intent e = new Intent(getApplicationContext(),Settings.class);
                        startActivity(e);
                        finish();
                        return false;
                }
                return false;
            }
        });

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.intNumber = this.sharedPreferences.getInt("INT_NUMBER",6);
    }
    private void sendToTaskDo(){
        Intent i =new Intent(getApplicationContext(),Submit_task.class);
        i.putExtra("task",Task);
        i.putExtra("taskid",TaskId);
        startActivity(i);
        finish();
    }
    @Override
    public void onAnimationStart(Animation animation) {
        this.blnButtonRotation = false;
        Submit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast toast = Toast.makeText(this,""+String.valueOf((int)((double)this.intNumber)-Math.floor(((double)this.lngDegrees) /(360.d /((double)this.intNumber)))) + "",0);
        toast.setGravity(49,0,0);
        toast.show();
        if((int)((double)this.intNumber)-Math.floor(((double)this.lngDegrees) /(360.d /((double)this.intNumber))) == 1.00){
            Task = "Post about our work in Facebook.";
            Text2.setText("Task:"+Task);
        }
        if((int)((double)this.intNumber)-Math.floor(((double)this.lngDegrees) /(360.d /((double)this.intNumber)))== 2.00){
            Task = "Like our last post in Facebook.";
            Text2.setText("Task:"+Task);
        }
        if((int)((double)this.intNumber)-Math.floor(((double)this.lngDegrees) /(360.d /((double)this.intNumber))) == 3.00){
            Task = "Click on the next ad.";
            Text2.setText("Task:"+Task);
        }
        if((int)((double)this.intNumber)-Math.floor(((double)this.lngDegrees) /(360.d /((double)this.intNumber))) == 4.00){
            Task = "Write about us to your friend.";
            Text2.setText("Task:"+Task);
        }
        if((int)((double)this.intNumber)-Math.floor(((double)this.lngDegrees) /(360.d /((double)this.intNumber))) == 5.00){
            Task = "Like our last post in Instagram.";
            Text2.setText("Task:"+Task);
        }
        if((int)((double)this.intNumber)-Math.floor(((double)this.lngDegrees) /(360.d /((double)this.intNumber))) == 6.00){
            Task = "Post about our work in Instagram.";
            Text2.setText("Task:"+Task);
        }
        this.blnButtonRotation = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Text1.setVisibility(View.VISIBLE);
                Text2.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    private void onClickRotation(){
        int ran = new Random().nextInt(360)+ 3600;
        RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDegrees,(float)(this.lngDegrees+((long)ran)),1,0.5f,1,0.5f);
        this.lngDegrees = (this.lngDegrees + ((long)ran))%360;
        rotateAnimation.setDuration((long)ran);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(this);
        Image1.setAnimation(rotateAnimation);
        Image1.startAnimation(rotateAnimation);
        TaskId = getTaskId();
        Text1.setText("Task Id:"+ String.valueOf(TaskId));
    }
}
