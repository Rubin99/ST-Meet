package com.example.stmeet.java_display;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.R;
import com.example.stmeet.info.cards;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class JavaDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView mName, mSubject, mId;
    ImageView mImage;
    ImageView mAccept, mReject;

    private FirebaseAuth mAuth;
    private String currentUId;
    private DatabaseReference usersDb;

    View mView;

    public JavaDisplayViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mId = (TextView) itemView.findViewById(R.id.javaId);
        mName = itemView.findViewById(R.id.javaName);
        mSubject = itemView.findViewById(R.id.javaSubject);
        mImage = itemView.findViewById(R.id.javaImage);
        //mAccept = itemView.findViewById(R.id.accept);
        //mReject = itemView.findViewById(R.id.reject);

        mAuth = FirebaseAuth.getInstance();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();

        mView = itemView;

        /*mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

                //long userID = getItemId(position);


//                String userId = JavaDisplayObject.getUserId();
                //usersDb.child(oppositeUserRole).child(userId).child("connections").child("accepted").child(currentUId).setValue(true);
                //usersDb.child().child("connections").child("accepted").child(currentUId).setValue(true);

            }
        });*/
        /*//item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
*/

    }



    private JavaDisplayViewHolder.ClickListener mClickListener;

    @Override
    public void onClick(View v) {

    }

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(JavaDisplayViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
