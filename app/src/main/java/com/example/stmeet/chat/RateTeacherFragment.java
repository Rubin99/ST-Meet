package com.example.stmeet.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.stmeet.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateTeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateTeacherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RateTeacherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RateTeacherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RateTeacherFragment newInstance(String param1, String param2) {
        RateTeacherFragment fragment = new RateTeacherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rateView = inflater.inflate(R.layout.fragment_rate_teacher, container, false);

        final RatingBar mRatingBar = rateView.findViewById(R.id.ratingBar2);
        final EditText mCommentEditText = rateView.findViewById(R.id.commentEditText);

        /*mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mDatabaseUser.setValue(rating);
                DatabaseReference mTeacherRatingDb = FirebaseDatabase.getInstance().getReference().child("Users").child(matchId).child("rating");
                mTeacherRatingDb.child(chatId).setValue(rating);
            }
        });*/
        return rateView;
    }
    public static RateTeacherFragment newInstance() { return new RateTeacherFragment(); }

}