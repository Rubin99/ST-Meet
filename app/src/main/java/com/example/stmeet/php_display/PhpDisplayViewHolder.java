package com.example.stmeet.php_display;

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

public class PhpDisplayViewHolder extends RecyclerView.ViewHolder {


    public TextView mPhpTeacherId, mPhpName, mHourlyRate;
    public ImageView mPhpImage, mAccept, mReject;
    public RatingBar mRatingBar;

    String currentUId;
    FirebaseAuth mAuth;
    DatabaseReference usersDb;

    public PhpDisplayViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        //itemView.setOnClickListener(this);

        mPhpTeacherId = itemView.findViewById(R.id.phpId);
        mPhpTeacherId.setVisibility(View.INVISIBLE);
        mPhpName = itemView.findViewById(R.id.phpName);
        mHourlyRate = itemView.findViewById(R.id.phpHourlyRate);
        mPhpImage = itemView.findViewById(R.id.phpImage);
        mAccept = itemView.findViewById(R.id.accept);
        mReject = itemView.findViewById(R.id.reject);
        //mJavaRating = itemView.findViewById(R.id.javaRating);
        mRatingBar = itemView.findViewById(R.id.ratingBarPhp);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Request sent", Toast.LENGTH_LONG).show();
                usersDb.child(mPhpTeacherId.getText().toString()).child("connections").child("accepted").child(currentUId).setValue(true);
                mAccept.setVisibility(View.INVISIBLE);
                mReject.setVisibility(View.INVISIBLE);
            }
        });
        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Teacher Rejected sent", Toast.LENGTH_LONG).show();
                usersDb.child(mPhpTeacherId.getText().toString()).child("connections").child("rejected").child(currentUId).setValue(true);
                mReject.setVisibility(View.INVISIBLE);
                mAccept.setVisibility(View.INVISIBLE);
            }
        });

        mPhpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeacherInfoActivity.class);
                Bundle b = new Bundle();
                b.putString("teacherId", mPhpTeacherId.getText().toString());
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }

}
