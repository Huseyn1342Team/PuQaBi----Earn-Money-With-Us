package com.onethreefourtwo.puqabi;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class Payment_Adapter extends ArrayAdapter<Payment_Model> {
    private Activity context;
    private String month;
    private List<Payment_Model> artistList;
    public Payment_Adapter(Activity context, List<Payment_Model> artistList){
        super(context,R.layout.payment_list,artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewitem = inflater.inflate(R.layout.payment_list, null, true);
        final TextView Date = (TextView)listViewitem.findViewById(R.id.date);
        final TextView Usd = (TextView)listViewitem.findViewById(R.id.usd);
        final TextView Usd2 = (TextView)listViewitem.findViewById(R.id.usd2);
        final RelativeLayout rel1 = (RelativeLayout)listViewitem.findViewById(R.id.rel1);
        final Payment_Model model = artistList.get(position);

        Date.setText(model.getDate());
        Usd.setText(model.getUsd1()+"USD");
        Usd2.setText(model.getUsd2()+"USD");
        if(model.getUsd1() == null || model.getUsd1().equals("")){
            rel1.setVisibility(View.GONE);
        }


        return  listViewitem;
    }
}
