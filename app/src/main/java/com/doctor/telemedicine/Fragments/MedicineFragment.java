package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.doctor.telemedicine.Activity.CallLogHistoryActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.CurrentlyOnlineDoctorAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.VideoCallModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MedicineFragment extends Fragment {
    View v;
    Context context;


    public static MedicineFragment newInstance() {
        MedicineFragment fragment = new MedicineFragment();
        return fragment;
    }

    public MedicineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_medicine, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);



        return v;
    }





}
