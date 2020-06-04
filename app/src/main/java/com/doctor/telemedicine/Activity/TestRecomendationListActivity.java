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

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.RecomendedTestAppointmentAdapterPatient;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.TestRecomendationModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static java.security.AccessController.getContext;

public class TestRecomendationListActivity extends AppCompatActivity implements ApiListener.TestRecomDownloadListener{
    Context context=this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recomendation_list);
        ButterKnife.bind(this);
        setUpStatusbar();
        Api.getInstance().getMyTestRecomendations(TOKEN,USER_ID,this);
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

    @Override
    public void onTestRecomDownloadSuccess(List<TestRecomendationModel> data) {
        RecomendedTestAppointmentAdapterPatient        mAdapter = new RecomendedTestAppointmentAdapterPatient(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onTestRecomDownloadFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
