package com.example.stmeet.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.stmeet.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView mTextId, mTextAmount, mTextStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        mTextId = findViewById(R.id.textId);
        mTextAmount = findViewById(R.id.textAmount);
        mTextStatus = findViewById(R.id.textStatus);

        //Get intent
        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            mTextId.setText(response.getString("id"));
            mTextStatus.setText(response.getString("state"));
            mTextAmount.setText("Rs." + paymentAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}