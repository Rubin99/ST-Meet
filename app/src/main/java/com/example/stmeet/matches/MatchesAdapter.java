package com.example.stmeet.matches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<MatchesObject> matchesList; //contains information passed through matchesObject
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rcv = new MatchesViewHolder((layoutView));

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MatchesViewHolder holder, int position) {
        holder.mMatchid.setText(matchesList.get(position).getUserId());

    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
