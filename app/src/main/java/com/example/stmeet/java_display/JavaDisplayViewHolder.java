package com.example.stmeet.java_display;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.chat.ChatActivity;
import com.example.stmeet.info.cards;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class JavaDisplayViewHolder extends RecyclerView.ViewHolder {


    public TextView mJavaId, mJavaName, mJavaSubject;
    public ImageView mJavaImage, mAccept, mReject;

    String currentUId;
    FirebaseAuth mAuth;
    DatabaseReference usersDb;

    public JavaDisplayViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        //itemView.setOnClickListener(this);

        mJavaId = itemView.findViewById(R.id.javaId);
        mJavaId.setVisibility(View.INVISIBLE);
        mJavaName = itemView.findViewById(R.id.javaName);
        mJavaSubject = itemView.findViewById(R.id.javaSubject);
        mJavaImage = itemView.findViewById(R.id.javaImage);
        mAccept = itemView.findViewById(R.id.accept);
        mReject = itemView.findViewById(R.id.reject);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDb.child(mJavaId.getText().toString()).child("connections").child("accepted").child(currentUId).setValue(true);
                mAccept.setVisibility(View.INVISIBLE);
                mReject.setVisibility(View.INVISIBLE);
            }
        });
        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDb.child(mJavaId.getText().toString()).child("connections").child("rejected").child(currentUId).setValue(true);
                mReject.setVisibility(View.INVISIBLE);
                mAccept.setVisibility(View.INVISIBLE);
            }
        });
    }





    //private JavaDisplayViewHolder.ClickListener mClickListener;
/*
    @Override
    public void onClick(View v) {

    }

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(JavaDisplayViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }*/
}
