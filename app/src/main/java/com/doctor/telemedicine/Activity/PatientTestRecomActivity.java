package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.RecomendedTestAppointmentAdapterPatient;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.RecomentationModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PatientTestRecomActivity extends AppCompatActivity implements ApiListener.DrRecomentationDownloadListener{

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_test_recom);
        ButterKnife.bind(this);
        Api.getInstance().downlaodPaRecomendation(USER_ID,this);

    }

    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onRecomendationDownloadSuccess(List<RecomentationModel> list) {
//        RecomendedTestAppointmentAdapterPatient mAdapter = new RecomendedTestAppointmentAdapterPatient(list);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
//        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onRecomendationFailed(String msg) {

    }
}
