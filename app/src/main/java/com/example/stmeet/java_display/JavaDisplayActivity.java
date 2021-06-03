package com.example.stmeet.java_display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.stmeet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class    JavaDisplayActivity extends AppCompatActivity {

    private List<JavaDisplayObject> javaList;
    private RecyclerView mJavaRecyclerView;
    private JavaDisplayAdapter mJavaAdapter;
    private RecyclerView.LayoutManager mRequestLayoutManager;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users");

    DatabaseReference dbJava;

    private String currentUserId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_display);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userId = mAuth.getCurrentUser().getUid();

        mJavaRecyclerView = findViewById(R.id.javaRecycler);
        mJavaRecyclerView.setNestedScrollingEnabled(false);
        mJavaRecyclerView.setHasFixedSize(true);
        mJavaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        javaList = new ArrayList<>();
        mJavaAdapter = new JavaDisplayAdapter(this, javaList);
        mJavaRecyclerView.setAdapter(mJavaAdapter);


        /*dbJava = FirebaseDatabase.getInstance().getReference().child("Users");
        dbJava.addListenerForSingleValueEvent(valueEventListener);*/

        Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("subject").equalTo("java");
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
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

    /*@Override
    protected void onStart() {
        super.onStart();
        mJavaAdapter.startListening();
    }*/
}