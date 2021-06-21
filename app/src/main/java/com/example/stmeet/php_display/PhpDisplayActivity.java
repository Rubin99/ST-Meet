package com.example.stmeet.php_display;

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

import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.SubjectListActivity;
import com.example.stmeet.chat.ChatActivity;
import com.example.stmeet.chat.RateTeacherActivity;
import com.example.stmeet.chat.VideoActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.payment.TeacherPaymentActivity;
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


public class PhpDisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mPhpRecyclerView;
    private RecyclerView.Adapter mPhpAdapter;
    private RecyclerView.LayoutManager mPhpLayoutManager;

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
        setContentView(R.layout.activity_php_display);

        /*mButtonAsc = findViewById(R.id.sortAsc);
        mButtonDesc = findViewById(R.id.sortDsc);*/

        mAuth = FirebaseAuth.getInstance();

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mPhpRecyclerView = findViewById(R.id.phpRecycler);
        mPhpRecyclerView.setNestedScrollingEnabled(false);
        mPhpRecyclerView.setHasFixedSize(true);
        mPhpLayoutManager = new LinearLayoutManager(PhpDisplayActivity.this);
        mPhpRecyclerView.setLayoutManager(mPhpLayoutManager);
        mPhpAdapter = new PhpDisplayAdapter(getDataSetPhp(), PhpDisplayActivity.this);
        mPhpRecyclerView.setAdapter(mPhpAdapter);


        DividerItemDecoration decoration = new DividerItemDecoration(PhpDisplayActivity.this, DividerItemDecoration.VERTICAL);
        mPhpRecyclerView.addItemDecoration(decoration);

        getUserPhpId();

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
                Collections.sort(resultsPhp, new Comparator<PhpDisplayObject>() {
                    @Override
                    public int compare(PhpDisplayObject o1, PhpDisplayObject o2) {
                        return  o1.getRating().compareToIgnoreCase(o2.getRating());
                    }

                });
                mPhpAdapter.notifyDataSetChanged();
            }
        });
        mButtonDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(resultsPhp);
                mPhpAdapter.notifyDataSetChanged();
            }
        });*/
    }


    private void getUserPhpId() {
        Query javaDb = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("subject").equalTo("php");
        javaDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot php : snapshot.getChildren()){
                        FetchMatchInformation(php.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userPhpDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userPhpDb.addListenerForSingleValueEvent(new ValueEventListener() {
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

                    PhpDisplayObject obj = new PhpDisplayObject(userId, name, subject, profileImageUrl, rating); //
                    resultsPhp.add(obj);

                    mPhpAdapter.notifyDataSetChanged(); // IMP as recyclerView can start again and look for things that change

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
   private ArrayList<PhpDisplayObject> resultsPhp = new ArrayList<PhpDisplayObject>();
    private List<PhpDisplayObject> getDataSetPhp() {
        return resultsPhp;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_subject:
                Intent h= new Intent(PhpDisplayActivity.this, SubjectListActivity.class);
                h.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(h);
                break;
            case R.id.nav_teacher:
                Intent t = new Intent(PhpDisplayActivity.this, MainActivity.class);
                t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(PhpDisplayActivity.this, UserInfoActivity.class);
                p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(PhpDisplayActivity.this, MatchesActivity.class);
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(m);
                break;
            case R.id.nav_request:
                Intent r = new Intent(PhpDisplayActivity.this, StudentRequestActivity.class);
                r.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(r);
                break;
            case R.id.nav_java:
                Intent j = new Intent(PhpDisplayActivity.this, PhpDisplayActivity.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Intent l= new Intent(PhpDisplayActivity.this, ChooseLoginRegistrationActivity.class);
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
                Collections.reverse(resultsPhp);
                mPhpAdapter.notifyDataSetChanged();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}