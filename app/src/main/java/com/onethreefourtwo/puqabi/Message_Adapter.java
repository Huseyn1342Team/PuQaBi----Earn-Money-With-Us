package com.onethreefourtwo.puqabi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.ViewHolder>{

    public static final int MSG_TITLE_LEFT = 0;
    public static final int MSG_TITLE_RIGHT = 1;
    private Context mContext;
    private List<messagemodel> mChat;
    FirebaseUser fuser;
    public Message_Adapter(Context mContext, List<messagemodel> mChat){
        this.mContext = mContext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public Message_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TITLE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new Message_Adapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new Message_Adapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Message_Adapter.ViewHolder holder, int position) {
        messagemodel chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profille_image;
        public ViewHolder(View itemView){
            super(itemView);
            show_message= itemView.findViewById(R.id.showmsg);
        }
    }
    @Override
    public int getItemViewType(int position){
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TITLE_RIGHT;
        }else {
            return MSG_TITLE_LEFT;
        }
    }
}
