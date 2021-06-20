package com.example.stmeet.student_requests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.stmeet.R;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.MatchesObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StudentRequestActivity extends AppCompatActivity {

    private RecyclerView mRequestRecyclerView;
    private RecyclerView.Adapter mRequestAdapter;
    private RecyclerView.LayoutManager mRequestLayoutManager;

    private String currenrUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_request_list);

        currenrUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRequestRecyclerView =findViewById(R.id.recyclerViewSR);
        mRequestRecyclerView.setNestedScrollingEnabled(false);
        mRequestRecyclerView.setHasFixedSize(true);
        mRequestLayoutManager = new LinearLayoutManager(StudentRequestActivity.this);
        mRequestRecyclerView.setLayoutManager(mRequestLayoutManager);
        mRequestAdapter = new StudentRequestAdapter(getDataSetRequests(), StudentRequestActivity.this);
        mRequestRecyclerView.setAdapter(mRequestAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(StudentRequestActivity.this, DividerItemDecoration.VERTICAL);
        mRequestRecyclerView.addItemDecoration(decoration);
        
        getUserRequestId();
    }

    private void getUserRequestId() {
        DatabaseReference requestDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currenrUserId).child("connections").child("accepted");
        requestDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot request : snapshot.getChildren()) {
                        FetchRequestInformation(request.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void FetchRequestInformation(String key) {
        DatabaseReference userRequestDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userRequestDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists() && !snapshot.child("connections").child("matches").hasChild(currenrUserId)){
                    String userId = snapshot.getKey();
                    String name = "";
                    String profileImageUrl = "";
                    if (snapshot.child("name").getValue() != null){
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }

                    StudentRequestObject objSR = new StudentRequestObject(userId, name, profileImageUrl); //
                    resultsRequests.add(objSR);

                    mRequestAdapter.notifyDataSetChanged(); // IMP as recyclerView can start again and look for things that change

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private ArrayList<StudentRequestObject> resultsRequests = new ArrayList<StudentRequestObject>();
    private List<StudentRequestObject> getDataSetRequests() {
        return resultsRequests;
    }
}