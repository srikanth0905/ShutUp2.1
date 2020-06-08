package com.greymat9er.shutup;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.ViewHolder {

    //This was for ListView
//    public MessageAdapter(Context context, int resource, List<ShutUpMessages> object) {
//        super(context, resource, object);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        if (convertView == null)
//            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
//
//        ImageView photoImageView = convertView.findViewById(R.id.photoImageView);
//        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
//        TextView authorTextView = convertView.findViewById(R.id.authorTextView);
//
//        ShutUpMessages message = getItem(position);
//
//        boolean isPhoto = message.getPhotoUri() != null;
//
//        if (isPhoto) {
//            messageTextView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.VISIBLE);
//
//            Glide.with(photoImageView.getContext())
//                    .load(message.getPhotoUri())
//                    .into(photoImageView);
//        } else {
//            messageTextView.setVisibility(View.VISIBLE);
//            photoImageView.setVisibility(View.GONE);
//
//            messageTextView.setText(message.getText());
//        }
//        authorTextView.setText(message.getName());
//
//        return convertView;
//    }


    //for Recycler View
//    private ArrayList<ShutUpMessages> mShutUpMessages;

    public MessageAdapter(@NonNull View itemView) {
        super(itemView);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoImageView;
        public TextView messageTextView;
        public TextView authorTextView;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.photoImageView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }
    }
}
