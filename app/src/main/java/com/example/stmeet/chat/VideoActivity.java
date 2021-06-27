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
import android.widget.EditText;

import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.SubjectListActivity;
import com.example.stmeet.info.UserInfoActivity;
import com.example.stmeet.login_registration.ChooseLoginRegistrationActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;


public class VideoActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    .build();
            //JitsiMeet.setDefaultConferenceOptions(options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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

    public void videoCall(View view) {
        EditText editText = findViewById(R.id.videoId);
        String text = editText.getText().toString();
        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .build();
            JitsiMeetActivity.launch(this, options);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_subject:
                Intent h= new Intent(VideoActivity.this, SubjectListActivity.class);
                startActivity(h);
                break;
            case R.id.nav_teacher:
                Intent t = new Intent(VideoActivity.this, MainActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent p= new Intent(VideoActivity.this, UserInfoActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(VideoActivity.this, MatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(VideoActivity.this, AboutUsActivity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                //mAuth.signOut();
                Intent l= new Intent(VideoActivity.this, ChooseLoginRegistrationActivity.class);
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