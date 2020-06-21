package com.onethreefourtwo.puqabi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat extends AppCompatActivity {
    private String ReceiverUid;
    private ImageView Back;
    private ImageButton SendButton;
    private EditText SendEdit;
    private FirebaseUser SenderUser;
    private Message_Adapter messageAdapter;
    private List<messagemodel> mChat;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private TextView Name;
    private ImageView Call;
    private String phonenumber;
    private String profil;
    private String c;
    private String msg;
    private int dad;
    private DatabaseReference db1,db2;
    private boolean notify = false;
    private EditText SendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Back = (ImageView)findViewById(R.id.back);
        recyclerView = (RecyclerView)findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SendButton = (ImageButton)findViewById(R.id.btnsend);
        SendText = (EditText)findViewById(R.id.editsendtext);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readMessages(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Chat.this,FirstMainActivity.class);
                startActivity(i);
                finish();
            }
        });
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message  = SendText.getText().toString();
                if(!message.equals("")){
                    SendMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),message);
                }else{
                    Toast.makeText(getApplicationContext(),"You cannot send empty message!",Toast.LENGTH_SHORT).show();
                }
                SendText.setText("");
                readMessages(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });
    }
    private void SendMessage(String sender, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("message", message);
        hashMap.put("sender",sender);
        reference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Chat").push().setValue(hashMap);
        final String msg = message;
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void readMessages(final String myid) {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    messagemodel chat = snapshot.getValue(messagemodel.class);
                    mChat.add(chat);
                }
                messageAdapter = new Message_Adapter(getApplicationContext(), mChat);
                recyclerView.setAdapter (messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
