package com.example.stmeet.java_display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.stmeet.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JavaDisplayAdapter extends RecyclerView.Adapter<JavaDisplayAdapter.JavaViewHolder> {

    private Context context;
    private List<JavaDisplayObject> javaList;

    public JavaDisplayAdapter (Context context, List<JavaDisplayObject> javaList){
        this.context = context;
        this.javaList = javaList;
    }

    class JavaViewHolder extends RecyclerView.ViewHolder{

        TextView mName, mSubject;
        ImageView mImage;

        public JavaViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.javaName);
            mSubject = itemView.findViewById(R.id.javaSubject);
            mImage = itemView.findViewById(R.id.javaImage);
        }
    }

    @NotNull
    @Override
    public JavaViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_java, parent, false);
        return new JavaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JavaDisplayAdapter.JavaViewHolder holder, int position) {
        JavaDisplayObject javaDisplayObject = javaList.get(position);
        holder.mName.setText(javaDisplayObject.name);
        holder.mSubject.setText(javaDisplayObject.subject);
        if (!javaDisplayObject.getProfileImageUrl().equals("default")){
            Glide.with(context).load(javaDisplayObject.getProfileImageUrl()).into(holder.mImage);
        }
    }

    @Override
    public int getItemCount() {
        return javaList.size();
    }
}
