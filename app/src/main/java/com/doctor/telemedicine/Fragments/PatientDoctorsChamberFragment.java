package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.ChambersListAdapterDr;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Activity.DoctorsFullProfileView.CHAMBERLIST;


public class PatientDoctorsChamberFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;





    public static PatientDoctorsChamberFragment newInstance() {
        PatientDoctorsChamberFragment fragment = new PatientDoctorsChamberFragment();
        return fragment;
    }

    public PatientDoctorsChamberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.doctors_chamber_fragment_by_patient, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);

        //DrConfirmedActivity
        initRecycler();

        return v;
    }

    private void initRecycler() {
        ChambersListAdapterDr mAdapter=new  ChambersListAdapterDr(CHAMBERLIST);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }


}
