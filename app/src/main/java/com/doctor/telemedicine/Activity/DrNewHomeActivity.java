package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.SearchAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel2;
import com.doctor.telemedicine.model.BasicByDrResponse;
import com.doctor.telemedicine.model.BasicInfoModel;
import com.doctor.telemedicine.model.SpacialistModel;
import com.doctor.telemedicine.model.TestModel;
import com.doctor.telemedicine.model.testSelectedModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.spacialist;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.serviceNameList;

public class DrNewHomeActivity extends AppCompatActivity implements ApiListener.basicInfoDownloadListener,
        ApiListener.testNamesDownloadListener {
    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.searchDr_recycler)
    RecyclerView searchDr_recycler;
    SessionManager sessionManager;
    Context context = this;
    SearchAdapterDoctor mAdapter;
    int count = 0;
    List<AppointmentModel2> datalist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_new_home);
        sessionManager = new SessionManager(this);
        ButterKnife.bind(this);
        USER_ID = sessionManager.getUserId();
        initAdapter();
        init_search();
        Api.getInstance().downloadBasicInfo(this);
        Api.getInstance().downloadTestNames(this);
    }

    private void initAdapter() {
        mAdapter = new SearchAdapterDoctor(datalist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        searchDr_recycler.setLayoutManager(mLayoutManager);
        searchDr_recycler.setItemAnimator(new DefaultItemAnimator());
        //searchDr_recycler.addItemDecoration(new_ DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        searchDr_recycler.setAdapter(mAdapter);
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    private void init_search() {
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String id = "";
                String key = ed_search.getText().toString().trim();
                String patient_name = "";
                if (isNumeric(key)) {
                    id = key;
                    patient_name = "";

                } else {
                    id = "";
                    patient_name = key;

                }

                if ((id.trim().length() + patient_name.trim().length()) > 0) {
                    searchDr_recycler.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.VISIBLE);


                    //  Toast.makeText(context, ""+(id.trim().length()+patient_name.trim().length()), Toast.LENGTH_SHORT).show();
                    Api.getInstance().searchAppointment(id, USER_ID, patient_name, new ApiListener.appointmentSearchListener() {
                        @Override
                        public void onAppointmentSearchSuccess(List<AppointmentModel2> data) {
                            datalist.clear();
                            if (data!=null && data.size()>0){
                                datalist.addAll(data);
                                mAdapter.notifyDataSetChanged();
                            }else {
                                AppointmentModel2 model2=new AppointmentModel2();
                                model2.setId("0");
                                datalist.add(model2);
                                mAdapter.notifyDataSetChanged();


                            }


                        }

                        @Override
                        public void onAppointmentSearchFailed(String msg) {
                            Toast.makeText(DrNewHomeActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    searchDr_recycler.setVisibility(View.GONE);
                    divider.setVisibility(View.GONE);

                    // Toast.makeText(context, "clr list", Toast.LENGTH_SHORT).show();
                    if (mAdapter == null) {

                    } else {
                        mAdapter.clearAdapter();

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBasicInfoDownloadSuccess(BasicInfoModel data) {
        count++;


        spacialist.clear();
        for (int i = 0; i < data.getSpacialist().size(); i++) {
          //  spacialist.add(new_ SpacialistModel(data.getSpacialist().get(i), false));
        }


    }

    @Override
    public void ontestNamesDownloadSuccess(BasicByDrResponse data) {
        serviceNameList.clear();
        DataStore.testModelList.clear();
        //   Toast.makeText(this, ""+data.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < data.getTestNames().size(); i++) {
           // DataStore.testModelList.add(new testSelectedModel(false, data.getTestNames().get(i)));
        }
        serviceNameList=data.getServiceNames();
    }

    @Override
    public void ontestNamesDownloadFailed(String msg) {

    }

    @Override
    public void onBasicInfoDownloadFailed(String msg) {
        count++;


    }

    public void appointmentsDr(View view) {
        startActivity(new Intent(this, DrAllAppointmentsActivity.class));

    }

    public void editInfo(View view) {
        startActivity(new Intent(this, DrPersonalInfoActivity.class));

    }

    public void chamber(View view) {
        startActivity(new Intent(this, DrChamberListActivity.class));

    }

    public void openVideoCallDr(View view) {
        startActivity(new Intent(this, VideoCallActivityDr.class));

    }

    public void logout(View view) {
        sessionManager.setLoggedIn(false);
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    public void openMyServices(View view) {
        startActivity(new Intent(this, ServicesActivityDr.class));

    }
}
