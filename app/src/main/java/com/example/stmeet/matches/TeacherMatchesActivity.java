package com.example.stmeet.matches;

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
import android.view.View;
import android.widget.Toast;

import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.SubjectListActivity;
import com.example.stmeet.info.TeacherInfoUserActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.student_requests.StudentRequestActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class TeacherMatchesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager;

    private String currentUserId;

    private FirebaseAuth mAuth;

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_matches);

        mAuth = FirebaseAuth.getInstance();

        // For navigation sidebar -------------------------------------------------------------------------------------

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

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchesLayoutManager = new LinearLayoutManager(TeacherMatchesActivity.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter = new TeacherMatchesAdapter(getDataSetMatches(), TeacherMatchesActivity.this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(TeacherMatchesActivity.this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        getUserMatchId();

        //---------------------------FAB ICON----------------------------------------------------------------------------------
        fab = findViewById(R.id.find_teacher);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeacherMatchesActivity.this, "List of Requests", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherMatchesActivity.this, StudentRequestActivity.class);
                startActivity(intent);
                return;
            }
        });
        //---------------------------FAB ICON----------------------------------------------------------------------------------


    }

    private void getUserMatchId() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("connections").child("matches");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot match : snapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userMatchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userMatchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userId = snapshot.getKey();
                    String name = "";
                    String profileImageUrl = "";
                    if (snapshot.child("name").getValue() != null){
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }

                    TeacherMatchesObject obj = new TeacherMatchesObject(userId, name, profileImageUrl); //
                    resultsMatches.add(obj);

                    mMatchesAdapter.notifyDataSetChanged(); // IMP as recyclerView can start again and look for things that change

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private ArrayList<TeacherMatchesObject> resultsMatches = new ArrayList<TeacherMatchesObject>();
    private List<TeacherMatchesObject> getDataSetMatches() {
        return  resultsMatches;
    }

    // sidebar
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_teacher:
                Intent t = new Intent(TeacherMatchesActivity.this, MainActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(TeacherMatchesActivity.this, TeacherInfoUserActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(TeacherMatchesActivity.this, TeacherMatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_request:
                Intent r= new Intent(TeacherMatchesActivity.this, StudentRequestActivity.class);
                startActivity(r);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(TeacherMatchesActivity.this, AboutUsActivity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut(); //!!!!!!!!!!!!!!!!!!!!!!! Need to add mAuth
                Intent l= new Intent(TeacherMatchesActivity.this, ChooseRoleActivity.class);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                finish();
                break;

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //-------------------------------------------------------------
}