package com.doctor.telemedicine.Fragments;


import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.DrServicesListAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DrOnlineServiceModel;
import com.doctor.telemedicine.model.ServiceNameInfo;
import com.doctor.telemedicine.model.ServiceWithBoolean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.serviceNameList;
import static org.webrtc.ContextUtils.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragmentDr extends Fragment implements ApiListener.DrServicesDownloadListener, ApiListener.AllServiceDownloadListener {
    View view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public static List<ServiceWithBoolean> SERVICES_LIST = new ArrayList<>();
    public static List<ServiceNameInfo> ALL_SERVICES = new ArrayList<>();
    Context context;
    int counter=0;


    public OnlineFragmentDr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_online_fragment_dr, container, false);
        ButterKnife.bind(this, view);
        context = view.getContext();
        Api.getInstance().getAllServices(TOKEN, this);

        return view;
    }

    @Override
    public void onDrServicesDownloadSuccess(List<DrOnlineServiceModel> data) {
        Gson gson=new Gson();
        //Toast.makeText(context, gson.toJson(data), Toast.LENGTH_LONG).show();
        SERVICES_LIST.clear();
        for (int i = 0; i < ALL_SERVICES.size(); i++) {
            SERVICES_LIST.add(new ServiceWithBoolean(false,"00.0", ALL_SERVICES.get(i)));

        }

     for (int i = 0; i < SERVICES_LIST.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (SERVICES_LIST.get(i).getServiceName().getId()==data.get(j).getOnlineServiceId()&&data.get(j).getStatus()==1) {
                    SERVICES_LIST.get(i).setSelected(true);
                    SERVICES_LIST.get(i).setFees(""+data.get(j).getFees_per_unit());


                }
            }
        }
       // Toast.makeText(context, "matched size "+counter, Toast.LENGTH_SHORT).show();

        DrServicesListAdapter mAdapter = new DrServicesListAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new_ DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDrServicesDownloadFailed(String msg) {

    }

    @Override
    public void onAllServiceDownloadSuccess(List<ServiceNameInfo> data) {
        ALL_SERVICES=data;
        Api.getInstance().getADoctorsServices(TOKEN, USER_ID, this);

    }

    @Override
    public void onAllServiceDownloadFailed(String msg) {

    }

//     for (int i=0;i<serviceNameList.size();i++){
//        SERVICES_LIST.add(new ServiceWithBoolean(false,serviceNameList.get(i)));
//
//    }
//        for (int i=0;i<SERVICES_LIST.size();i++){
//        for (int j=0;j<list.size();j++){
//            if (SERVICES_LIST.get(i).getServiceName().getId().equals(list.get(j).getServiceId())){
//                SERVICES_LIST.get(i).setSelected(true);
//                break;
//
//            }
//        }
//    }
//    DrServicesListAdapter mAdapter = new DrServicesListAdapter();
//    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//    //recyclerView.addItemDecoration(new_ DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//
//        recyclerView.setAdapter(mAdapter);

}
