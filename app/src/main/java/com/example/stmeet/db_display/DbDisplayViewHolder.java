package com.example.stmeet.db_display;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;
import com.example.stmeet.teacher_info.TeacherInfoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class DbDisplayViewHolder extends RecyclerView.ViewHolder {


    public TextView mDbTeacherId, mDbName, mHourlyRate;
    public ImageView mDbImage, mAccept, mReject;
    public RatingBar mDbRatingBar;

    String currentUId;
    FirebaseAuth mAuth;
    DatabaseReference usersDb;

    public DbDisplayViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        mDbTeacherId = itemView.findViewById(R.id.dbId);
        mDbTeacherId.setVisibility(View.INVISIBLE);
        mDbName = itemView.findViewById(R.id.dbName);
        mDbImage = itemView.findViewById(R.id.dbImage);
        mAccept = itemView.findViewById(R.id.accept);
        mReject = itemView.findViewById(R.id.reject);
        mDbRatingBar = itemView.findViewById(R.id.ratingBarDb);
        mHourlyRate =itemView.findViewById(R.id.dbHourlyRate);

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
                Toast.makeText(itemView.getContext(), "Request sent", Toast.LENGTH_LONG).show();
                usersDb.child(mDbTeacherId.getText().toString()).child("connections").child("rejected").child(currentUId).setValue(true);
                mReject.setVisibility(View.INVISIBLE);
                mAccept.setVisibility(View.INVISIBLE);
            }
        });

        mDbImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Teacher Rejected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), TeacherInfoActivity.class);
                Bundle b = new Bundle();
                b.putString("teacherId", mDbTeacherId.getText().toString());
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }

}
