package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.CHAMBER_TO_BOOK;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_DOC;

public class BookingConfirmActivity extends AppCompatActivity {
    @BindView(R.id.tv_refID)
    TextView tv_refID;
    @BindView(R.id.tv_namePatient)
    TextView tv_namePatient;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_department)
    TextView tv_department;
    @BindView(R.id.tv_fees)
    TextView tv_fees;
    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirm);
        setUpStatusbar();
        ButterKnife.bind(this);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        //CHAMBER_TO_BOOK
        /// NOW_SHOWING_DOC
        if (b != null) {

            String j2 = (String) b.get("refID");
            String name = (String) b.get("name");
            tv_name.setText(name);
            tv_department.setText(NOW_SHOWING_DOC.getDepartmentModel().getName());
            String date = (String) b.get("date");
            tv_date.setText(date);
            tv_refID.setText("Booking ID : " + j2);

        }
        Glide.with(BookingConfirmActivity.this).load(PHOTO_BASE + NOW_SHOWING_DOC.getPhoto()).into(image);
    }

    public void setUpStatusbar() {
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

    public void gotoHome(View view) {
        startActivity(new Intent(this, PatientHomeActivity.class));
        finishAffinity();
    }
}
