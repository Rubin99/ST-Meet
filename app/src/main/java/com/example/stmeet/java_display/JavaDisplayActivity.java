package com.example.stmeet.java_display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.MatchesObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class JavaDisplayActivity extends AppCompatActivity {

    private RecyclerView mJavaRecyclerView;
    private RecyclerView.Adapter mJavaAdapter;
    private RecyclerView.LayoutManager mJavaLayoutManager;

    private String currentUserId;

    private RatingBar mRatingBar;

    DatabaseReference testDb;

    private Button mButtonAsc, mButtonDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_display);

        mButtonAsc = findViewById(R.id.sortAsc);
        mButtonDesc = findViewById(R.id.sortDsc);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mJavaRecyclerView = findViewById(R.id.javaRecycler);
        mJavaRecyclerView.setNestedScrollingEnabled(false);
        mJavaRecyclerView.setHasFixedSize(true);
        mJavaLayoutManager = new LinearLayoutManager(JavaDisplayActivity.this);
        mJavaRecyclerView.setLayoutManager(mJavaLayoutManager);
        mJavaAdapter = new JavaDisplayAdapter(getDataSetJava(), JavaDisplayActivity.this);
        mJavaRecyclerView.setAdapter(mJavaAdapter);


        DividerItemDecoration decoration = new DividerItemDecoration(JavaDisplayActivity.this, DividerItemDecoration.VERTICAL);
        mJavaRecyclerView.addItemDecoration(decoration);

        getUserJavaId();


        /*dbJava = FirebaseDatabase.getInstance().getReference().child("Users");
        dbJava.addListenerForSingleValueEvent(valueEventListener);*/

       /* Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("subject").equalTo("java");
        query.addListenerForSingleValueEvent(valueEventListener);
*/
        ////////////////////

     /*   new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

                *//*int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);*//*
            }

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                usersDb.child("5eemG2cwJRdbxO9r44XmbFpw44t2").child("connections").child("accepted").child(currentUId).setValue(true);
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
        }).attachToRecyclerView(mJavaRecyclerView);*/

        ///////////////////////////


        mButtonAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(resultsJava, new Comparator<JavaDisplayObject>() {
                    @Override
                    public int compare(JavaDisplayObject o1, JavaDisplayObject o2) {
                        return  o1.getRating().compareToIgnoreCase(o2.getRating());
                    }

                });
                mJavaAdapter.notifyDataSetChanged();
            }
        });
        mButtonDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(resultsJava);
                mJavaAdapter.notifyDataSetChanged();
            }
        });
    }


    private void getUserJavaId() {
        Query javaDb = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("subject").equalTo("java");
        javaDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot java : snapshot.getChildren()){
                        FetchMatchInformation(java.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userJavaDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userJavaDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userId = snapshot.getKey();
                    String name = "";
                    String subject = "";
                    String profileImageUrl = "";
                    String rating = "";
                    if (snapshot.child("name").getValue() != null){
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("subject").getValue() != null){
                        subject = snapshot.child("subject").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    float ratingSum = 0;
                    float ratingsTotal = 0;
                    float ratingsAvg = 0;
                    for (DataSnapshot child : snapshot.child("rating").getChildren()){
                        ratingSum = ratingSum + Integer.valueOf(child.getValue().toString());
                        ratingsTotal++;
                    }
                   /* if(ratingsTotal != 0){
                        ratingsAvg = ratingSum/ratingsTotal;
                        rating.setRating(ratingsAvg);
                    }*/
                    if (snapshot.child("rating").getValue() != null){
                        rating = String.valueOf(ratingSum/ratingsTotal);
                    }

                    JavaDisplayObject obj = new JavaDisplayObject(userId, name, subject, profileImageUrl, rating); //
                    resultsJava.add(obj);

                    mJavaAdapter.notifyDataSetChanged(); // IMP as recyclerView can start again and look for things that change

                    testDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /* ValueEventListener valueEventListener = new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
             javaList.clear();
             if (snapshot.exists()){
                 for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                     JavaDisplayObject javaDisplayObject = dataSnapshot.getValue(JavaDisplayObject.class);
                     javaList.add(javaDisplayObject);
                 }
                 mJavaAdapter.notifyDataSetChanged();
             }
         }
 
         @Override
         public void onCancelled(@NonNull @NotNull DatabaseError error) {
 
         }
     };
 */
    /* public void sendRequest(View view) {

                JavaDisplayObject object = view;
                String userId = JavaDisplayObject.getUserId();
                //usersDb.child(oppositeUserRole).child(userId).child("connections").child("accepted").child(currentUId).setValue(true);
                usersDb.child(userId).child("connections").child("accepted").child(currentUId).setValue(true);

                Toast.makeText(JavaDisplayActivity.this, "Request sent!", Toast.LENGTH_SHORT).show();
            }*/
   private ArrayList<JavaDisplayObject> resultsJava = new ArrayList<JavaDisplayObject>();
    private List<JavaDisplayObject> getDataSetJava() {
        return  resultsJava;
    }
    
}