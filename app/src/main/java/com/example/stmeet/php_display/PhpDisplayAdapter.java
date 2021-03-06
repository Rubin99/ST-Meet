package com.example.stmeet.php_display;

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

public class PhpDisplayAdapter extends RecyclerView.Adapter<PhpDisplayViewHolder> {

    private Context context;
    private List<PhpDisplayObject> phpList;

    public PhpDisplayAdapter(List<PhpDisplayObject> phpList, Context context){
        this.phpList = phpList;
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public PhpDisplayViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_php, null, false);
        RecyclerView.LayoutParams rlp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rlp);
        PhpDisplayViewHolder pdv = new PhpDisplayViewHolder((view));

        return pdv;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PhpDisplayViewHolder holder, int position) {
        holder.mPhpTeacherId.setText(phpList.get(position).getUserId());
        holder.mPhpName.setText(phpList.get(position).getName());
        holder.mHourlyRate.setText(phpList.get(position).getHourlyRate());
        //holder.mJavaRating.setText(javaList.get(position).getRating());
        if (phpList.get(position).getRating() == ""){
            holder.mRatingBar.setRating((float) 0.0);
        }else {
            holder.mRatingBar.setRating(Float.parseFloat(phpList.get(position).getRating()));
        }
        if (!phpList.get(position).getProfileImageUrl().equals("default")){
            Glide.with(context).load(phpList.get(position).getProfileImageUrl()).into(holder.mPhpImage);
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

                //PhpDisplayObject obj = (PhpDisplayObject) position;
                //String id = obj.getUserId();

                //String userId = PhpDisplayObject.getUserId();
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
    public void setOnClickListener(PhpDisplayViewHolder.ClickListener clickListener){

    }
*/



    @Override
    public int getItemCount() {
        return phpList.size();
    }
}
