package com.example.stmeet.matches;

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

public class TeacherMatchesAdapter extends RecyclerView.Adapter<TeacherMatchesViewHolder> {
    private List<TeacherMatchesObject> matchesList; //contains information passed through matchesObject
    private Context context;

    public TeacherMatchesAdapter(List<TeacherMatchesObject> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TeacherMatchesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_matches_teacher, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        TeacherMatchesViewHolder rcv = new TeacherMatchesViewHolder((layoutView));

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TeacherMatchesViewHolder holder, int position) {
        holder.mMatchid.setText(matchesList.get(position).getUserId());
        holder.mMatchName.setText(matchesList.get(position).getName());
        if (!matchesList.get(position).getProfileImageUrl().equals("default")){
            Glide.with(context).load(matchesList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
        }
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}

