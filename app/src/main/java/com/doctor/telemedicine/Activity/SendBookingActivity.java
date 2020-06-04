package com.doctor.telemedicine.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.Day;
import com.doctor.telemedicine.model.StatusResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.singleDrModel;

public class SendBookingActivity extends AppCompatActivity implements ApiListener.appoinetmentPOstListener {
    @BindView(R.id.ed_problems)
    EditText ed_problems;
    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.ed_name)
    EditText ed_name;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    String problem, name, phone;
    SessionManager sessionManager;
    List<Integer> weekDays = new ArrayList<>();
    List<String> times = new ArrayList<>();
    Calendar calendar;

    @BindView(R.id.tv_datePlus)
    TextView tv_datePlus;
    @BindView(R.id.tv_dateMinus)
    TextView tv_dateMinus;
    @BindView(R.id.tv_weekDay)
    TextView tv_weekDay;
    int date, month;
    String appointmentDate;
    Calendar todayCalender;
    String selectedTime="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_booking);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        todayCalender = Calendar.getInstance();
        date = calendar.getTime().getDate();
        month = calendar.getTime().getMonth();
        sessionManager = new SessionManager(this);
        weekDays.clear();
        for (int i = 0; i < singleDrModel.getDays().size(); i++) {
            List<Day> d = singleDrModel.getDays();
            weekDays.add(Integer.parseInt(d.get(i).getDay()));
            times.add(d.get(i).getTime());

        }
        tv_weekDay.setText(DataStore.convertToWeekDay(String.valueOf(calendar.getTime().getDay())));
        tv_month.setText(DataStore.convertToMonth(calendar.getTime().getMonth()));
        tv_date.setText("" + calendar.getTime().getDate());

        tv_datePlus.setOnClickListener((View v) -> nextDate());
        tv_dateMinus.setOnClickListener((View v) -> previousDate());
        Check();
    }

    private void previousDate() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.after(todayCalender)) {

            MinusCheck();
        }else {
            Toast.makeText(this, "Cannot appoint for previousday", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextDate() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Check();


    }

    private void Check() {

        date = calendar.getTime().getDate();

        month = calendar.getTime().getMonth();
        calendar.setTime(new Date(calendar.getTime().getYear(), month, date));
        tv_weekDay.setText(DataStore.convertToWeekDay(String.valueOf(calendar.getTime().getDay())));

        tv_date.setText("" + calendar.getTime().getDate());
        tv_month.setText(DataStore.convertToMonth(calendar.getTime().getMonth()));


        boolean isAvailable = getIsDrChamberOpen(calendar);
        tv_startTime.setText(selectedTime);

        if (isAvailable) {
            //  Toast.makeText(this, "Dr is available today", Toast.LENGTH_SHORT).show();
            appointmentDate=""+calendar.get(Calendar.YEAR)+"-"+String.valueOf(calendar.getTime().getMonth()+1)+"-"+calendar.getTime().getDate()+" "+"00:00:00";
            //Toast.makeText(this, appointmentDate, Toast.LENGTH_LONG).show();
            tv_year.setText(""+calendar.get(Calendar.YEAR));

        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Check();

        }


    }
    private void MinusCheck() {

        date = calendar.getTime().getDate();

        month = calendar.getTime().getMonth();
        calendar.setTime(new Date(calendar.getTime().getYear(), month, date));
        tv_weekDay.setText(DataStore.convertToWeekDay(String.valueOf(calendar.getTime().getDay())));
        tv_date.setText("" + calendar.getTime().getDate());
        tv_month.setText(DataStore.convertToMonth(calendar.getTime().getMonth()));


        boolean isAvailable = getIsDrChamberOpen(calendar);
        tv_startTime.setText(selectedTime);

        if (isAvailable) {
            //  Toast.makeText(this, "Dr is available today", Toast.LENGTH_SHORT).show();
            appointmentDate=""+calendar.get(Calendar.YEAR)+"-"+String.valueOf(calendar.getTime().getMonth()+1)+"-"+calendar.getTime().getDate()+" "+"00:00:00";
            tv_year.setText(""+calendar.get(Calendar.YEAR));

        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            MinusCheck();

        }


    }
    private boolean getIsDrChamberOpen(Calendar calendar) {
        boolean status = false;
        for (int d = 0; d < weekDays.size(); d++) {
            if (calendar.getTime().getDay() == weekDays.get(d)) {
                status = true;
                selectedTime=times.get(d);
                break;
            } else {
                status = false;
            }
        }


        return status;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    public void submit(View view) {
        problem = ed_problems.getText().toString().trim();
        phone = ed_phone.getText().toString().trim();
        name = ed_name.getText().toString().trim();
        MyProgressBar.with(this);

        Api.getInstance().postAppointment(sessionManager.getUserId(), singleDrModel.getId(), singleDrModel.getDr_id(), name, phone, problem,appointmentDate, this);
    }

    @Override
    public void onAppointmentPostSuccess(StatusResponse status) {
        MyProgressBar.dismiss();
        if (status.getStatus()) {
            MyDialog.getInstance().with(SendBookingActivity.this)
                    .message("Your appointment request is sent successfully.Please wait till confirmation")
                    .autoBack(false)
                    .autoDismiss(false)
                    .showMsgOnly();
        } else {
            MyDialog.getInstance().with(SendBookingActivity.this)
                    .message("Techinal error.Try again later")
                    .autoBack(false)
                    .autoDismiss(false)
                    .showMsgOnly();
        }
    }

    @Override
    public void onAppointmentPostFailed(String msg) {
        MyProgressBar.dismiss();

        MyDialog.getInstance().with(SendBookingActivity.this)
                .message("Techinal error.Try again later")
                .autoBack(false)
                .autoDismiss(false)
                .showMsgOnly();

    }

    public void back(View view) {
        onBackPressed();
    }
}
