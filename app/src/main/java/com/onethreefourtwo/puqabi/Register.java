package com.onethreefourtwo.puqabi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    private Button Submit;
    private EditText Mail,Pass,Name;
    private TextView LogIn;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Submit = (Button)findViewById(R.id.submit);
        Mail = (EditText)findViewById(R.id.mail);
        Pass = (EditText)findViewById(R.id.pass);
        Name = (EditText)findViewById(R.id.name);
        LogIn = (TextView) findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Activity_2.class);
                startActivity(i);
                finish();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.createUserWithEmailAndPassword(Mail.getText().toString(),Pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Your registration is  successful!",Toast.LENGTH_SHORT).show();
                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference();
                            db1.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(Name.getText().toString());
                            db1.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("mail").setValue(Mail.getText().toString());
                            db1.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pass").setValue(Pass.getText().toString());
                            db1.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("balance").setValue("0.00");
                            Calendar calendar = Calendar.getInstance();
                            final int day = calendar.get(Calendar.DAY_OF_MONTH);
                            final int month = calendar.get(Calendar.MONTH)+1;
                            final int year = calendar.get(Calendar.YEAR);
                            db1.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Payments").child("Xerc").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("price").setValue("0.00");
                            Intent i =new Intent(Register.this,FirstMainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Process is failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
