/*
package com.example.stmeet.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.example.stmeet.R;

public class PaymentEsewaActivity extends AppCompatActivity {

    Button mBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_esewa);

        ESewaConfiguration eSewaConfiguration = new ESewaConfiguration()
                .clientId("JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R")
                .secretKey("BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==")
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);

        mBuy = findViewById(R.id.buy);

        mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ESewaPayment eSewaPayment = new ESewaPayment("1000",
        "Teacher Fee", "1","<call_back_url>");
            }
        });
    }
}*/
