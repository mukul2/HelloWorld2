package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.DoctorsFullProfileView;
import com.doctor.telemedicine.Activity.DrConfirmedActivity;
import com.doctor.telemedicine.Activity.DrPendingActivity;
import com.doctor.telemedicine.Activity.DrPrescriptionListActivity;
import com.doctor.telemedicine.Activity.RecheckActivityDr;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.EducationsAdapterDoctor;
import com.doctor.telemedicine.adapter.SkillAdapterDoctor;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Activity.DoctorsFullProfileView.EDUCATION;
import static com.doctor.telemedicine.Activity.DoctorsFullProfileView.SKILLS;


public class PatientDoctorsEducationFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;





    public static PatientDoctorsEducationFragment newInstance() {
        PatientDoctorsEducationFragment fragment = new PatientDoctorsEducationFragment();
        return fragment;
    }

    public PatientDoctorsEducationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.doctors_education_fragment_by_patient, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);
        initRecyclerView();

        //DrConfirmedActivity

        return v;
    }


    private void initRecyclerView() {

        EducationsAdapterDoctor mAdapter = new EducationsAdapterDoctor(DoctorsFullProfileView.EDUCATION);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }




}
