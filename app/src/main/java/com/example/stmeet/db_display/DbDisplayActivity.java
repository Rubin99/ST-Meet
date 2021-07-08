package com.example.stmeet.db_display;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stmeet.AboutUs2Activity;
import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.SubjectListActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.java_display.JavaDisplayObject;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.student_requests.StudentRequestActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DbDisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mDbRecyclerView;
    private RecyclerView.Adapter mDbAdapter;
    private RecyclerView.LayoutManager mDbLayoutManager;

    private String currentUserId;

    private FirebaseAuth mAuth;

    private RatingBar mRatingBar;

    DatabaseReference testDb;

    private Button mButtonAsc, mButtonDesc;

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_display);

        /*mButtonAsc = findViewById(R.id.sortAsc);
        mButtonDesc = findViewById(R.id.sortDsc);*/

        mAuth = FirebaseAuth.getInstance();

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDbRecyclerView = findViewById(R.id.dbRecycler);
        mDbRecyclerView.setNestedScrollingEnabled(false);
        mDbRecyclerView.setHasFixedSize(true);
        mDbLayoutManager = new LinearLayoutManager(DbDisplayActivity.this);
        mDbRecyclerView.setLayoutManager(mDbLayoutManager);
        mDbAdapter = new DbDisplayAdapter(getDataSetDb(), DbDisplayActivity.this);
        mDbRecyclerView.setAdapter(mDbAdapter);


        DividerItemDecoration decoration = new DividerItemDecoration(DbDisplayActivity.this, DividerItemDecoration.VERTICAL);
        mDbRecyclerView.addItemDecoration(decoration);

        getUserDbId();

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


    private void getUserDbId() {
        Query dbDb = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("subject").equalTo("database");
        dbDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot db : snapshot.getChildren()){
                        FetchMatchInformation(db.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDbDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDbDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists() && !snapshot.child("connections").child("rejected").hasChild(currentUserId)){
                    String userId = snapshot.getKey();
                    String name = "";
                    String hourlyRate = "";
                    String profileImageUrl = "";
                    String rating = "";
                    if (snapshot.child("name").getValue() != null){
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("hourlyRate").getValue() != null){
                        hourlyRate = snapshot.child("hourlyRate").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    float ratingSum = 0;
                    float ratingsTotal = 0;
                    float ratingsAvg = 0;
                    for (DataSnapshot child : snapshot.child("rating").getChildren()){
                        ratingSum = ratingSum + Integer.valueOf(child.getValue().toString());
                        ratingsTotal++;
                    }

                    if (snapshot.child("rating").getValue() != null){
                        rating = String.valueOf(ratingSum/ratingsTotal);
                    }

                    DbDisplayObject obj = new DbDisplayObject(userId, name, hourlyRate, profileImageUrl, rating); //
                    resultsDb.add(obj);

                    mDbAdapter.notifyDataSetChanged(); // IMP as recyclerView can start again and look for things that change

                    testDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

   private ArrayList<DbDisplayObject> resultsDb = new ArrayList<DbDisplayObject>();
    private List<DbDisplayObject> getDataSetDb() {
        return resultsDb;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_subject:
                Intent h= new Intent(DbDisplayActivity.this, SubjectListActivity.class);
                startActivity(h);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(DbDisplayActivity.this, UserInfoActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(DbDisplayActivity.this, MatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(DbDisplayActivity.this, AboutUs2Activity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Intent l= new Intent(DbDisplayActivity.this, ChooseRoleActivity.class);
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
        menuInflater.inflate(R.menu.teacher_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.high:
                Toast.makeText(this, "Sort by Highest Rated to Lowest", Toast.LENGTH_SHORT).show();
                Collections.reverse(resultsDb);
                mDbAdapter.notifyDataSetChanged();
                break;

            case R.id.low:
                Toast.makeText(this, "Sort by Lowest to Highest", Toast.LENGTH_SHORT).show();
                Collections.sort(resultsDb, new Comparator<DbDisplayObject>() {
                    @Override
                    public int compare(DbDisplayObject o1, DbDisplayObject o2) {
                        return  o1.getRating().compareToIgnoreCase(o2.getRating());
                    }
                });
                mDbAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}