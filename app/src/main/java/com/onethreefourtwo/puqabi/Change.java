package com.onethreefourtwo.puqabi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Change extends AppCompatActivity {
    private RelativeLayout Name,Mail,Pass;
    String Password,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Name = (RelativeLayout)findViewById(R.id.name);
        Mail = (RelativeLayout)findViewById(R.id.email);
        Pass = (RelativeLayout)findViewById(R.id.password);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Email = dataSnapshot.child("mail").getValue().toString();
                Password = dataSnapshot.child("pass").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Name();
        Mail();
        Pass();

    }
    private void Name(){
        if(getIntent().getStringExtra("category").equals("name")){
            Mail.setVisibility(View.GONE);
            Name.setVisibility(View.VISIBLE);
            Pass.setVisibility(View.GONE);
            final EditText NewName = (EditText)findViewById(R.id.newname);
            final Button SubmitName = (Button)findViewById(R.id.submitname);
            SubmitName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NewName.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"You new email field is empty!",Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(getApplicationContext(),Settings.class);
                        startActivity(a);
                        finish();
                    }else {
                        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        db1.child("name").setValue(NewName.getText().toString());
                        Intent a = new Intent(getApplicationContext(),FirstMainActivity.class);
                        startActivity(a);
                        finish();
                    }
                }
            });
        }
    }
    private void Pass(){
        if(getIntent().getStringExtra("category").equals("pass")){
            Mail.setVisibility(View.GONE);
            Name.setVisibility(View.GONE);
            Pass.setVisibility(View.VISIBLE);

            final EditText OldPass = (EditText)findViewById(R.id.oldpass);
            final EditText NewPass = (EditText)findViewById(R.id.newpass);
            final Button SubmitPass = (Button)findViewById(R.id.submitpass);

            SubmitPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(OldPass.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"New email fiel is empty!",Toast.LENGTH_SHORT).show();
                        Intent b = new Intent(getApplicationContext(),Settings.class);
                        startActivity(b);
                        finish();
                    }
                    if(NewPass.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Password fiel is empty!",Toast.LENGTH_SHORT).show();
                        Intent b = new Intent(getApplicationContext(),Settings.class);
                        startActivity(b);
                        finish();
                    }
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    db1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String password = dataSnapshot.child("pass").getValue().toString();
                            if(password.equals(OldPass.getText().toString())){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(Email, Password);
                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                user.updatePassword(NewPass.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(),"You updated your password!",Toast.LENGTH_SHORT).show();
                                                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                    db1.child("pass").setValue(NewPass.getText().toString());
                                                                    Intent a = new Intent(getApplicationContext(),FirstMainActivity.class);
                                                                    startActivity(a);
                                                                    finish();
                                                                }else{
                                                                    Toast.makeText(getApplicationContext(),"You cannot updated your password!",Toast.LENGTH_SHORT).show();
                                                                    Intent a =new Intent(getApplicationContext(),Settings.class);
                                                                    startActivity(a);
                                                                    finish();
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
    }
    private void Mail(){
        if(getIntent().getStringExtra("category").equals("email")){
            Mail.setVisibility(View.VISIBLE);
            Name.setVisibility(View.GONE);
            Pass.setVisibility(View.GONE);

            final EditText PassForEmail = (EditText)findViewById(R.id.passforemail);
            final EditText NewMail = (EditText)findViewById(R.id.newmail);
            final Button SubmitMail = (Button)findViewById(R.id.submitmail);

            SubmitMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NewMail.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"New email field is empty!",Toast.LENGTH_SHORT).show();
                        Intent b = new Intent(getApplicationContext(),Settings.class);
                        startActivity(b);
                        finish();
                    }
                    if(PassForEmail.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Password field is empty!",Toast.LENGTH_SHORT).show();
                        Intent b = new Intent(getApplicationContext(),Settings.class);
                        startActivity(b);
                        finish();
                    }



                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    db1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String password = dataSnapshot.child("pass").getValue().toString();
                            if(password.equals(PassForEmail.getText().toString())){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(Email, Password);
                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                user.updateEmail(NewMail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(),"You updated your email!",Toast.LENGTH_SHORT).show();
                                                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                    db1.child("mail").setValue(NewMail.getText().toString());
                                                                    Intent a = new Intent(getApplicationContext(),FirstMainActivity.class);
                                                                    startActivity(a);
                                                                    finish();
                                                                }else{
                                                                    Toast.makeText(getApplicationContext(),"You cannot updated your email!",Toast.LENGTH_SHORT).show();
                                                                    Intent a =new Intent(getApplicationContext(),Settings.class);
                                                                    startActivity(a);
                                                                    finish();
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

        }
    }
}
