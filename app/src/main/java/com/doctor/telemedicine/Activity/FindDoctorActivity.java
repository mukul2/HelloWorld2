package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.SpacialistAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.BasicInfoModel;
import com.doctor.telemedicine.model.DoctorModel;
import com.doctor.telemedicine.model.SpacialistModel;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.searchResult;
import static com.doctor.telemedicine.Data.Data.spacialist;

public class FindDoctorActivity extends AppCompatActivity implements ApiListener.basicInfoDownloadListener,
        ApiListener.doctorSearchListener {

    RecyclerView recyclerView;
    SpacialistAdapter mAdapter;
    Context context = this;

    Spinner spinner,specialistSpinner;
    String locationCity = "Dhaka";
    List<String> hospitalList = new ArrayList<>();
    List<String> specialist = new ArrayList<>();
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> dataAdapterSpecialist;
    EditText ed_query;
    String selectedHospital = "";
    String selectedSpacialist = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ed_query = (EditText) findViewById(R.id.ed_query);

        mAdapter = new SpacialistAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        init_hospitalsSpinner();
        init_SpecialistSpinner();





        Api.getInstance().downloadBasicInfo(this);




    }

    private void init_hospitalsSpinner() {
        spinner = (Spinner) findViewById(R.id.hospitals);
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hospitalList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedHospital = hospitalList.get(i);

                } else {
                    selectedHospital = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedHospital = "";

            }
        });
    }

    private void init_SpecialistSpinner() {
        specialistSpinner = (Spinner) findViewById(R.id.specialistSpinner);
        dataAdapterSpecialist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, specialist);
        dataAdapterSpecialist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialistSpinner.setAdapter(dataAdapterSpecialist);
        specialistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedSpacialist = specialist.get(i);

                } else {
                    selectedSpacialist = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedSpacialist = "";

            }
        });
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

    private void getLocation() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                // Acquire a reference to the system Location Manager

            }
        });
    }


    @Override
    public void onBasicInfoDownloadSuccess(BasicInfoModel data) {
        specialist.add("All Specialist");

        spacialist.clear();
        hospitalList.add("All Hospitals");
        for (int i = 0; i < data.getSpacialist().size(); i++) {
            spacialist.add(new SpacialistModel(data.getSpacialist().get(i).getName(), false));
           specialist.add(data.getSpacialist().get(i).getName());
        }

        mAdapter.notifyDataSetChanged();
        hospitalList.addAll(data.getHospitalList());
        dataAdapter.notifyDataSetChanged();
        dataAdapterSpecialist.notifyDataSetChanged();

        //specialist adapter


    }

    @Override
    public void onBasicInfoDownloadFailed(String msg) {

    }

    public void searchDoctor(View view) {
        progressDialog.show();
        String searchQuery = "";
       // selectedSpacialist = "";
        //selectedHospital="";
        searchQuery += ed_query.getText().toString().trim() + "\n";
        String name = ed_query.getText().toString().trim();
        searchQuery += selectedHospital + "\n";
//        for (int i = 0; i < spacialist.size(); i++) {
//            if (spacialist.get(i).isSelected()) {
//                selectedSpacialist = spacialist.get(i).getName();
//                break;
//
//            }
//        }
        searchQuery += selectedSpacialist;
        // Toast.makeText(context, searchQuery, Toast.LENGTH_LONG).show();
        Api.getInstance().searchDoctor(name, selectedHospital, selectedSpacialist, "", "", this);


    }

    @Override
    public void onSearchSuccess(List<DoctorModel> list) {
        searchResult.clear();
        progressDialog.dismiss();
        searchResult.addAll(list);
        startActivity(new Intent(this,SearchResultActivity.class));
    }

    @Override
    public void onSuccessFailed(String msg) {
        progressDialog.dismiss();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public void back(View view) {
        onBackPressed();
    }
}
