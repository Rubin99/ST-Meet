package com.example.stmeet.login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stmeet.R;
import com.example.stmeet.matches.MatchesActivity;
import com.example.stmeet.matches.TeacherMatchesActivity;
import com.example.stmeet.teacher_info.TeacherInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class TeacherLoginActivity extends AppCompatActivity {

    private EditText mEmailLogin, mPasswordLogin;
    private Button mLogin;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        //States of user (Login logout)
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @org.jetbrains.annotations.NotNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(TeacherLoginActivity.this, TeacherMatchesActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mEmailLogin = findViewById(R.id.emailLoginTeacher);
        mPasswordLogin = findViewById(R.id.passwordLoginTeacher);
        mLogin = findViewById(R.id.loginTeacher);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailLogin.getText().toString();
                final String password = mPasswordLogin.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(TeacherLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(TeacherLoginActivity.this, "Sign in error", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TeacherLoginActivity.this, ChooseLoginRegistrationActivity.class);
                            //intent.putExtra("userRole", userRole);
                            startActivity(intent);
                            return;
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

    public void toRegister(View view) {
        Intent intent = new Intent(TeacherLoginActivity.this, TeacherRegistrationActivity.class);
        startActivity(intent);
        return;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(TeacherLoginActivity.this, ChooseTeacherLoginRegistrationActivity.class);
        b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(b);
        finish();
    }
}