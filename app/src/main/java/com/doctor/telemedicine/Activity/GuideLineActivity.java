package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.doctor.telemedicine.R;

public class GuideLineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_line);
    }

    public void back(View view) {
        onBackPressed();
    }
}
