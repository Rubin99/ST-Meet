package com.example.stmeet;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.stmeet.db_display.DbDisplayActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.java_display.JavaDisplayActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.php_display.PhpDisplayActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SubjectListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mJava, mPhp, mDb;

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        mAuth = FirebaseAuth.getInstance();

        mJava = findViewById(R.id.java);
        mPhp = findViewById(R.id.php);
        mDb = findViewById(R.id.db);

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

        mJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubjectListActivity.this, "Java Teachers", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubjectListActivity.this, JavaDisplayActivity.class);
                startActivity(intent);
                return;
            }
        });
        mPhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubjectListActivity.this, "Php Teachers", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubjectListActivity.this, PhpDisplayActivity.class);
                startActivity(intent);
                return;
            }
        });
        mDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubjectListActivity.this, "Database Teachers", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubjectListActivity.this, DbDisplayActivity.class);
                startActivity(intent);
                return;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_subject:
                Intent h= new Intent(SubjectListActivity.this, SubjectListActivity.class);
                startActivity(h);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(SubjectListActivity.this, UserInfoActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(SubjectListActivity.this, MatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(SubjectListActivity.this, AboutUs2Activity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut(); ///-------------------------------------
                Intent l= new Intent(SubjectListActivity.this, ChooseRoleActivity.class);
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