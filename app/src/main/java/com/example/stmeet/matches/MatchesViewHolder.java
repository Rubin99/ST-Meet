package com.example.stmeet.matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;
import com.example.stmeet.chat.ChatActivity;
import com.example.stmeet.teacher_info.TeacherInfoActivity;

import org.jetbrains.annotations.NotNull;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchid, mMatchName, mMatchSubject, mHourlyRate;
    public ImageView mMatchImage, mMatchProfile;

    public MatchesViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchid = itemView.findViewById(R.id.matchId);
        mMatchName = itemView.findViewById(R.id.matchName);
        mMatchSubject = itemView.findViewById(R.id.matchSubject);
        mMatchImage = itemView.findViewById(R.id.matchImage);
        mHourlyRate = itemView.findViewById(R.id.matchesHourlyRate);
        mMatchid.setVisibility(View.INVISIBLE); //////////////////
        mMatchProfile = itemView.findViewById(R.id.profile);

        mMatchProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeacherInfoActivity.class);
                Bundle b = new Bundle();
                b.putString("teacherId", mMatchid.getText().toString());
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId", mMatchid.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);

    }
}
