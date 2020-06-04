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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.common.AccountPicker;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.GeneralListener;
import com.doctor.telemedicine.adapter.PrescriptionAdapterDoctor;
import com.doctor.telemedicine.adapter.ReviewPrecriptionsAdapterDoctor;
import com.doctor.telemedicine.adapter.ReviewPrecriptionsAdapterPatient;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.PrescriptionModel;
import com.doctor.telemedicine.model.PrescriptionReviewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.PRESCRIPTION_VIEW_TYPE;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PatientPrescriptionRecheckActivity extends AppCompatActivity implements ApiListener.ReviewRequestDownloadListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recheck_dr);
        ButterKnife.bind(this);
        setUpStatusbar();
        Api.getInstance().getMyReviews(TOKEN, USER_ID, "patient", this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Api.getInstance().getMyReviews(TOKEN, USER_ID, "patient", this);

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

    public void openRecheckBody(View view) {

    }

    @Override
    public void onReviewRequestDownloadSuccess(List<PrescriptionReviewModel> response) {
        PRESCRIPTION_VIEW_TYPE = "review";
        ReviewPrecriptionsAdapterPatient mAdapter = new ReviewPrecriptionsAdapterPatient(response);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(_sGridLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
        GeneralListener.setNeedRefresh(new GeneralListener.youNeedRefresh() {
            @Override
            public void doRefresh(int value) {

            }
        });
    }

    @Override
    public void onReviewRequestDownloadFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
