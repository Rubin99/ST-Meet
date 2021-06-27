package com.example.stmeet.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.info.TeacherInfoUserActivity;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.matches.TeacherMatchesActivity;
import com.example.stmeet.student_requests.StudentRequestActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;

    private EditText mSendMessageEditText;
    private Button mSendButton;

    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";

    private RatingBar mRate;
    private EditText mComment;
    private Button mSubmit;

    private String currentUserId, matchId, chatId;

    DatabaseReference mDatabaseUser, mDatabaseChat;

    private FirebaseAuth mAuth;


    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_chat);

        mAuth = FirebaseAuth.getInstance();

        // For navigation sidebar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navmenu);
        navigationView.setItemIconTintList(null);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close); //?
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        //--------------------------------------------------------------

        matchId = getIntent().getExtras().getString("matchId");

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("connections").child("matches").child(matchId).child("ChatId"); //chatId
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        getChatId();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(TeacherChatActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new ChatAdapter(getDataSetChat(), TeacherChatActivity.this);
        mRecyclerView.setAdapter(mChatAdapter);

        mSendMessageEditText = findViewById(R.id.message);
        mSendButton = findViewById(R.id.send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String sendMessageText = mSendMessageEditText.getText().toString();

        if (!sendMessageText.isEmpty()){
            DatabaseReference newMessageDb = mDatabaseChat.push();

            Map newMessage = new HashMap();
            newMessage.put("createdByUser", currentUserId);
            newMessage.put("text", sendMessageText);

            newMessageDb.setValue(newMessage);
        }
        mSendMessageEditText.setText(null);
    }

    private void getChatId() {
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    chatId = snapshot.getValue().toString();
                    mDatabaseChat = mDatabaseChat.child(chatId);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                if(snapshot.exists()){
                    String message = null;
                    String createdByUser = null;

                    if(snapshot.child("text").getValue()!=null){
                        message = snapshot.child("text").getValue().toString();
                    }
                    if(snapshot.child("createdByUser").getValue()!=null){
                        createdByUser = snapshot.child("createdByUser").getValue().toString();
                    }

                    if(message!=null && createdByUser!=null){
                        Boolean currentUserBoolean = false;
                        if(createdByUser.equals(currentUserId)){
                            currentUserBoolean = true;
                        }
                        ChatObject newMessage = new ChatObject(message, currentUserBoolean);
                        resultsChat.add(newMessage);
                        mChatAdapter.notifyDataSetChanged();
                    }
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
    }

    private ArrayList<ChatObject> resultsChat = new ArrayList<ChatObject>();
    private List<ChatObject> getDataSetChat() {
        return  resultsChat;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_teacher:
                Intent t = new Intent(TeacherChatActivity.this, MainActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(TeacherChatActivity.this, TeacherInfoUserActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(TeacherChatActivity.this, TeacherMatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_request:
                Intent r= new Intent(TeacherChatActivity.this, StudentRequestActivity.class);
                startActivity(r);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(TeacherChatActivity.this, AboutUsActivity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut(); //!!!!!!!!!!!!!!!!!!!!!!! Need to add mAuth
                Intent l= new Intent(TeacherChatActivity.this, ChooseRoleActivity.class);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                finish();
                break;

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.paypal:
                Toast.makeText(this, "Paypal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.videoCall:
                Toast.makeText(this, "Video Call", Toast.LENGTH_SHORT).show();
                Intent video = new Intent(TeacherChatActivity.this, VideoActivity.class);
                startActivity(video);
                break;
            case R.id.rateTeacher:
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();

                Intent rate= new Intent(TeacherChatActivity.this, RateStudentActivity.class);
                Bundle b = new Bundle();
                b.putString("matchId", matchId);
                b.putString("chatId", chatId);
                rate.putExtras(b);
                startActivity(rate);

                /*if (!isFragmentDisplayed){
                    displayFragment();
                }else {
                    closeFragment();
                }
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

}