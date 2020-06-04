package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.doctor.telemedicine.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_NOTICE;

public class NotificationDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_body)
    TextView tv_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        ButterKnife.bind(this);
        //NOW_SHOWING_NOTICE
        setUpStatusbar();
        tv_body.setText(NOW_SHOWING_NOTICE.getMessage());
    }

    public void back(View view) {
        onBackPressed();
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
}
