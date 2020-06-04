package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.ScheduleAdapter;
import com.doctor.telemedicine.model.DoctorModel;

import static com.doctor.telemedicine.Data.Data.days;
import static com.doctor.telemedicine.Data.Data.singleDrModel;

public class ChamberDetailActivity extends AppCompatActivity {
    ScheduleAdapter mAdapter;
    RecyclerView recycler_view;
    Context context = this;
    public TextView title, tv_hospitalName,  tv_lastDegree, tv_epacialist, tv_address;
    DoctorModel doctorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamber_detail);
        tv_hospitalName = (TextView) findViewById(R.id.tv_hospitalName);
        tv_lastDegree = (TextView) findViewById(R.id.tv_lastDegree);
        tv_epacialist = (TextView) findViewById(R.id.tv_epacialist);
        tv_address = (TextView) findViewById(R.id.tv_address);
        doctorModel=singleDrModel;
        setTitle(doctorModel.getDrName());
        tv_address.setText(doctorModel.getAddress());
        tv_epacialist.setText(doctorModel.getSpecialist());
        tv_hospitalName.setText(doctorModel.getHospitalName());
        tv_address.setText(doctorModel.getAddress());

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ScheduleAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recycler_view.setAdapter(mAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               onBackPressed();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    public void openBookingActivity(View view) {
        startActivity(new Intent(this,SendBookingActivity.class));
    }

    public void back(View view) {
        onBackPressed();
    }
}
