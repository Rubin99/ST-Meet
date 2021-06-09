package com.example.stmeet.db_display;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;
import com.example.stmeet.teacher_info.TeacherInfoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class DbDisplayViewHolder extends RecyclerView.ViewHolder {


    public TextView mDbTeacherId, mDbName, mDbSubject;
    public ImageView mDbImage, mAccept, mReject;
    public RatingBar mDbRatingBar;

    String currentUId;
    FirebaseAuth mAuth;
    DatabaseReference usersDb;

    public DbDisplayViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        //itemView.setOnClickListener(this);

        mDbTeacherId = itemView.findViewById(R.id.dbId);
        mDbTeacherId.setVisibility(View.INVISIBLE);
        mDbName = itemView.findViewById(R.id.dbName);
        mDbSubject = itemView.findViewById(R.id.dbSubject);
        mDbImage = itemView.findViewById(R.id.dbImage);
        mAccept = itemView.findViewById(R.id.accept);
        mReject = itemView.findViewById(R.id.reject);
        //mJavaRating = itemView.findViewById(R.id.javaRating);
        mDbRatingBar = itemView.findViewById(R.id.ratingBarDb);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDb.child(mDbTeacherId.getText().toString()).child("connections").child("accepted").child(currentUId).setValue(true);
                mAccept.setVisibility(View.INVISIBLE);
                mReject.setVisibility(View.INVISIBLE);
            }
        });
        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDb.child(mDbTeacherId.getText().toString()).child("connections").child("rejected").child(currentUId).setValue(true);
                mReject.setVisibility(View.INVISIBLE);
                mAccept.setVisibility(View.INVISIBLE);
            }
        });

        mDbImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeacherInfoActivity.class);
                Bundle b = new Bundle();
                b.putString("teacherId", mDbTeacherId.getText().toString());
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }





    //private PhpDisplayViewHolder.ClickListener mClickListener;
/*
    @Override
    public void onClick(View v) {

    }

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(PhpDisplayViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }*/
}
