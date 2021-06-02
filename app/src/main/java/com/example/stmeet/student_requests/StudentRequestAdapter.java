package com.example.stmeet.student_requests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.stmeet.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StudentRequestAdapter extends RecyclerView.Adapter<StudentRequestHolder> {

    private List<StudentRequestObject> requestList;
    private Context context;

    public StudentRequestAdapter(List<StudentRequestObject> requestList, Context context){
        this.requestList = requestList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public StudentRequestHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View requestLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_student_request_list, null, false);
        RecyclerView.LayoutParams rlp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        requestLayoutView.setLayoutParams(rlp);
        StudentRequestHolder srh = new StudentRequestHolder((requestLayoutView));

        return srh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentRequestHolder holder, int position) {
        holder.mStudentId.setText(requestList.get(position).getStudentId());
        holder.mStudentName.setText(requestList.get(position).getStudentName());
        if (!requestList.get(position).getStudentProfileImageUrl().equals("default")){
            Glide.with(context).load(requestList.get(position).getStudentProfileImageUrl()).into(holder.mStudentImage);
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
