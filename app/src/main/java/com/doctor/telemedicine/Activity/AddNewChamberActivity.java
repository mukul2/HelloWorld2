package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.adapter.ChamberDaysListAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.ChamberDaysPostModel;
import com.doctor.telemedicine.model.DaysTimeModel;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.widgets.DividerItemDecoration;
import com.doctor.telemedicine.widgets.MyDialogList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class AddNewChamberActivity extends BaseActivity implements ApiListener.drSchedulePostListener {
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context = this;
    List<DaysTimeModel> list = new ArrayList<>();
    List<DaysTimeModel> listReserved = new ArrayList<>();
    List<String> days = new ArrayList<>();
    List<String> startTime = new ArrayList<>();
    List<String> endTime = new ArrayList<>();
    @BindView(R.id.ed_address)
    EditText ed_address;
    @BindView(R.id.ed_fees)
    EditText ed_fees;
    @BindView(R.id.ed_followupfees)
    EditText ed_followupfees;
    @BindView(R.id.ed_chamberName)
    EditText ed_chamberName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_chamber);
        ButterKnife.bind(this);
        ChamberDaysListAdapter mAdapter = new ChamberDaysListAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL, false);
        recycler_view.setAdapter(mAdapter);
        recycler_view.addItemDecoration(dividerItemDecoration);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogList.getInstance().with((Activity) context).dayAddDialog(new MyDialogList.DaysSelectedListener() {
                    @Override
                    public void onDaysSelected(DaysTimeModel day) {
                        //Toast.makeText(context, day.getDayName(), Toast.LENGTH_SHORT).show();
                        list.add(list.size(), day);
                        mAdapter.notifyItemInserted(list.size() - 1);

                        days.add(day.getDayName());
                        startTime.add(day.getStartTime());
                        endTime.add(day.getStartTime());
                        //List<Day>list=new ArrayList<>();


                    }

                    @Override
                    public void onDaysSelectCanceled(String canceled) {

                    }
                });
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }

    public void save(View view) {
        String address = ed_address.getText().toString().trim();
        String fees = ed_fees.getText().toString().trim();
        String followup = ed_followupfees.getText().toString().trim();
        String chamberName = ed_chamberName.getText().toString().trim();
        List<ChamberDaysPostModel> data = new ArrayList<>();

        for (int i = 0; i < days.size(); i++) {
            // data.add(new ChamberDaysPostModel(days.get(i),startTime.get(i),endTime.get(i)));
            ChamberDaysPostModel model = new ChamberDaysPostModel();
            model.setDay(days.get(i));
            model.setStartTime(startTime.get(i));
            model.setEndTime(endTime.get(i));
            data.add(model);

        }
        MyProgressBar.with(context).show();
        Gson gson = new Gson();
        //  Toast.makeText(context, gson.toJson(data), Toast.LENGTH_LONG).show();
        Api.getInstance().setDrSchedule(TOKEN, USER_ID, chamberName, address, fees, followup, gson.toJson(data), this);

    }

    @Override
    public void ondrSchedulePostSuccess(StatusMessage data) {
        MyProgressBar.dismiss();
        if (data != null && data.getStatus() != null && data.getStatus() == true) {
            Toast.makeText(context, data.getMessage(), Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void ondrSchedulePostFailed(String msg) {
        MyProgressBar.dismiss();

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public void Back(View view) {
        onBackPressed();
    }
}
