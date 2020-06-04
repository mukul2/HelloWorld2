package com.doctor.telemedicine.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentResponse;
import com.doctor.telemedicine.model.RecomentationModel;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivityPatient extends AppCompatActivity {
    SessionManager sessionManager;
    Context context = this;
    public static List<AppointmentModel> PENDING_LIST=new ArrayList<>();
    public static List<AppointmentModel> CONFIRMED_LIST=new ArrayList<>();
    public static List<RecomentationModel> RECOMENDED_LIST=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_patient);
        sessionManager=new SessionManager(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
       // progressDialog.show();
        //Api.getInstance().getAppointmentsBypatient(sessionManager.getUserId(), this);

    }

    public void openApproved(View view) {
        startActivity(new Intent(this, PatientConfirmedActivity.class));
    }

    public void openPending(View view) {
        startActivity(new Intent(this, PatientPendingActivity.class));
    }

    public void openTestRecomendtions(View view) {
        startActivity(new Intent(this, PatientTestRecomActivity.class));

    }

    public void back(View view) {
        onBackPressed();
    }


}
