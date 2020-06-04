package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.adapter.ChatAdapterSupportTeam;
import com.doctor.telemedicine.adapter.DepartmentsAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.QueryModel;
import com.doctor.telemedicine.model.StatusMessage;
import com.sinch.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.ADMIN_ID;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PublicAskByPatient extends AppCompatActivity implements ApiListener.publicQueryPostListenerPatient, ApiListener.publicQueryDownloadListenerPatient {
    @BindView(R.id.ed_message)
    EditText ed_message;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context = this;

    List<QueryModel> queryModels = new ArrayList<>();

    ChatAdapterSupportTeam mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_ask_by_patient);
        ButterKnife.bind(this);
        //publicQueryPOst
        initRecycler();
        Api.getInstance().getMyAllQuery(TOKEN, USER_ID, ADMIN_ID, this);
    }

    private void initRecycler() {
        mAdapter = new ChatAdapterSupportTeam(context, queryModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void send(View view) {
        String message = ed_message.getText().toString().trim();
        if (message.length() > 0) {
            MyProgressBar.with(context);
            Api.getInstance().publicQueryPOst(TOKEN, message, USER_ID, ADMIN_ID, this);

        }

    }

    @Override
    public void onPublicQueryPostSuccess(StatusMessage data) {
        MyProgressBar.dismiss();
        ed_message.setText("");
        // Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();

        Api.getInstance().getMyAllQuery(TOKEN, USER_ID, ADMIN_ID, this);

    }

    @Override
    public void onPublicQueryPostFailed(String msg) {
        MyProgressBar.dismiss();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPublicQueryDownloadSuccess(List<QueryModel> data) {
        queryModels.clear();
        queryModels.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (queryModels.size() > 0) {
            recycler_view.smoothScrollToPosition(data.size() - 1);
        }

    }

    @Override
    public void onPublicQueryDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
