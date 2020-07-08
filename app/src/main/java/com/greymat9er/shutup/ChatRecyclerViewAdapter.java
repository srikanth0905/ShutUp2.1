package com.greymat9er.shutup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<ChatList> mData;
    String mUserName;

    public ChatRecyclerViewAdapter(Context mContext, List<ChatList> mData, String mUserName) {
        this.mContext = mContext;
        this.mData = mData;
        this.mUserName = mUserName;
        Log.i("ChatRecyclerViewAdapter: ", mUserName);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_chat_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        //adding onClick listener to chat item
        viewHolder.chatItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Click on Item " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, MainActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putParcelable("FirebaseUser", firebaseUser);
                bundle.putString("Username", mUserName);
                mContext.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.chatFriendName.setText(mData.get(position).getFriendName());
        holder.chatProfilePicture.setImageResource(mData.get(position).getProfilePicture());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView chatFriendName;
        private CircleImageView chatProfilePicture;
        private LinearLayout chatItem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            chatItem = itemView.findViewById(R.id.chat_item);
            chatFriendName = itemView.findViewById(R.id.chat_friend_name);
            chatProfilePicture = itemView.findViewById(R.id.chat_profile_picture);
        }
    }
}
