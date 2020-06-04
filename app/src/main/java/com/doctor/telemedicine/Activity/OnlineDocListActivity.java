package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.DoctorsSearchAdapter;
import com.doctor.telemedicine.adapter.OnlineDoctorsSearchAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.OnlineDoctorsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.TYPE_OF_ACTIVITY;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;

public class OnlineDocListActivity extends BaseActivity implements ApiListener.DownloadOnlineDocListener {
    Context context = this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_doc_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String depID = bundle.getString("depID");
            Api.getInstance().getOnLineDoctors(TOKEN, depID, this);
        }
    }

    @Override
    public void onOnlineDocSearchSuccess(List<OnlineDoctorsModel> status) {
        TYPE_OF_ACTIVITY = "OnlineDoc";
        OnlineDoctorsSearchAdapter mAdapter = new OnlineDoctorsSearchAdapter(status);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        // recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
        tv_title.setText("Doctors (" + status.size() + ")");
        if (status.size() == 0) {
            tv_title.setText("Sorry No Doctors found");
        }


    }

    @Override
    public void onOnlineDocSearchFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public void back(View view) {
        onBackPressed();
    }
}
