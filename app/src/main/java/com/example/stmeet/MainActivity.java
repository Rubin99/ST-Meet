package com.example.stmeet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.info.cards;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.info.arrayAdapter;
import com.example.stmeet.matches.MatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private cards cards_data[];
    private com.example.stmeet.info.arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;

    private String currentUId;
    private DatabaseReference usersDb;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();

        checkUserRole();



        rowItems = new ArrayList<cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.topic, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("connections").child("rejected").child(currentUId).setValue(true);

                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("connections").child("accepted").child(currentUId).setValue(true);

                isConnectionMatch(userId);

                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb = usersDb.child(currentUId).child("connections").child("accepted").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(MainActivity.this, "new connection", Toast.LENGTH_LONG).show();

                    //have to make sure matches sub branch is within userId !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    usersDb.child(snapshot.getKey()).child("connections").child("matches").child(currentUId).setValue(true);
                    usersDb.child(currentUId).child("connections").child("matches").child(snapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private String userRole;     //either student or teacher
    private String oppositeUserRole;  //opposite for the one in userRole
    public void checkUserRole(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("role").getValue() != null){
                        userRole =snapshot.child("role").getValue().toString();
                        switch (userRole){
                            case "Student":
                                oppositeUserRole = "Teacher";
                                break;
                            case "Teacher":
                                oppositeUserRole = "Student";
                                break;
                        }
                        getOppositeUserRole();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    public void getOppositeUserRole(){
        //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //if statement checks if we have already rejected or accedpted and will never show it again!!!!! WILL have to change it.
                if (snapshot.exists() && !snapshot.child("connections").child("rejected").hasChild(currentUId) && !snapshot.child("connections").child("accepted").hasChild(currentUId) && snapshot.child("role").getValue().toString().equals(oppositeUserRole)){
//                if (snapshot.exists()){
                    String profileImageUrl = "default";
                    if (!snapshot.child("profileImageUrl").getValue().equals("default")){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    cards item = new cards(snapshot.getKey(), snapshot.child("name").getValue().toString(), profileImageUrl);
                    rowItems.add(item);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        /*DatabaseReference teacherDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Teacher");
        teacherDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.getKey().equals(user.getUid())){
                    userRole = "Teacher";
                    oppositeUserRole = "Student";
                }
            }
            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });*/
    }

    public void LogoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public void goToUserInfo(View view) {
        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
        startActivity(intent);
        return;
    }

    public void goToMatches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }
}