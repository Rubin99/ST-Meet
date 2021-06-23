package com.example.stmeet.login_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stmeet.R;

public class ChooseTeacherLoginRegistrationActivity extends AppCompatActivity {

    private Button mTeacherLogin, mTeacherRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teacher_login_registration);

        mTeacherLogin = findViewById(R.id.loginTeacher);
        mTeacherRegister = findViewById(R.id.registerTeacher);

        mTeacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTeacherLoginRegistrationActivity.this, TeacherLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        mTeacherRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTeacherLoginRegistrationActivity.this, TeacherRegistrationActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}