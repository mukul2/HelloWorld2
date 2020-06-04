package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.ConfirmedAppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.ConfirmedAppointmentAdapterPatientNew;
import com.doctor.telemedicine.adapter.PendingAppointmentAdapterPatientNew;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentResponse;
import com.doctor.telemedicine.model.RecomentationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PatientNewHome extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;

    SessionManager sessionManager;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_new_home);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        tv_name.setText(sessionManager.getUserName());
        USER_ID=sessionManager.getUserId();

    }



    private void setUpPendingRecycler(List<AppointmentModel> notConfirmed) {
//        PendingAppointmentAdapterPatientNew mAdapter = new PendingAppointmentAdapterPatientNew(notConfirmed);
//        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        pendingRecycler.setLayoutManager(layoutManager);
//        pendingRecycler.setItemAnimator(new DefaultItemAnimator());
//        pendingRecycler.setAdapter(mAdapter);
    }

    private void setupUpcommingRecycler(List<AppointmentModel> confirmed) {
//        ConfirmedAppointmentAdapterPatientNew mAdapter = new ConfirmedAppointmentAdapterPatientNew(confirmed);
//        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        upcomingRecycler.setLayoutManager(layoutManager);
//        upcomingRecycler.setItemAnimator(new DefaultItemAnimator());
//        upcomingRecycler.setAdapter(mAdapter);
    }



    public void openFindDoctor(View view) {
        startActivity(new Intent(context,FindDoctorActivity.class));
    }

    public void appointments(View view) {
        startActivity(new Intent(context,AppointmentsActivityPatient.class));

    }

    public void openVideoCall(View view) {
        startActivity(new Intent(context,VideoCallPatientActivity.class));

    }

    public void logout(View view) {
        sessionManager.setLoggedIn(false);
        startActivity(new Intent(this,LoginActivity.class));
        finishAffinity();
    }

    public void openExplore(View view) {
        startActivity(new Intent(this,DepartmentsActivity.class));

    }
}
