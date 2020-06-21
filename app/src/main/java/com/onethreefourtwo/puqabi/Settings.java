package com.onethreefourtwo.puqabi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    private TextView Instagram,Facebook,LogOut,Name,Password,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Instagram = (TextView)findViewById(R.id.text8);
        Facebook = (TextView)findViewById(R.id.text7);
        LogOut = (TextView)findViewById(R.id.text9);
        Name = (TextView)findViewById(R.id.text3);
        Password = (TextView)findViewById(R.id.text4);
        Email = (TextView)findViewById(R.id.text5);
        clickFacebook();
        clickInstagram();
        clickLogOut();
        clickName();
        clickPassword();
        clickEmail();
        BottomNavigationView Bottom = (BottomNavigationView)findViewById(R.id.bottom);
        Bottom.setSelectedItemId(R.id.settings);
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

    }
    private void clickName(){
        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(),Change.class);
                a.putExtra("category","name");
                startActivity(a);
            }
        });
    }
    private void clickPassword(){
        Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(),Change.class);
                a.putExtra("category","pass");
                startActivity(a);
            }
        });
    }
    private void clickEmail(){
        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent(getApplicationContext(),Change.class);
                a.putExtra("category","email");
                startActivity(a);
            }
        });
    }
    private void clickInstagram(){
        Instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  https://www.instagram.com/1342team/
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("https://www.instagram.com/1342team/") );

                startActivity( browse );
            }
        });
    }
    private void clickFacebook(){
        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("https://www.facebook.com/Up-Down-Team-101980031152428/") );

                startActivity( browse );
            }
        });
    }
    private void clickLogOut(){
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i =new Intent(getApplicationContext(), Activity_2.class);
                startActivity(i);
                finish();
            }
        });
    }
}
