package com.example.stmeet.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.stmeet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RateTeacherActivity extends AppCompatActivity {

    private RatingBar mRate;
    private EditText mComment;
    private Button mSubmit;

    private String mMatchId, mChatId;

    private String currentUserId;

    DatabaseReference mDatabaseUser, mDatabaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_teacher);

        mRate = findViewById(R.id.ratingBar3);
        mComment = findViewById(R.id.comment);
        mSubmit  =findViewById(R.id.rateButton);

        mMatchId = getIntent().getExtras().getString("matchId");
        mChatId = getIntent().getExtras().getString("chatId");

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("connections").child("matches").child(mMatchId).child("ChatId"); //chatId
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        mRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mDatabaseUser.setValue(rating);
                DatabaseReference mTeacherRatingDb = FirebaseDatabase.getInstance().getReference().child("Users").child(mMatchId).child("rating");
                mTeacherRatingDb.child(mChatId).setValue(rating);
            }
        });
    }

    public void submit(View view) {
    }
}