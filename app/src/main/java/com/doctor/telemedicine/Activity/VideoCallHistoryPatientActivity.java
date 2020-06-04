package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.CallLogHistoryAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.VideoCallHistoryModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class VideoCallHistoryPatientActivity extends BaseActivity implements  ApiListener.VideoCallHistoryDownloadListenerPatient{
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_history_patient);
        ButterKnife.bind(this);
        Api.getInstance().getVideoCallSummery(TOKEN,USER_ID,"patient",this);

    }

    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onVideoCallHistoryDownloadSuccess(List<VideoCallHistoryModel> data) {
        CallLogHistoryAdapter mAdapter = new CallLogHistoryAdapter(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onVideoCallHistoryDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


    }

    public void back(View view) {
        onBackPressed();
    }
}
