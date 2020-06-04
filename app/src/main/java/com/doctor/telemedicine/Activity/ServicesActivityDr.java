package com.doctor.telemedicine.Activity;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.DrServicesListAdapter;
import com.doctor.telemedicine.adapter.SearchResultAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DrServiceModel;
import com.doctor.telemedicine.model.ServiceIdPrice;
import com.doctor.telemedicine.model.ServiceName;
import com.doctor.telemedicine.model.ServiceWithBoolean;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.serviceNameList;

public class ServicesActivityDr extends AppCompatActivity implements ApiListener.DrServiceDownloadListener,ApiListener.drServicePostListener{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
  //  public static List<ServiceWithBoolean> SERVICES_LIST=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_dr);
        ButterKnife.bind(this);
        //SERVICES_LIST.clear();
        Api.getInstance().downloadDrServiceList(USER_ID,this);
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onDrServiceDownloadSuccess(List<DrServiceModel> list) {
//        for (int i=0;i<serviceNameList.size();i++){
//            SERVICES_LIST.add(new ServiceWithBoolean(false,serviceNameList.get(i)));
//
//        }
//        for (int i=0;i<SERVICES_LIST.size();i++){
//            for (int j=0;j<list.size();j++){
//                if (SERVICES_LIST.get(i).getServiceName().getId().equals(list.get(j).getServiceId())){
//                    SERVICES_LIST.get(i).setSelected(true);
//                    break;
//
//                }
//            }
//        }
//        DrServicesListAdapter mAdapter = new DrServicesListAdapter();
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        //recyclerView.addItemDecoration(new_ DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//
//        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onDrServiceDownloadFailed(String msg) {

    }

    public void update(View view) {
//        List<ServiceIdPrice>list=new ArrayList<>();
//        int counter =0;
//        for (int i=0;i<SERVICES_LIST.size();i++){
//            if (SERVICES_LIST.get(i).isSelected()){
//                list.add(new ServiceIdPrice(SERVICES_LIST.get(i).getServiceName().getId(),"500"));
//
//            }
//
//        }
//        Gson gson=new Gson();
//        Toast.makeText(this, gson.toJson(list), Toast.LENGTH_SHORT).show();
//        Api.getInstance().postDrServices(USER_ID,gson.toJson(list),this);
    }

    @Override
    public void ondrServicePostSuccess(StatusMessage response) {
        if (response!=null){
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void ondrServicePostFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
