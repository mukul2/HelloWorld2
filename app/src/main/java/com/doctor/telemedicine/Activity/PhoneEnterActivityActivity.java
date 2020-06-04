package com.doctor.telemedicine.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.doctor.telemedicine.R;

public class PhoneEnterActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_enter_activity);
    }

    public void openVarificationCodeActivity(View view) {
        startActivity(new Intent(this,CodeVerificationActivity.class));
    }
}
