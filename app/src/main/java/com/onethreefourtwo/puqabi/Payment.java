package com.onethreefourtwo.puqabi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Payment extends AppCompatActivity {
    private ListView list1;
    private List<Payment_Model> artistList;
    private String uid;

    private TextView Balance;

    private ImageView PayImage;
    private TextView RelPart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH)+1;
        final int year = calendar.get(Calendar.YEAR);
        DatabaseReference a = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        a.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Complete").hasChild(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year))){
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Complete").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year));
                    db1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int b =(int) dataSnapshot.getChildrenCount();
                            final double c = b * 0.05;
                            DatabaseReference a2 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Payments").child("Qazanc").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year));
                            a2.child("money").setValue(String.valueOf(c));
                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            db2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    double balans = Double.valueOf( dataSnapshot.child("balance").getValue().toString());
                                    double balance = balans+c;
                                    String xerc = dataSnapshot.child("Payments").child("Xerc").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("price").getValue().toString();
                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    Balance.setText(String.valueOf(balance).substring(0,4));
                                    db1.child("Payments").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("usd1").setValue(String.valueOf(c).substring(0,4));
                                    db1.child("Payments").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("usd2").setValue(xerc);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    db1.child("Payments").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("date").setValue(String.valueOf(day)+"."+String.valueOf(month)+"."+String.valueOf(year));
                    db1.child("Payments").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("usd1").setValue("0.00");
                    db1.child("Payments").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("usd2").setValue("0.00");
                    db1.child("Payments").child("Xerc").child(String.valueOf(day)+"a"+String.valueOf(month)+String.valueOf(year)).child("price").setValue("0.00");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        BottomNavigationView Bottom = (BottomNavigationView)findViewById(R.id.bottom);
        Bottom.setSelectedItemId(R.id.money);
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

        list1 = (ListView)findViewById(R.id.list);
        artistList = new ArrayList<>();
        Balance = (TextView)findViewById(R.id.balance);
        PayImage = (ImageView)findViewById(R.id.payimage);
        RelPart2 = (TextView)findViewById(R.id.relpart2);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        PayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference a1 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                a1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long balance =Long.valueOf(dataSnapshot.child("balance").getValue().toString());
                        if(balance >= 50.00){
                            Toast.makeText(getApplicationContext(),"Your request for payment was done!",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"Your balance must be 50.00 USD for getting money!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        RelPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference a1 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                a1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        float BalanceText = (float)dataSnapshot.child("balance").getValue();
                        if(BalanceText >= 50.00){
                            DatabaseReference b1 = FirebaseDatabase.getInstance().getReference();
                            b1.child("Request").child(uid).child("uid").setValue(uid);
                            Toast.makeText(getApplicationContext(),"Your request was sented to us! Thanks!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Your balance must be 50.00 USD for getting money!",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        d1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String BalanceText = dataSnapshot.child("balance").getValue().toString();
                Balance.setText(BalanceText+" USD");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Payments")){
                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Payments");
                    db2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            artistList.clear();
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Payment_Model Model = snapshot.getValue(Payment_Model.class);
                                artistList.add(Model);
                            }
                            Payment_Adapter adap = new Payment_Adapter(Payment.this,artistList);
                            list1.setAdapter(adap);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    final TextView EmptyList = (TextView)findViewById(R.id.emptylist);
                    list1.setVisibility(View.GONE);
                    EmptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
