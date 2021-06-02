package com.example.stmeet.java_display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.stmeet.R;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.MatchesObject;
import com.example.stmeet.student_requests.StudentRequestObject;
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

public class JavaDisplayActivity extends AppCompatActivity {

    private ArrayList<JavaDisplayObject> list;
    private RecyclerView mJavaRecyclerView;
    private JavaDisplayAdapter mJavaAdapter;
    Query databaseReference;

    private String currentUserId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_display);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userId = mAuth.getCurrentUser().getUid();

        mJavaRecyclerView = findViewById(R.id.recycler1);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("subject").equalTo("java");
        mJavaRecyclerView.setHasFixedSize(true);
        mJavaRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        mJavaAdapter = new JavaDisplayAdapter(JavaDisplayActivity.this, (ArrayList<JavaDisplayObject>) getDataSetRequests());
        mJavaRecyclerView.setAdapter(mJavaAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        FetchRequestInformation(dataSnapshot.getKey());
                    }
                    mJavaAdapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void FetchRequestInformation(String key) {
        DatabaseReference javaDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        javaDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userId = snapshot.getKey();
                    String name = "";
                    String subject = "";
                   /* String profileImageUrl = "";
                    if (snapshot.child("name").getValue() != null) {
                        name = snapshot.child("name").getValue().toString();
                    }*/
                    JavaDisplayObject jObj = new JavaDisplayObject(userId, name, subject);
                    resultsJava.add(jObj);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private ArrayList<JavaDisplayObject> resultsJava = new ArrayList<JavaDisplayObject>();
    private List<JavaDisplayObject> getDataSetRequests() {
        return resultsJava;
    }

}