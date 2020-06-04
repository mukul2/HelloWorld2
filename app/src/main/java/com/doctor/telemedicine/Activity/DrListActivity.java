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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.common.AccountPicker;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.DoctorsSearchAdapter;
import com.doctor.telemedicine.adapter.DrListAdapter;
import com.doctor.telemedicine.adapter.HospitalsAdapter;
import com.doctor.telemedicine.adapter.SearchResultAdapter;
import com.doctor.telemedicine.adapter.TreatmentHistoryAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DoctorModel;
import com.doctor.telemedicine.model.SearchDoctorModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.TYPE_OF_ACTIVITY;
import static com.doctor.telemedicine.Data.DataStore.CLICKED_TITLE;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.downloadedDoctors;

public class DrListActivity extends AppCompatActivity implements ApiListener.DocSearchListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_list);
        ButterKnife.bind(this);
        setUpStatusbar();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
           int depID = bundle.getInt("depID");
            Toast.makeText(context, ""+depID, Toast.LENGTH_SHORT).show();
            Api.getInstance().searchDoctors(TOKEN,null,""+depID,this );
        }

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
    private void initRecyclerView() {
//        SearchResultAdapter mAdapter = new SearchResultAdapter();
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        recycler_view.setAdapter(mAdapter);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void book(View view) {
        startActivity(new Intent(this,BookingActivityNew.class));
    }

    public void viewProfile(View view) {
        startActivity(new Intent(this,SwipeNewActivity.class));
    }


    @Override
    public void onDocSearchSuccess(List<SearchDoctorModel> list) {
        TYPE_OF_ACTIVITY="ChamberDoc";
        DoctorsSearchAdapter mAdapter = new DoctorsSearchAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
       // recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
        tv_title.setText("Doctors ("+list.size()+")");
        if (list.size()==0){
            tv_title.setText("Sorry No Doctors found");
        }
    }

    @Override
    public void onDocSearchFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
