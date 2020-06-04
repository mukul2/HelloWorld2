package com.doctor.telemedicine.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.TestListAdapter;
import com.doctor.telemedicine.model.DaysTimeModel;
import com.doctor.telemedicine.model.TestModel;
import com.doctor.telemedicine.model.testSelectedModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.doctor.telemedicine.Data.DataStore.selectedTestIds;
import static com.doctor.telemedicine.Data.DataStore.testModelList;

/**
 * Created by mukul on 3/22/2019.
 */

public class MyDialogList {
    TextView tv_start_time;
    TextView tv_end_time;
    int startTime = 0, endTime = 0;
    private static MyDialogList myDialogList;
    Activity activity;
    DaysSelectedListener daysSelectedListener;
    testSelectedListener testSelectListener ;
    List<testSelectedModel> testModelLists=new ArrayList<>();
    int TYPE_START = 0;
    int TYPE_END = 1;
    DaysTimeModel daysTimeModel=new DaysTimeModel();
    private int mYear, mMonth, mDay, mHour, mMinute;
    public static MyDialogList getInstance() {

        if (myDialogList == null) {
            myDialogList = new MyDialogList();
        }
        return myDialogList;
    }
    public  MyDialogList with(Activity activity) {
        this.activity = activity;
        return this;
    }
    public  MyDialogList setData( List<testSelectedModel> data) {
        this.testModelLists = data;
        return this;
    }
    public interface DaysSelectedListener{
        void onDaysSelected(DaysTimeModel day);
        void onDaysSelectCanceled(String canceled);
    }
    public  interface testSelectedListener{
        void onDialogCloased( List<String>selectedTest);
    }
    public void dayAddDialog(DaysSelectedListener listener) {
        this.daysSelectedListener=listener;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.day_select_dialog);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv_done = (TextView) dialog.findViewById(R.id.tv_done);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        Spinner spinnerDays = (Spinner) dialog.findViewById(R.id.spinnerDays);
        tv_start_time = (TextView) dialog.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) dialog.findViewById(R.id.tv_end_time);
        tv_start_time.setText("Select time");
        tv_end_time.setText("Select time");
        List<String> list = DataStore.sevenDays();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(dataAdapter);
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                daysTimeModel.setDayName("" + (Integer.parseInt(DataStore.convertDayToNmber(list.get(i)))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysSelectedListener.onDaysSelected(daysTimeModel);


                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                daysSelectedListener.onDaysSelectCanceled("cancled");

                dialog.dismiss();
            }
        });
        tv_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPicker(tv_start_time, TYPE_START);
            }
        });
        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPicker(tv_end_time, TYPE_END);
            }
        });


    }
    private void showPicker(TextView textView, int TYPE) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String formatedTime = formateDate(hourOfDay, minute);
                        if (TYPE == TYPE_START) {
                            daysTimeModel.setStartTime(doubleDigit(hourOfDay) + ":" + doubleDigit(minute));
                            startTime = (hourOfDay * 60) + minute;

                        } else {
                            daysTimeModel.setEndTime(doubleDigit(hourOfDay) + ":" + doubleDigit(minute));
                            endTime = (hourOfDay * 60) + minute;


                        }


                        textView.setText(doubleDigit(hourOfDay) + ":" + doubleDigit(minute));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    private String formateDate(int hourOfDay, int minute) {
        String newHour = doubleDigit(hourOfDay % 12);
        String newMinute = doubleDigit(minute);
        String am_pm = amPm(hourOfDay);
        if (am_pm.equals("AM") && newHour.equals("00")) {
            newHour = "12";
        }
        return (newHour + " : " + newMinute + " " + am_pm);
    }

    private String amPm(int value) {
        return value > 12 ? "PM" : "AM";
    }

    private String doubleDigit(int value) {

        String s1 = "0" + value;
        String s2 = "" + value;
        return value < 10 ? s1 : s2;
    }
    public void showTestList(testSelectedListener listener) {
        this.testSelectListener=listener;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_with_recycler);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        TextView tv_done = (TextView) dialog.findViewById(R.id.tv_done);
        for (int i=0;i<testModelList.size();i++){
            testModelList.get(i).setSelected(false);

        }

        final TestListAdapter mAdapter = new TestListAdapter(testModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String>selectedTest=new ArrayList<>();
                for (int i=0;i<testModelList.size();i++){
                    if (testModelList.get(i).isSelected()){
                        selectedTest.add(testModelList.get(i).getTestModel().getId());
                    }
                }
                selectedTestIds.clear();
                selectedTestIds.addAll(selectedTest);
                testSelectListener.onDialogCloased(selectedTest);

                dialog.dismiss();
            }
        });


    }
}
