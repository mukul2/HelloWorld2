package com.doctor.telemedicine.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.doctor.telemedicine.Activity.AddNewChamberActivity;
import com.doctor.telemedicine.Activity.PatientHomeActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.ChamberDaysListAdapter;
import com.doctor.telemedicine.adapter.ChambersListAdapterDr;
import com.doctor.telemedicine.adapter.DepartmentsAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DaysTimeModel;
import com.doctor.telemedicine.model.DrChamberResponse;
import com.doctor.telemedicine.widgets.DividerItemDecoration;
import com.doctor.telemedicine.widgets.MyDialogList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Fragments.HomeFragmentDrTwo.CHAMBERLIST;


public class ChamberFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.upload)
    ExtendedFloatingActionButton upload;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    List<DaysTimeModel> list = new ArrayList<>();
    List<DaysTimeModel> listReserved = new ArrayList<>();
    List<String> days = new ArrayList<>();
    List<String> timeRange = new ArrayList<>();


    public static ChamberFragment newInstance() {
        ChamberFragment fragment = new ChamberFragment();
        return fragment;
    }

    public ChamberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.chamber_fragment, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);

        //dayAddDialog
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, AddNewChamberActivity.class));
            }
        });
       //CHAMBERLIST
        ChambersListAdapterDr mAdapter=new  ChambersListAdapterDr(CHAMBERLIST);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);

        return v;
    }


}
