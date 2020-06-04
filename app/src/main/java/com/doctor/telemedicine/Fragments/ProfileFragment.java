package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.doctor.telemedicine.Activity.AdviceActivityPatient;
import com.doctor.telemedicine.Activity.BillsActivity;
import com.doctor.telemedicine.Activity.NotificationActivity;
import com.doctor.telemedicine.Activity.PatientDiseaseSumActivity;
import com.doctor.telemedicine.Activity.PatientPersonalInfoActivity;
import com.doctor.telemedicine.Activity.PatientPrescriptionRecheckActivity;
import com.doctor.telemedicine.Activity.PrescriptionActivityPatient;
import com.doctor.telemedicine.Activity.TestRecomendationListActivity;
import com.doctor.telemedicine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.linearPersonal)
    LinearLayout linearPersonal;
    @BindView(R.id.linearTest)
    LinearLayout linearTest;
    @BindView(R.id.linearDisease)
    LinearLayout linearDisease;

    @BindView(R.id.linearPrescription)
    LinearLayout linearPrescription;
    @BindView(R.id.linearPrescriptionReview)
    LinearLayout linearPrescriptionReview;
    @BindView(R.id.linearNotification)
    LinearLayout linearNotification;
    @BindView(R.id.linearBill)
    LinearLayout linearBill;



    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.profile_fragment, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);
        linearPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PatientPersonalInfoActivity.class));
            }
        });

        linearTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, TestRecomendationListActivity.class));
            }
        });
        linearNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, NotificationActivity.class));
            }
        });
        linearDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PatientDiseaseSumActivity.class));
            }
        });

        linearPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PrescriptionActivityPatient.class));
            }
        });
        linearPrescriptionReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PatientPrescriptionRecheckActivity.class));
            }
        });



        return v;
    }
    @OnClick(R.id.linearBill)
    public  void BillActivity(){
        //BillsActivity
        startActivity(new Intent(context, BillsActivity.class));

    }

    //





}
