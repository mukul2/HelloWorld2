package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.ConfirmedAppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.TreatmentHistoryAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.TreatmentHistoryModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements ApiListener.patientTreatmentHistoryListener{
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        Api.getInstance().patientTreatmentHistory(VisitActivityDr.P_ID,this);
    }

    @Override
    public void onpatientTreatmentHistorySearchSuccess(List<TreatmentHistoryModel> data) {
        TreatmentHistoryAdapterDoctor  mAdapter = new TreatmentHistoryAdapterDoctor(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onpatientTreatmentHistorySuccessFailed(String msg) {

    }

    public void back(View view) {
        onBackPressed();
    }
}
