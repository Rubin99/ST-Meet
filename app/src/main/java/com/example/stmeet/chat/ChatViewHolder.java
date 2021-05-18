package com.example.stmeet.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;

import org.jetbrains.annotations.NotNull;

public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ChatViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    }
}
