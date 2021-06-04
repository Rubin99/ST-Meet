package com.example.stmeet.java_display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.stmeet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JavaDisplayAdapter extends RecyclerView.Adapter<JavaDisplayViewHolder> {

    private Context context;
    private List<JavaDisplayObject> javaList;

    public JavaDisplayAdapter (Context context, List<JavaDisplayObject> javaList){
        this.context = context;
        this.javaList = javaList;
    }


    @NonNull
    @NotNull
    @Override
    public JavaDisplayViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_java, parent, false);
        return new JavaDisplayViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JavaDisplayViewHolder holder, int position) {
        JavaDisplayObject javaDisplayObject = javaList.get((Integer) position);
        holder.mId.setText(javaDisplayObject.getUserId());
        holder.mName.setText(javaDisplayObject.name);
        holder.mSubject.setText(javaDisplayObject.subject);
        if (!javaDisplayObject.getProfileImageUrl().equals("default")){
            Glide.with(context).load(javaDisplayObject.getProfileImageUrl()).into(holder.mImage);
        }
        FirebaseAuth mAuth;
        String currentUId;
        DatabaseReference usersDb;

        mAuth = FirebaseAuth.getInstance();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();
       /* holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //long userID = getItemId(position);

                //JavaDisplayObject obj = (JavaDisplayObject) position;
                //String id = obj.getUserId();

                //String userId = JavaDisplayObject.getUserId();
                //usersDb.child(oppositeUserRole).child(userId).child("connections").child("accepted").child(currentUId).setValue(true);
                //usersDb.child(id).child("connections").child("accepted").child(currentUId).setValue(true);

            }
        });*/

    }

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(JavaDisplayViewHolder.ClickListener clickListener){

    }



    @Override
    public int getItemCount() {
        return javaList.size();
    }
}
