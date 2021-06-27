package com.example.stmeet.login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.stmeet.R;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.TeacherMatchesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TeacherRegistrationActivity extends AppCompatActivity {

    private EditText mEmailRegister, mPasswordRegister, mNameRegister;
    private Button mRegister;
    private RadioGroup mRadioGroup, mRadioGroup2;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        //States of user (Login logout)
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @org.jetbrains.annotations.NotNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(TeacherRegistrationActivity.this, TeacherMatchesActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mEmailRegister = findViewById(R.id.emailRegister);
        mPasswordRegister = findViewById(R.id.passwordRegister);
        mRegister = findViewById(R.id.register);

        mNameRegister = findViewById(R.id.nameRegister);
        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioGroup2 = findViewById(R.id.radioGroup2);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectId = mRadioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = findViewById(selectId);
                if (radioButton.getText() == null){
                    return;
                }

                final String email = mEmailRegister.getText().toString();
                final String password = mPasswordRegister.getText().toString();
                final String name = mNameRegister.getText().toString();

                int subjectId = mRadioGroup2.getCheckedRadioButtonId();
                final RadioButton radioButton2 = findViewById(subjectId);
                if (radioButton2.getText() == null){
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(TeacherRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(TeacherRegistrationActivity.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TeacherRegistrationActivity.this, ChooseLoginRegistrationActivity.class);
                            //intent.putExtra("userRole", userRole);
                            startActivity(intent);
                            return;
                        } else {
                            String userId = mAuth.getCurrentUser().getUid();
                            //Setting default image
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            Map userInformation = new HashMap<>();
                            userInformation.put("name", name);
                            userInformation.put("role", radioButton.getText().toString());
                            userInformation.put("profileImageUrl", "default");
                            userInformation.put("subject", radioButton2.getText().toString().toLowerCase());
                            currentUserDb.updateChildren(userInformation);
                            currentUserDb.updateChildren(userInformation);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    public void toLogin(View view) {
        Intent intent = new Intent(TeacherRegistrationActivity.this, TeacherLoginActivity.class);
        startActivity(intent);
        return;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(TeacherRegistrationActivity.this, ChooseTeacherLoginRegistrationActivity.class);
        b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(b);
        finish();
    }

}