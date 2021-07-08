package com.example.stmeet.db_display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.stmeet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DbDisplayAdapter extends RecyclerView.Adapter<DbDisplayViewHolder> {

    private Context context;
    private List<DbDisplayObject> dbList;

    public DbDisplayAdapter(List<DbDisplayObject> dbList, Context context){
        this.dbList = dbList;
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public DbDisplayViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_db, null, false);
        RecyclerView.LayoutParams rlp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rlp);
        DbDisplayViewHolder ddv = new DbDisplayViewHolder((view));

        return ddv;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DbDisplayViewHolder holder, int position) {
        holder.mDbTeacherId.setText(dbList.get(position).getUserId());
        holder.mDbName.setText(dbList.get(position).getName());
        holder.mHourlyRate.setText(dbList.get(position).getHourlyRate());
        if (dbList.get(position).getRating() == ""){
            holder.mDbRatingBar.setRating((float) 0.0);
        }else {
            holder.mDbRatingBar.setRating(Float.parseFloat(dbList.get(position).getRating()));
        }
        if (!dbList.get(position).getProfileImageUrl().equals("default")){
            Glide.with(context).load(dbList.get(position).getProfileImageUrl()).into(holder.mDbImage);
        }

    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }
}
