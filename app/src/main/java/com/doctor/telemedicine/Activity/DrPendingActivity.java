package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.AccountPicker;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.PendingAppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.SkillAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentModelNew;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Fragments.HomeFragmentDrTwo.SKILLS;

public class DrPendingActivity extends AppCompatActivity implements ApiListener.appoinetmentsDownloadListener {

    Context context = this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_pending);
        ButterKnife.bind(this);
        setUpStatusbar();
        Api.getInstance().getAppointments(TOKEN, "doctor", USER_ID, "0", this);


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

    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onAppointmentDownloadSuccess(List<AppointmentModelNew> status) {
        if (status != null && status.size() > 0) {
            PendingAppointmentAdapterDoctor mAdapter = new PendingAppointmentAdapterDoctor(status);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
        } else {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAppointmentDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

//    @Override
//    public void onAppointmentDownloadSuccess(List<AppointmentModel> list) {
//        PendingAppointmentAdapterDoctor mAdapter = new PendingAppointmentAdapterDoctor(list);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//
//        recycler_view.setAdapter(mAdapter);
//
//    }
//
//    @Override
//    public void onAppointmentDownloadFailed(String msg) {
//
//    }
}
