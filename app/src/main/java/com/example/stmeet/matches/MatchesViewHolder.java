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

import org.jetbrains.annotations.NotNull;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchid, mMatchName;
    public ImageView mMatchImage;

    public MatchesViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchid = itemView.findViewById(R.id.matchId);
        mMatchName = itemView.findViewById(R.id.matchName);
        mMatchImage = itemView.findViewById(R.id.matchImage);
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
