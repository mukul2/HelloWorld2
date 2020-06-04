package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.TestListTypeAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentResponse;
import com.doctor.telemedicine.model.StatusResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.testList;
import static com.doctor.telemedicine.Data.ListenerPatientsData.PatientALLDataDownloadListener;
import static com.doctor.telemedicine.Data.ListenerPatientsData.PatientNotificationDataDownloadListener;

public class RecomendationDetailActivity extends BaseActivity  {
    TestListTypeAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_problem)
    TextView tv_problem;
    @BindView(R.id.tv_image)
    ImageView image;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_department)
    TextView tv_department;

    Context context = this;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendation_detail);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);

        mAdapter = new TestListTypeAdapter(testList.getTestRecommendationInfo());
        tv_problem.setText(testList.getProblems());
        tv_name.setText(testList.getDr_info().getName());
      //  tv_department.setText(testList.getDr_info().getDepartment_info().getName());
        Glide.with(context).load(PHOTO_BASE+testList.getDr_info().getPhoto()).into(image);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //holder.recycler_view.addItemDecoration(new_ DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recycler_view.setAdapter(mAdapter);

        Resources res = context.getResources();
      //  String text = res.getString(R.string.text);
       // tv_name.setText(testList.getDrName() + " " + text);
      //  tv_serial.setText("Serial No : "+testList.getAppointmentId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    public void cancel(View view) {
        MyProgressBar.with(context);
       // Api.getInstance().changeStatus(testList.getAppointmentId(), "" + Data.STATUS_CANCEL, this);
    }







    public void back(View view) {
        onBackPressed();
    }
}
