package com.greymat9er.shutup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {

    private String mUserName = "ANONYMOUS";
    private Button mSendButton;
    private EditText mMessageEditText;
    private ImageButton photoPickerButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosReference;
//    private FirebaseUser firebaseUser;


    private FirebaseRecyclerOptions<ShutUpMessages> options;

    public final static String MESSAGE_ROOT_REFERENCE = "messages";
    public final static String CHAT_ROOT_REFERENCE = "photos/";

    private static final int PHOTO_PICKER = 576;

    private RecyclerView recyclerView;
    private MessageAdapter adapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mUserName = intent.getStringExtra("Username");
        if (mUserName == null)
            mUserName = "Value not found";
        Log.d("MainActivity: ", mUserName);

        //initializing database and storage reference
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        mMessageDatabaseReference = mFirebaseDatabase.getReference().child(MESSAGE_ROOT_REFERENCE);
        mChatPhotosReference = mFirebaseStorage.getReference().child(CHAT_ROOT_REFERENCE);

        //initializing recycler view
        setUpRecyclerView();

        //setting Up authentication
//        signInHandler();

        photoPickerButton = findViewById(R.id.photoPickerButton);
        mMessageEditText = findViewById(R.id.messageEditText);
        mSendButton = findViewById(R.id.sendButton);

        progressDialog = new ProgressDialog(this);

        //Open photo selector when camera button is clicked
        photoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PHOTO_PICKER);
            }
        });

        //Enable send button when there is text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0)
                    mSendButton.setEnabled(true);
                else
                    mSendButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //send message to Database
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShutUpMessages message = new ShutUpMessages(mMessageEditText.getText().toString().trim(), mUserName, null);
                mMessageDatabaseReference.push().setValue(message);
                mMessageEditText.setText("");

                //scrolling up to the latest message
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                    }
                });
            }
        });
    }

    private void setUpRecyclerView() {
        options = new FirebaseRecyclerOptions.Builder<ShutUpMessages>()
                .setQuery(mMessageDatabaseReference, ShutUpMessages.class)
                .build();

        adapter = new MessageAdapter(this, options);
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                progressDialog.setMessage("Uploading...");
                assert data != null;
                uploadImage(data);
            }
        }
    }

    private void uploadImage(Intent data) {
        Uri imageUri = data.getData();
        if (imageUri != null) {
            progressDialog.show();
            final StorageReference photosReference =
                    mChatPhotosReference.child(mUserName + (new Timestamp(System.currentTimeMillis()).toString()) + imageUri.getLastPathSegment());

            photosReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                    photosReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.dismiss();
                            Log.i("Uploaded file URL: ", uri.toString());
//                            Toast.makeText(MainActivity.this, "File uploaded to: " + uri, Toast.LENGTH_LONG).show();
                            ShutUpMessages message = new ShutUpMessages(null, mUserName, uri.toString());
                            mMessageDatabaseReference.push().setValue(message);

                            //scrolling up to the latest message
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                                }
                            });

                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.main_activity_layout), "Image Uploaded successfully", Snackbar.LENGTH_SHORT);
                            snackbar.show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "File not uploaded: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "File upload failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
