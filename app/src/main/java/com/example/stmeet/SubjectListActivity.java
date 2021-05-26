package com.example.stmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stmeet.matches.MatchesActivity;

public class SubjectListActivity extends AppCompatActivity {

    private TextView mJava, mPhp, mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        mJava = findViewById(R.id.java);
        mPhp = findViewById(R.id.php);
        mDb = findViewById(R.id.db);

        mJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubjectListActivity.this, "Java Teachers", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubjectListActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
        mPhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubjectListActivity.this, "Php Teachers", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubjectListActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
        mDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubjectListActivity.this, "Database Teachers", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubjectListActivity.this, MainActivity.class);
                startActivity(intent);
                return;
            }
        });
    }
}