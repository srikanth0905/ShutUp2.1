package com.greymat9er.shutup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;
import java.util.Objects;

public class MessageAdapter extends FirebaseRecyclerAdapter<ShutUpMessages, MessageAdapter.MessageViewHolder> {

    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessageAdapter(Context context, @NonNull FirebaseRecyclerOptions<ShutUpMessages> options) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull ShutUpMessages model) {

        //adding animation to item
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_from_botton));

        boolean isPhoto = model.getPhotoUri() != null;
        if (isPhoto) {
            holder.messageTextView.setVisibility(View.GONE);
            holder.photoImageView.setVisibility(View.VISIBLE);

            Glide.with(holder.photoImageView.getContext())
                    .load(model.getPhotoUri())
                    .into(holder.photoImageView);
        } else {
            holder.messageTextView.setVisibility(View.VISIBLE);
            holder.photoImageView.setVisibility(View.GONE);

            holder.messageTextView.setText(model.getText());
        }
        holder.authorTextView.setText(model.getName());
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoImageView;
        public TextView messageTextView;
        public TextView authorTextView;
        public CardView container;
        public RecyclerView recyclerView;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            recyclerView = itemView.findViewById(R.id.messageRecyclerView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }
    }
}
