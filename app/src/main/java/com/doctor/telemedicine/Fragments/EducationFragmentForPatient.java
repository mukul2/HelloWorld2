package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.doctor.telemedicine.R;

import butterknife.ButterKnife;


public class EducationFragmentForPatient extends Fragment {
    View v;
    Context context;


    public static EducationFragmentForPatient newInstance() {
        EducationFragmentForPatient fragment = new EducationFragmentForPatient();
        return fragment;
    }

    public EducationFragmentForPatient() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.appointment_fragment, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);



        return v;
    }





}
