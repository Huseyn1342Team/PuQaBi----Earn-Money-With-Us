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

public class Activity_2 extends AppCompatActivity {
    private Button Submit;
    private EditText Pass,Mail;
    private TextView Register;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Submit = (Button)findViewById(R.id.submit);
        Pass = (EditText) findViewById(R.id.pass);
        Mail = (EditText)findViewById(R.id.mail);
        Register = (TextView)findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),Register.class);
                startActivity(i);
                finish();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword(Mail.getText().toString(),Pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"You log in is successful!",Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(Activity_2.this,FirstMainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Process is failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
