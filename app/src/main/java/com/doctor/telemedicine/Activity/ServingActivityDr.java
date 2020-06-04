package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.model.TrackModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.selectedSearchAppointmentModel;

public class ServingActivityDr extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;


    @BindView(R.id.tv_problem)
    TextView tv_problem;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_status)
    TextView tv_status;

    @BindView(R.id.tv_date)
    TextView tv_date;
    TrackModel data;
    SessionManager sessionManager;
    String KEY;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serving_dr);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        KEY = sessionManager.getToken();
        data = selectedSearchAppointmentModel;
        tv_name.setText(data.getPatientInfo().getName());
        tv_problem.setText(data.getProblems());
        tv_date.setText(data.getDate());
        if (data.getStatus()==0){
            tv_status.setText("Pending");
        }else if (data.getStatus()==1){
            tv_status.setText("Confirmed");
        }
    }
    public void servePost(View view) {
        Intent i=new Intent(this,PrescriptionGivingActivity.class);
        i.putExtra("type","chambePrescription");
        startActivity(i);

    }
    public void back(View view) {
        onBackPressed();
    }
}
