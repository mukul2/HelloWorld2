package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.adapter.DiseasesAdapterPatient;
import com.doctor.telemedicine.adapter.DoctorsSearchAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DiseasesModel;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PatientDiseaseSumActivity extends AppCompatActivity {
    Context context = this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.floatAdd)
    FloatingActionButton floatAdd;
    private TextView dateView;
    private int year, month, day;
    String dateInString;
    TextView tv_pickDate;
    ApiListener.DiseasesDownloadListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_disease_sum);
        ButterKnife.bind(this);
        setUpStatusbar();
        ApiListener.DiseasesDownloadListener listener = new ApiListener.DiseasesDownloadListener() {
            @Override
            public void onDiseasesDownloadSuccess(List<DiseasesModel> list) {
                DiseasesAdapterPatient mAdapter = new DiseasesAdapterPatient(list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recycler_view.setLayoutManager(staggeredGridLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                // recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
                recycler_view.setAdapter(mAdapter);
            }

            @Override
            public void onDiseasesDownloadSuccessFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        };
        Api.getInstance().DiseasesDownload(TOKEN, USER_ID, listener);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_diseases_dialog);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                CardView done = (CardView) dialog.findViewById(R.id.cardDone);
                tv_pickDate = (TextView) dialog.findViewById(R.id.tv_pickDate);
                EditText ed_status = (EditText) dialog.findViewById(R.id.ed_status);
                EditText ed_body = (EditText) dialog.findViewById(R.id.ed_body);
                tv_pickDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showDialog(999);

                    }
                });



                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String body = ed_body.getText().toString().trim();
                        String status = ed_status.getText().toString().trim();
                        String date = tv_pickDate.getText().toString().trim();
                        if (body.length() > 0) {
                            if (status.length() > 0) {
                                if (date.length() > 0) {
                                    MyProgressBar.with(PatientDiseaseSumActivity.this);
                                    Api.getInstance().addDiseases(TOKEN, USER_ID, body, date, status, new ApiListener.diseasesAddListener() {
                                        @Override
                                        public void onDiseasesAddSuccess(StatusMessage list) {
                                            if (list.getStatus() == true) {
                                                MyProgressBar.dismiss();
                                                dialog.dismiss();
                                                Toast.makeText(PatientDiseaseSumActivity.this, list.getMessage(), Toast.LENGTH_SHORT).show();
                                                Api.getInstance().DiseasesDownload(TOKEN, USER_ID, listener);
                                            }
                                        }

                                        @Override
                                        public void onDiseasesAddSuccessFailed(String msg) {
                                            Toast.makeText(PatientDiseaseSumActivity.this, msg, Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                } else {
                                    Toast.makeText(PatientDiseaseSumActivity.this, "", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(PatientDiseaseSumActivity.this, "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PatientDiseaseSumActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            //  Toast.makeText(context, ""+calendar.getTime().getYear(), Toast.LENGTH_SHORT).show();
            return new DatePickerDialog(this,
                    myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    dateInString = "" + arg1 + "-" + (arg2 + 1) + "-" + arg3;
                    Toast.makeText(context, dateInString, Toast.LENGTH_SHORT).show();
                    tv_pickDate.setText(dateInString);

                }
            };

    public void setUpStatusbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void back(View view) {
        onBackPressed();
    }


}
