package com.example.stmeet.student_requests;

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
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.TeacherMatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class StudentRequestHolder extends RecyclerView.ViewHolder {

    public TextView mStudentId, mStudentName;
    public ImageView mStudentImage, mRequestAccept, mRequestReject;

    String currentUId;
    FirebaseAuth mAuth;
    DatabaseReference usersDb;

    public StudentRequestHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        //itemView.setOnClickListener(this);

        mStudentId = itemView.findViewById(R.id.studentId);
        mStudentName = itemView.findViewById(R.id.studentName);
        mStudentImage = itemView.findViewById(R.id.studentImage);
        mStudentId.setVisibility(View.INVISIBLE);
        mRequestAccept = itemView.findViewById(R.id.requestAccept);
        mRequestReject = itemView.findViewById(R.id.requestReject);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        mRequestAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDb.child(mStudentId.getText().toString()).child("connections").child("accepted").child(currentUId).setValue(true);
                mRequestAccept.setVisibility(View.INVISIBLE);
                mRequestReject.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(v.getContext(), TeacherMatchesActivity.class);
                v.getContext().startActivity(intent);

                isConnectionMatch(mStudentId);
            }
        });
        mRequestReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDb.child(mStudentId.getText().toString()).child("connections").child("rejected").child(currentUId).setValue(true);
                mRequestReject.setVisibility(View.INVISIBLE);
                mRequestAccept.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void isConnectionMatch(TextView mStudentId) {
        DatabaseReference currentUserConnectionsDb = usersDb.child(currentUId).child("connections").child("accepted").child(mStudentId.getText().toString());
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(itemView.getContext(), "matched", Toast.LENGTH_LONG).show();

                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    //have to make sure matches sub branch is within userId !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                    //usersDb.child(oppositeUserRole).child(snapshot.getKey()).child("connections").child("matches").child(currentUId).child("ChatId").setValue(true);
                    //usersDb.child(userRole).child(currentUId).child("connections").child("matches").child(snapshot.getKey()).child("ChatId").setValue(true);
                    usersDb.child(snapshot.getKey()).child("connections").child("matches").child(currentUId).child("ChatId").setValue(key);
                    usersDb.child(currentUId).child("connections").child("matches").child(snapshot.getKey()).child("ChatId").setValue(key);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

}
