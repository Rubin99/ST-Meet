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

import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.info.TeacherInfoUserActivity;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.matches.TeacherMatchesActivity;
import com.example.stmeet.student_requests.StudentRequestActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RateStudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RatingBar mRate;
    private EditText mComment;
    private Button mSubmit;

    private String mMatchId, mChatId;

    private String currentUserId;

    private FirebaseAuth mAuth;

    DatabaseReference mDatabaseUser, mDatabaseChat;

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_student);

        mAuth = FirebaseAuth.getInstance();

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

                DatabaseReference mStudentRatingDb = FirebaseDatabase.getInstance().getReference().child("Users").child(mMatchId).child("rating");
                mStudentRatingDb.child(mChatId).setValue(rating);
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

            case R.id.nav_profile:
                Intent p= new Intent(RateStudentActivity.this, TeacherInfoUserActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(RateStudentActivity.this, TeacherMatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_request:
                Intent r= new Intent(RateStudentActivity.this, StudentRequestActivity.class);
                startActivity(r);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(RateStudentActivity.this, AboutUsActivity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut(); //!!!!!!!!!!!!!!!!!!!!!!! Need to add mAuth
                Intent l= new Intent(RateStudentActivity.this, ChooseRoleActivity.class);
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