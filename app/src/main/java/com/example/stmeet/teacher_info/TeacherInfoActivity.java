package com.example.stmeet.teacher_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.stmeet.AboutUs2Activity;
import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.SubjectListActivity;
import com.example.stmeet.info.TeacherInfoUserActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.MatchesAdapter;
import com.example.stmeet.matches.MatchesObject;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    public TextView mTeacherId, mTeacherName, mTeacherSubject, mTeacherEducation, mTeacherSchool, mTeacherAbout, mTeacherHourlyRate;
    public ImageView mTeacherImage;
    public RatingBar mRatingBar;

    private FirebaseAuth mAuth;
    private DatabaseReference mTeacherDatabase, mRatingCount;

    private String userId, name, profileImageUrl, education, school, subject, about, hourlyRate;


    private String teacherId;
    private String matchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);

        teacherId = getIntent().getExtras().getString("teacherId");
        matchId = getIntent().getExtras().getString("matchId");

        mTeacherId = findViewById(R.id.infoTeacherId);
        mTeacherName = findViewById(R.id.otherName);
        mTeacherSubject = findViewById(R.id.otherSubject);
        mTeacherEducation = findViewById(R.id.otherEducation);
        mTeacherSchool = findViewById(R.id.otherSchool);
        mTeacherAbout = findViewById(R.id.otherAbout);
        mTeacherImage = findViewById(R.id.otherProfileImage);
        mRatingBar = findViewById(R.id.ratingBarInfo);
        mTeacherHourlyRate = findViewById(R.id.hourlyRate);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mTeacherDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(teacherId); // getting inside the userid

        getTeacherInfo();

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

    }

    private void getTeacherInfo() {
        mTeacherDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name") != null){
                        name = map.get("name").toString();
                        mTeacherName.setText(name);
                    }
                    if (map.get("userId") != null){
                        userId = map.get("userId").toString();
                        mTeacherId.setText(userId);
                    }
                    if (map.get("subject") != null){
                        subject = map.get("subject").toString();
                        mTeacherSubject.setText(subject);
                    }
                    if (map.get("education") != null){
                        education = map.get("education").toString();
                        mTeacherEducation.setText(education);
                    }
                    if (map.get("school") != null){
                        school = map.get("school").toString();
                        mTeacherSchool.setText(school);
                    }
                    if (map.get("about") != null){
                        about = map.get("about").toString();
                        mTeacherAbout.setText(about);
                    }
                    if (map.get("hourlyRate") != null){
                        hourlyRate = map.get("hourlyRate").toString();
                        mTeacherHourlyRate.setText(hourlyRate);
                    }
                    if (map.get("profileImageUrl") != null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch(profileImageUrl){
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mTeacherImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mTeacherImage);
                                break;
                        }
                    }
                    int ratingSum = 0;
                    float ratingsTotal = 0;
                    float ratingsAvg = 0;
                    for (DataSnapshot child : snapshot.child("rating").getChildren()){
                        ratingSum = ratingSum + Integer.valueOf(child.getValue().toString());
                        ratingsTotal++;
                    }
                    if(ratingsTotal!= 0){
                        ratingsAvg = ratingSum/ratingsTotal;
                        mRatingBar.setRating(ratingsAvg);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_subject:
                Intent h= new Intent(TeacherInfoActivity.this, SubjectListActivity.class);
                startActivity(h);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(TeacherInfoActivity.this, UserInfoActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(TeacherInfoActivity.this, MatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(TeacherInfoActivity.this, AboutUs2Activity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut(); ///-------------------------------------
                Intent l= new Intent(TeacherInfoActivity.this, ChooseRoleActivity.class);
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