package com.example.stmeet.java_display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stmeet.MainActivity;
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

    private FirebaseAuth mAuth;
    private String currentUId;
    private DatabaseReference usersDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_display);

        mAuth = FirebaseAuth.getInstance();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();

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

        ////////////////////

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

                /*int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);*/
            }

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
        }).attachToRecyclerView(mJavaRecyclerView);

        ///////////////////////////


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

    /* public void sendRequest(View view) {

                JavaDisplayObject object = view;
                String userId = JavaDisplayObject.getUserId();
                //usersDb.child(oppositeUserRole).child(userId).child("connections").child("accepted").child(currentUId).setValue(true);
                usersDb.child(userId).child("connections").child("accepted").child(currentUId).setValue(true);

                Toast.makeText(JavaDisplayActivity.this, "Request sent!", Toast.LENGTH_SHORT).show();
            }*/
    /*@Override
    protected void onStart() {
        super.onStart();
        mJavaAdapter.startListening();
    }*/
}