package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.DepartmentsActivity;
import com.doctor.telemedicine.Data.lis;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.ConfirmedAppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.DepartmentsAdapter;
import com.doctor.telemedicine.adapter.HospitalsAdapter;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DepartmentsListFragment extends Fragment {
    View v;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context;

    public DepartmentsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_fragment, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);
        initRecyclerView();

        return v;
    }
    private void initRecyclerView() {
//        DepartmentsAdapter mAdapter = new DepartmentsAdapter(DepartmentsActivity.SPECIALIST);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        recycler_view.setAdapter(mAdapter);
    }



}
