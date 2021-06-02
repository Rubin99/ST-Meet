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

public class JavaDisplayAdapter extends RecyclerView.Adapter<JavaDisplayAdapter.MyViewHolder> {

    Context context;

    ArrayList<JavaDisplayObject> list;

    public JavaDisplayAdapter(Context context, ArrayList<JavaDisplayObject> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_java, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JavaDisplayAdapter.MyViewHolder holder, int position) {
        JavaDisplayObject javaObj = list.get(position);
        holder.mJavaId.setText(javaObj.getJavaId());
        holder.mJavaName.setText(javaObj.getJavaName());
        holder.mJavaSubject.setText(javaObj.getJavaSubject());
        /*if (!list.get(position).getJavaProfileImageUrl().equals("default")){
            Glide.with(context).load(list.get(position).getJavaProfileImageUrl()).into(holder.mJavaProfile);
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mJavaId, mJavaName, mJavaSubject;
        //ImageView mJavaProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mJavaId =itemView.findViewById(R.id.javaId);
            mJavaName = itemView.findViewById(R.id.javaName);
            mJavaSubject = itemView.findViewById(R.id.javaSubject);
           // mJavaProfile = itemView.findViewById(R.id.javaImage);
            //mJavaId.setVisibility(View.INVISIBLE);

        }
    }
}
