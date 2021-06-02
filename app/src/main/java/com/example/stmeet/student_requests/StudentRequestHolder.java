package com.example.stmeet.student_requests;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;

import org.jetbrains.annotations.NotNull;

public class StudentRequestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mStudentId, mStudentName;
    public ImageView mStudentImage;

    public StudentRequestHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mStudentId = itemView.findViewById(R.id.studentId);
        mStudentName = itemView.findViewById(R.id.studentName);
        mStudentImage = itemView.findViewById(R.id.studentImage);
        mStudentId.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

    }
}
