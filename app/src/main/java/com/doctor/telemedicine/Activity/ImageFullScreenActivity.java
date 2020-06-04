package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.doctor.telemedicine.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFullScreenActivity extends AppCompatActivity {
    @BindView(R.id.myZoomageView)
    ZoomageView myZoomageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        ButterKnife.bind(this);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            String j = (String) b.get("link");
            Glide.with(ImageFullScreenActivity.this).load(j).into(myZoomageView);

        }
    }
}
