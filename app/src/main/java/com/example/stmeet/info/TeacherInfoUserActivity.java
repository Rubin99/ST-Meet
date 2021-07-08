package com.example.stmeet.info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stmeet.AboutUsActivity;
import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
import com.example.stmeet.login_registration.ChooseRoleActivity;
import com.example.stmeet.matches.TeacherMatchesActivity;
import com.example.stmeet.student_requests.StudentRequestActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherInfoUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private EditText mNameField, mPhoneNoField, mEducationField, mSchoolField, mSubjectField, mAboutField, mHourlyRate;
    private Button mConfirm, mBack;
    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private String userId, name, phone, profileImageUrl, userRole, education, school, subject, about, hourlyRate;

    private Uri resultUri;

    // For navigation sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    //----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info_user);

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

        //String userRole = getIntent().getExtras().getString("userRole");

        mNameField = findViewById(R.id.name);
        mPhoneNoField = findViewById(R.id.phoneNo);
        mEducationField = findViewById(R.id.education);
        mSchoolField = findViewById(R.id.school);
        mSubjectField = findViewById(R.id.subject);
        mAboutField = findViewById(R.id.about);
        //mConfirm = findViewById(R.id.confirm);
        mProfileImage = findViewById(R.id.profileImage);
        mHourlyRate = findViewById(R.id.userHourlyRate);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid(); // takes the current users id

        //error re, double check
        //mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userRole).child(userId);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId); // getting inside the userid

        getUserInfo();

        //getting image from phone
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1); //1 always attached to the intent, sort of like a unique identifier for this intent
            }
        });

        /*mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });*/

       /* mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });*/

    }

    //Litsener to check for current user TeacherInfoObject
    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name") != null){
                        name = map.get("name").toString();
                        mNameField.setText(name);
                    }
                    if (map.get("phone") != null){
                        phone = map.get("phone").toString();
                        mPhoneNoField.setText(phone);
                    }
                    if (map.get("role") != null){
                        userRole = map.get("role").toString();
                    }
                    if (map.get("education") != null){
                        education = map.get("education").toString();
                        mEducationField.setText(education);
                    }
                    if (map.get("school") != null){
                        school = map.get("school").toString();
                        mSchoolField.setText(school);
                    }
                    if (map.get("subject") != null){
                        subject = map.get("subject").toString();
                        mSubjectField.setText(subject);
                    }
                    if (map.get("hourlyRate") != null){
                        hourlyRate = map.get("hourlyRate").toString();
                        mHourlyRate.setText(hourlyRate);
                    }
                    if (map.get("about") != null){
                        about = map.get("about").toString();
                        mAboutField.setText(about);
                    }
                    Glide.with(mProfileImage.getContext()).clear(mProfileImage);
                    if (map.get("profileImageUrl") != null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch(profileImageUrl){
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mProfileImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mProfileImage);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    //For saving the information in the database
    private void saveUserInformation() {
        name = mNameField.getText().toString();
        phone = mPhoneNoField.getText().toString();
        education = mEducationField.getText().toString();
        school = mSchoolField.getText().toString();
        subject = mSubjectField.getText().toString();
        about = mAboutField.getText().toString();
        hourlyRate = mHourlyRate.getText().toString();

        //To save it
        Map userInformation = new HashMap();
        userInformation.put("name", name);
        userInformation.put("phone", phone);
        userInformation.put("education", education);
        userInformation.put("school", school);
        userInformation.put("subject", subject);
        userInformation.put("about", about);
        userInformation.put("hourlyRate", hourlyRate);
        mUserDatabase.updateChildren(userInformation);

        if (resultUri != null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;
            //pass result from resultUri to bitmap
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) { finish(); }});
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Grab the url from the profile image that was saved. Which allows to get image from cloud
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map newImage = new HashMap();
                            newImage.put("profileImageUrl", uri.toString());
                            mUserDatabase.updateChildren(newImage);

                            finish();
                            return;
                        }
                    });/*.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            finish();
                            return;
                        }
                    });*/
                }
            });
        }else {
            Intent p= new Intent(TeacherInfoUserActivity.this, TeacherInfoUserActivity.class);
            startActivity(p);
            //return;
            //finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri; //Uri basically location of image in the phone
            mProfileImage.setImageURI(resultUri);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_profile:
                Intent p= new Intent(TeacherInfoUserActivity.this, TeacherInfoUserActivity.class);
                startActivity(p);
                break;
            case R.id.nav_matches:
                Intent m= new Intent(TeacherInfoUserActivity.this, TeacherMatchesActivity.class);
                startActivity(m);
                break;
            case R.id.nav_request:
                Intent r= new Intent(TeacherInfoUserActivity.this, StudentRequestActivity.class);
                startActivity(r);
                break;
            case R.id.nav_aboutUs:
                Intent a= new Intent(TeacherInfoUserActivity.this, AboutUsActivity.class);
                startActivity(a);
                break;
            case R.id.nav_logout:
                mAuth.signOut(); //!!!!!!!!!!!!!!!!!!!!!!! Need to add mAuth
                Intent l= new Intent(TeacherInfoUserActivity.this, ChooseRoleActivity.class);
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
        menuInflater.inflate(R.menu.user_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                saveUserInformation();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}