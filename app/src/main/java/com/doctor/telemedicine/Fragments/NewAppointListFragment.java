package com.doctor.telemedicine.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.lis;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.AppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.PendingAppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.SearchResultAdapter;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewAppointListFragment extends Fragment implements ApiListener.dataDownloadListener{
    View v;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    PendingAppointmentAdapterDoctor mAdapter;
    @BindView(R.id.tv_no_item)
    TextView tv_no_item;
    public NewAppointListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lis. setPendinglistener(this);
        v = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        ButterKnife.bind(this,v);




        return v;
    }
    @Override
    public void onDownloaded(List<AppointmentModel> status) {
//        mAdapter = new PendingAppointmentAdapterDoctor(status);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//
//        recycler_view.setAdapter(mAdapter);
//        if (status.size()>0){
//            tv_no_item.setVisibility(View.GONE);
//        }else {
//            tv_no_item.setVisibility(View.VISIBLE);
//
//        }

    }

}
