package com.example.stmeet.matches;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;

import org.jetbrains.annotations.NotNull;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchid;

    public MatchesViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchid = itemView.findViewById(R.id.matchId);
    }

    @Override
    public void onClick(View v) {

    }
}
