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
import com.example.stmeet.matches.MatchesViewHolder;
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

    public JavaDisplayAdapter (List<JavaDisplayObject> javaList, Context context){
        this.javaList = javaList;
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public JavaDisplayViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(context).inflate(R.layout.items_java, parent, false);
        return new JavaDisplayViewHolder(view);*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_java, null, false);
        RecyclerView.LayoutParams rlp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rlp);
        JavaDisplayViewHolder jdv = new JavaDisplayViewHolder((view));

        return jdv;

/*        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        JavaDisplayViewHolder rcv = new JavaDisplayViewHolder((view));

        return rcv;*/

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JavaDisplayViewHolder holder, int position) {
        holder.mTeacherId.setText(javaList.get(position).getUserId());
        holder.mJavaName.setText(javaList.get(position).getName());
        holder.mJavaSubject.setText(javaList.get(position).getSubject());
        if (!javaList.get(position).getProfileImageUrl().equals("default")){
            Glide.with(context).load(javaList.get(position).getProfileImageUrl()).into(holder.mJavaImage);
        }
        String currentUId;
        FirebaseAuth mAuth;
        DatabaseReference usersDb;
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        /*holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDb.child().child("connections").child("accepted").child(currentUId).setValue(true);
            }
        });
*/
        /*FirebaseAuth mAuth;
        String currentUId;
        DatabaseReference usersDb;
        String javaID = javaDisplayObject.userId;

        mAuth = FirebaseAuth.getInstance();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();*/
        /*holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //long userID = getItemId(position);

                //JavaDisplayObject obj = (JavaDisplayObject) position;
                //String id = obj.getUserId();

                //String userId = JavaDisplayObject.getUserId();
                //usersDb.child(oppositeUserRole).child(userId).child("connections").child("accepted").child(currentUId).setValue(true);
                usersDb.child(String.valueOf(position)).child("connections").child("accepted").child(currentUId).setValue(true);

            }
        });*/

    }
/*

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(JavaDisplayViewHolder.ClickListener clickListener){

    }
*/



    @Override
    public int getItemCount() {
        return javaList.size();
    }
}
