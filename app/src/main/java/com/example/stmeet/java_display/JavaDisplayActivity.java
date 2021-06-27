package com.example.stmeet.java_display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.SubjectListActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.MatchesObject;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class JavaDisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mJavaRecyclerView;
    private RecyclerView.Adapter mJavaAdapter;
    private RecyclerView.LayoutManager mJavaLayoutManager;

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
        setContentView(R.layout.activity_java_display);

        /*mButtonAsc = findViewById(R.id.sortAsc);
        mButtonDesc = findViewById(R.id.sortDsc);*/

        mAuth = FirebaseAuth.getInstance();

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mJavaRecyclerView = findViewById(R.id.javaRecycler);
        mJavaRecyclerView.setNestedScrollingEnabled(false);
        mJavaRecyclerView.setHasFixedSize(true);
        mJavaLayoutManager = new LinearLayoutManager(JavaDisplayActivity.this);
        mJavaRecyclerView.setLayoutManager(mJavaLayoutManager);
        mJavaAdapter = new JavaDisplayAdapter(getDataSetJava(), JavaDisplayActivity.this);
        mJavaRecyclerView.setAdapter(mJavaAdapter);


        DividerItemDecoration decoration = new DividerItemDecoration(JavaDisplayActivity.this, DividerItemDecoration.VERTICAL);
        mJavaRecyclerView.addItemDecoration(decoration);

        getUserJavaId();

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

        /*mButtonAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(resultsJava, new Comparator<JavaDisplayObject>() {
                    @Override
                    public int compare(JavaDisplayObject o1, JavaDisplayObject o2) {
                        return  o1.getRating().compareToIgnoreCase(o2.getRating());
                    }

                });
                mJavaAdapter.notifyDataSetChanged();
            }
        });
        mButtonDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(resultsJava);
                mJavaAdapter.notifyDataSetChanged();
            }
        });*/
    }


    private void getUserJavaId() {
        Query javaDb = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("subject").equalTo("java");
        javaDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot java : snapshot.getChildren()){
                        FetchMatchInformation(java.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userJavaDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userJavaDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userId = snapshot.getKey();
                    String name = "";
                    String subject = "";
                    String profileImageUrl = "";
                    String rating = "";
                    if (snapshot.child("name").getValue() != null){
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("subject").getValue() != null){
                        subject = snapshot.child("subject").getValue().toString();
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
                   /* if(ratingsTotal != 0){
                        ratingsAvg = ratingSum/ratingsTotal;
                        rating.setRating(ratingsAvg);
                    }*/
                    if (snapshot.child("rating").getValue() != null){
                        rating = String.valueOf(ratingSum/ratingsTotal);
                    }

                    JavaDisplayObject obj = new JavaDisplayObject(userId, name, subject, profileImageUrl, rating); //
                    resultsJava.add(obj);

                    mJavaAdapter.notifyDataSetChanged(); // IMP as recyclerView can start again and look for things that change

                    testDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /* ValueEventListener valueEventListener = new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
             javaList.clear();
             if (snapshot.exists()){
                 for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                     PhpDisplayObject javaDisplayObject = dataSnapshot.getValue(PhpDisplayObject.class);
                     javaList.add(javaDisplayObject);
                 }
                 mJavaAdapter.notifyDataSetChanged();
             }
         }
 
         @Override
         public void onCancelled(@NonNull @NotNull DatabaseError error) {
 
         }
     };
 */
    /* public void sendRequest(View view) {

                PhpDisplayObject object = view;
                String userId = PhpDisplayObject.getUserId();
                //usersDb.child(oppositeUserRole).child(userId).child("connections").child("accepted").child(currentUId).setValue(true);
                usersDb.child(userId).child("connections").child("accepted").child(currentUId).setValue(true);

                Toast.makeText(PhpDisplayActivity.this, "Request sent!", Toast.LENGTH_SHORT).show();
            }*/
   private ArrayList<JavaDisplayObject> resultsJava = new ArrayList<JavaDisplayObject>();
    private List<JavaDisplayObject> getDataSetJava() {
        return  resultsJava;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_subject:
                Intent h= new Intent(JavaDisplayActivity.this, SubjectListActivity.class);
                startActivity(h);
                break;
            case R.id.nav_teacher:
                Intent t = new Intent(JavaDisplayActivity.this, MainActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(JavaDisplayActivity.this, UserInfoActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(JavaDisplayActivity.this, MatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_request:
                Intent r = new Intent(JavaDisplayActivity.this, StudentRequestActivity.class);
                startActivity(r);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(JavaDisplayActivity.this, AboutUsActivity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Intent l= new Intent(JavaDisplayActivity.this, ChooseLoginRegistrationActivity.class);
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
            case R.id.sort:
                Toast.makeText(this, "Sort by Highest Rated", Toast.LENGTH_SHORT).show();
                Collections.reverse(resultsJava);
                mJavaAdapter.notifyDataSetChanged();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}