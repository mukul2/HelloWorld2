package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.TestListTypeAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel2;
import com.doctor.telemedicine.model.AppointmentResponse;
import com.doctor.telemedicine.model.RecomentationModel;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.TestList;
import com.doctor.telemedicine.model.TestName;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.testList;

public class VisitActivityDr extends AppCompatActivity implements ApiListener.TestDownloadListener,
        ApiListener.servePostListener {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.ed_commment)
    EditText ed_commment;
    @BindView(R.id.ed_fees)
    EditText ed_fees;
    @BindView(R.id.tv_problems)
    TextView tv_problems;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.spinnerStatus)
    Spinner spinnerStatus;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    public static String APPOINTMENT_ID, DR_ID, P_ID, DR_NAME, PATIENT_NAME, COMMENT, FEES, CHAMBER_ID;
    SessionManager sessionManager;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_dr);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        AppointmentModel2 model = Data.drServingModel;
        APPOINTMENT_ID = model.getId();
        DR_ID = sessionManager.getUserId();
        P_ID = model.getPatientId();
        DR_NAME = model.getDrName();
        PATIENT_NAME = model.getAppointmentFor();
        CHAMBER_ID = model.getChamberId();
        setTitle("" + model.getId());
        tv_name.setText(model.getAppointmentFor());
        tv_problems.setText(model.getProblems());
        tv_time.setText(DataStore.changeDateformate(model.getDate()));
        initSpinner(model.getStatus());
        Api.getInstance().downlaodRecomendedLits("" + model.getId(), this);
    }

    private void initSpinner(String status) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, Data.getAllStatusTypes());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(dataAdapter);
        for (int i = 0; i < Data.getAllStatusTypes().size(); i++) {
            if (status.equals("" + i)) {
                spinnerStatus.setSelection(i);
                break;
            }
        }
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


    @Override
    public void onTestDownloadSuccess(List<TestList> list) {

        List<TestName> listNew = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TestName testName = new TestName();
            testName.setName(list.get(i).getTestName());
            testName.setType(list.get(i).getTestType());
            listNew.add(testName);
        }
//        TestListTypeAdapter mAdapter = new TestListTypeAdapter(listNew);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        //holder.recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
//        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onTestDownloadFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    public void submit(View view) {
        FEES = ed_fees.getText().toString().trim();
        COMMENT = ed_commment.getText().toString().trim();
        MyProgressBar.with(VisitActivityDr.this);
        Api.getInstance().servePost(APPOINTMENT_ID, DR_ID, P_ID, DR_NAME, PATIENT_NAME, COMMENT, FEES, CHAMBER_ID, this);

    }

    @Override
    public void onServePostSuccess(StatusMessage response) {
        MyProgressBar.dismiss();
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
        onBackPressed();

    }

    @Override
    public void onServePostFailed(String msg) {
        MyProgressBar.dismiss();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public void openHistoryActicivity(View view) {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    public void back(View view) {
        onBackPressed();
    }
}
