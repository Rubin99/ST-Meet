package com.example.stmeet.student_requests;

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

import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.info.TeacherInfoUserActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.MatchesObject;
import com.example.stmeet.matches.TeacherMatchesActivity;
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

public class StudentRequestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRequestRecyclerView;
    private RecyclerView.Adapter mRequestAdapter;
    private RecyclerView.LayoutManager mRequestLayoutManager;

    private String currenrUserId;

    private FirebaseAuth mAuth;

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_request_list);

        currenrUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAuth = FirebaseAuth.getInstance();

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
    // sidebar
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_teacher:
                Intent t = new Intent(StudentRequestActivity.this, MainActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(StudentRequestActivity.this, TeacherInfoUserActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(StudentRequestActivity.this, TeacherMatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_request:
                Intent r= new Intent(StudentRequestActivity.this, StudentRequestActivity.class);
                startActivity(r);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(StudentRequestActivity.this, AboutUsActivity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut(); //!!!!!!!!!!!!!!!!!!!!!!! Need to add mAuth
                Intent l= new Intent(StudentRequestActivity.this, ChooseRoleActivity.class);
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