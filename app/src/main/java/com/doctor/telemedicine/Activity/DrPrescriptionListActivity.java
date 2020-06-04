package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.common.AccountPicker;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.DoctorsSearchAdapter;
import com.doctor.telemedicine.adapter.PrescriptionRequestAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.PrescriptionRequestModel;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class DrPrescriptionListActivity extends AppCompatActivity implements ApiListener.MyPrescriptionRequestDownloadListener{
    Context context=this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_prescription_list);
        ButterKnife.bind(this);
        setUpStatusbar();
        Toast.makeText(context, "IM here", Toast.LENGTH_SHORT).show();
        Api.getInstance().getMyPrescriptionRequest(TOKEN,USER_ID,"doctor",this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Api.getInstance().getMyPrescriptionRequest(TOKEN,USER_ID,"doctor",this);

    }

    public  void setUpStatusbar(){
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
    public void openPrescriptionBody(View view){
        startActivity(new Intent(context, PrescriptionGivingActivity.class));

    }

    @Override
    public void onMyPrescriptionRequestDownloadSuccess(List<PrescriptionRequestModel> data) {
        PrescriptionRequestAdapterDoctor mAdapter = new PrescriptionRequestAdapterDoctor(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        // recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    public void onMyPrescriptionRequestDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }


}
