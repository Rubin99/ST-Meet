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
import com.example.stmeet.matches.TeacherMatchesActivity;
import com.example.stmeet.teacher_info.TeacherInfoActivity;
import com.example.stmeet.matches.MatchesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailLogin, mPasswordLogin;
    private Button mLogin;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //States of user (Login logout)
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @org.jetbrains.annotations.NotNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){

                    /*String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference uidRef = rootRef.child("Users").child(uid);

                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.child("role").getValue().toString() == "Student"){
                                Intent intent = new Intent(LoginActivity.this, MatchesActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            } else if (snapshot.child("role").getValue().toString() == "Teacher"){
                                Intent intent = new Intent(LoginActivity.this, TeacherMatchesActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    };*/
                    /*String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                               String role = dataSnapshot.child("role").getValue().toString();
                               if (role.equals("Student")){
                                   Intent intent = new Intent(LoginActivity.this, MatchesActivity.class);
                                   startActivity(intent);
                                   finish();
                                   return;
                               }
                               if (role.equals("Teacher")){

                               }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });*/


                    Intent intent = new Intent(LoginActivity.this, MatchesActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mEmailLogin = findViewById(R.id.emailLogin);
        mPasswordLogin = findViewById(R.id.passwordLogin);
        mLogin = findViewById(R.id.login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailLogin.getText().toString();
                final String password = mPasswordLogin.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Sign in error", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ChooseLoginRegistrationActivity.class);
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
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
        return;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent b = new Intent(LoginActivity.this, ChooseLoginRegistrationActivity.class);
        b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(b);
        finish();
    }
}