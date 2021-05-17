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

import com.example.stmeet.MainActivity;
import com.example.stmeet.R;
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

public class RegistrationActivity extends AppCompatActivity {

    private EditText mEmailRegister, mPasswordRegister, mNameRegister;
    private Button mRegister;
    private RadioGroup mRadioGroup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //States of user (Login logout)
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @org.jetbrains.annotations.NotNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
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
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                        } else {
                            String userId = mAuth.getCurrentUser().getUid();
                            //Setting default image
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            Map userInformation = new HashMap<>();
                            userInformation.put("name", name);
                            userInformation.put("role", radioButton.getText().toString());
                            userInformation.put("profileImageUrl", "default");
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
}