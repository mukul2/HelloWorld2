package com.doctor.telemedicine.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentResponse;

import java.util.ArrayList;
import java.util.List;

public class DrAllAppointmentsActivity extends AppCompatActivity {
    SessionManager sessionManager;
    public  static List<AppointmentModel> PENDING=new ArrayList<>();
    public  static List<AppointmentModel> CONFIRMED=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_all_appointments);
        sessionManager=new SessionManager(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
       // progressDialog.show();
        //Api.getInstance().getAppointmentsByDoctor(sessionManager.getUserId(), this);

    }

    public void openApprovedDr(View view) {
        startActivity(new Intent(this,DrConfirmedActivity.class));

    }

    public void openPendingDr(View view) {
        startActivity(new Intent(this,DrPendingActivity.class));
    }

    public void back(View view) {
        onBackPressed();
    }


}
