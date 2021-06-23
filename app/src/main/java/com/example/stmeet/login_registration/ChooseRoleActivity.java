package com.example.stmeet.login_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stmeet.R;

public class ChooseRoleActivity extends AppCompatActivity {

    private Button mStudent, mTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        mStudent = findViewById(R.id.roleStudent);
        mTeacher = findViewById(R.id.roleTeacher);

        mStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent student = new Intent(ChooseRoleActivity.this, ChooseLoginRegistrationActivity.class);
                startActivity(student);
                finish();
                return;
            }
        });
        mTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teacher = new Intent(ChooseRoleActivity.this, ChooseTeacherLoginRegistrationActivity.class);
                startActivity(teacher);
                finish();
                return;
            }
        });
    }
}