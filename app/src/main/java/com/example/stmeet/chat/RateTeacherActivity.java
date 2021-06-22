package com.example.stmeet.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.SubjectListActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RateTeacherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RatingBar mRate;
    private EditText mComment;
    private Button mSubmit;

    private String mMatchId, mChatId;

    private String currentUserId;

    DatabaseReference mDatabaseUser, mDatabaseChat;

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_teacher);

        mRate = findViewById(R.id.ratingBar3);
        mSubmit  =findViewById(R.id.rateButton);

        mMatchId = getIntent().getExtras().getString("matchId");
        mChatId = getIntent().getExtras().getString("chatId");

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("connections").child("matches").child(mMatchId).child("ChatId"); //chatId
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

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

        mRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mDatabaseUser.setValue(rating);
                DatabaseReference mTeacherRatingDb = FirebaseDatabase.getInstance().getReference().child("Users").child(mMatchId).child("rating");
                mTeacherRatingDb.child(mChatId).setValue(rating);
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_subject:
                Intent h= new Intent(RateTeacherActivity.this, SubjectListActivity.class);
                h.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(h);
                break;
            case R.id.nav_teacher:
                Intent t = new Intent(RateTeacherActivity.this, MainActivity.class);
                t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(RateTeacherActivity.this, UserInfoActivity.class);
                p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(RateTeacherActivity.this, MatchesActivity.class);
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(m);
                break;
            case R.id.nav_logout:
                //mAuth.signOut();
                Intent l= new Intent(RateTeacherActivity.this, ChooseLoginRegistrationActivity.class);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                finish();
                break;

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}