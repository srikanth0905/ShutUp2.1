package com.greymat9er.shutup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<ShutUpMessages> message;

    public MessageAdapter(List<ShutUpMessages> message) {
        this.message = message;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageAdapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        boolean isPhoto = message.get(position).getPhotoUri() != null;

        if (isPhoto) {
            holder.messageTextView.setVisibility(View.GONE);
            holder.photoImageView.setVisibility(View.VISIBLE);

            Glide.with(holder.photoImageView.getContext())
                    .load(message.get(position).getPhotoUri())
                    .into(holder.photoImageView);
        } else {
            holder.messageTextView.setVisibility(View.VISIBLE);
            holder.photoImageView.setVisibility(View.GONE);

            holder.messageTextView.setText(message.get(position).getText());
        }
        holder.authorTextView.setText(message.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return 0;
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
