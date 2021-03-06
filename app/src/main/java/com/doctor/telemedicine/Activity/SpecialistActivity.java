package com.doctor.telemedicine.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.DepartmentsAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DepartmentModel;
import com.doctor.telemedicine.model.DeptModel;
import com.doctor.telemedicine.model.MedicineModel;
import com.sinch.gson.JsonElement;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.TYPE_OF_ACTIVITY;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;

public class SpecialistActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;


    Context context = this;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        //Api.getInstance().getDepartMentsList(TOKEN,this);
        setUpStatusbar();
        Api.getInstance().getDepList(TOKEN, new ApiListener.DeptDownloadListener() {
            @Override
            public void onDepartmentDownloadSuccess(List<DeptModel> list) {
                Toast.makeText(context, ""+list.size(), Toast.LENGTH_SHORT).show();
                        TYPE_OF_ACTIVITY="Chambers";

        DepartmentsAdapter mAdapter = new DepartmentsAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);

            }

            @Override
            public void onDepartmentDownloadFailed(String msg) {

            }
        });


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


//
//    @Override
//    public void onAppointmentSearchSuccess(List<DepartmentModel> list) {
//        TYPE_OF_ACTIVITY="Chambers";
//
//        DepartmentsAdapter mAdapter = new DepartmentsAdapter(list);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        recycler_view.setAdapter(mAdapter);
//
//    }
//
//    @Override
//    public void onAppointmentSearchFailed(String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//
//    }
}
