package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.doctor.telemedicine.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_AMBULANCE;

public class AmbulanceDetailActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_district)
    TextView tv_district;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_detail);
        ButterKnife.bind(this);
        setUpStatusbar();
        title.setText(NOW_SHOWING_AMBULANCE.getTitle());
        tv_district.setText(NOW_SHOWING_AMBULANCE.getDistrictInfo().getName());
        tv_phone.setText(NOW_SHOWING_AMBULANCE.getPhone());
        tv_area.setText(NOW_SHOWING_AMBULANCE.getArea());
        tv_address.setText(NOW_SHOWING_AMBULANCE.getAddress());
    }

    private void setUpStatusbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {


            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void call(View view) {
        if (NOW_SHOWING_AMBULANCE.getPhone().length()>0){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+NOW_SHOWING_AMBULANCE.getPhone()));
            startActivity(intent);
        }

    }
}
