package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.adapter.PrescriptionGivingAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.MedicineModel;
import com.doctor.telemedicine.model.PrescriptionMedicineModel;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.DataStore.REVIEW_MODEL;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PrescriptionReviewReplyActivity extends AppCompatActivity implements ApiListener.DownloadMedicinesListInfoListener,
        ApiListener.PrescriptionAddListener{
    @BindView(R.id.ed_comment)
    EditText ed_comment;
    @BindView(R.id.ed_diagonoses_diseases)
    EditText ed_diagonoses_diseases;
    @BindView(R.id.linearPrescription)
    LinearLayout linearPrescription;
    @BindView(R.id.card_add)
    CardView card_add;
    @BindView(R.id.switch_prescription)
    CheckBox switch_prescription;
    List<MedicineModel> ALL_MEDICINE = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    List<PrescriptionMedicineModel> medicineList = new ArrayList<>();
    int selected_med = 0;
    PrescriptionGivingAdapterDoctor mAdapter;
    Spinner spinnerMedicineName;
    int morning = 0;
    int noon = 0;
    int night = 0;
    String selectedDurationType = null;
    String selectedDuration = null;
    int MORNING_DOSE_COUNT = 0;
    int NOON_DOSE_COUNT = 0;
    int NIGHT_DOSE_COUNT = 0;
    int DURATION_COUNT = 0;

    int BEFORE_MEAL = 0;
    int AFTER_MEAL = 1;

    int MORNIG_STATUS = 0;
    int NOON_STATUS = 0;
    int NIGHT_STATUS = 0;
    String SELECTED_DURATION = "";
    String TYPE;
    Context context=this;
    boolean isPrescriptionON=false;
    String PRESCRIPTION_REVIEW_ID="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_review_reply);
        ButterKnife.bind(this);
        setUpStatusbar();
        setSwitchListener();
        Api.getInstance().getMedicinesList(TOKEN, this);

    }
    //
    public void AddMedicine(View view) {
        if (isPrescriptionON) {

            SELECTED_DURATION = "";
            BEFORE_MEAL = 0;
            AFTER_MEAL = 1;

            MORNIG_STATUS = 0;
            NOON_STATUS = 0;
            NIGHT_STATUS = 0;


            MORNING_DOSE_COUNT = 0;
            NOON_DOSE_COUNT = 0;
            NIGHT_DOSE_COUNT = 0;
            DURATION_COUNT = 0;


            morning = noon = night = 0;
            selectedDurationType = null;
            selectedDuration = null;

            final Dialog dialog = new Dialog(PrescriptionReviewReplyActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.add_medicine_dialog);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            CheckBox checkBox_1 = (CheckBox) dialog.findViewById(R.id.checkbox_1);
            CheckBox checkbox_2 = (CheckBox) dialog.findViewById(R.id.checkbox_2);
            CheckBox checkbox_3 = (CheckBox) dialog.findViewById(R.id.checkbox_3);

//morning start
            TextView tv_morning_minus = (TextView) dialog.findViewById(R.id.tv_morning_minus);
            TextView tv_morning_plus = (TextView) dialog.findViewById(R.id.tv_morning_plus);
            TextView tv_morning_dose = (TextView) dialog.findViewById(R.id.tv_morning_dose);
            tv_morning_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox_1.setChecked(true);
                    MORNING_DOSE_COUNT++;
                    tv_morning_dose.setText("" + MORNING_DOSE_COUNT);

                }
            });
            tv_morning_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MORNING_DOSE_COUNT != 0) {
                        MORNING_DOSE_COUNT--;
                        tv_morning_dose.setText("" + MORNING_DOSE_COUNT);
                        if (MORNING_DOSE_COUNT == 0) {
                            checkBox_1.setChecked(false);

                        }
                    } else {
                        checkBox_1.setChecked(false);

                    }

                }
            });

            //morning ends


//noon start
            TextView tv_noon_minus = (TextView) dialog.findViewById(R.id.tv_noon_minus);
            TextView tv_noon_plus = (TextView) dialog.findViewById(R.id.tv_noon_plus);
            TextView tv_noon_dose = (TextView) dialog.findViewById(R.id.tv_noon_dose);
            tv_noon_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkbox_2.setChecked(true);
                    NOON_DOSE_COUNT++;
                    tv_noon_dose.setText("" + NOON_DOSE_COUNT);

                }
            });
            tv_noon_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NOON_DOSE_COUNT != 0) {
                        NOON_DOSE_COUNT--;
                        tv_noon_dose.setText("" + NOON_DOSE_COUNT);
                        if (NOON_DOSE_COUNT == 0) {
                            checkbox_2.setChecked(false);

                        }
                    } else {
                        checkbox_2.setChecked(false);

                    }

                }
            });

            //morning ends
            //noon start
            TextView tv_night_minus = (TextView) dialog.findViewById(R.id.tv_night_minus);
            TextView tv_night_dose = (TextView) dialog.findViewById(R.id.tv_night_dose);
            TextView tv_night_plus = (TextView) dialog.findViewById(R.id.tv_night_plus);
            tv_night_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkbox_3.setChecked(true);
                    NIGHT_DOSE_COUNT++;
                    tv_night_dose.setText("" + NIGHT_DOSE_COUNT);

                }
            });
            tv_night_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NIGHT_DOSE_COUNT != 0) {
                        NIGHT_DOSE_COUNT--;
                        tv_night_dose.setText("" + NIGHT_DOSE_COUNT);
                        if (NIGHT_DOSE_COUNT == 0) {
                            checkbox_3.setChecked(false);

                        }
                    } else {
                        checkbox_3.setChecked(false);


                    }

                }
            });

            //morning ends

            //duration startes
            TextView duration_plus = (TextView) dialog.findViewById(R.id.tv_duration_plus);
            TextView tv_duration_minus = (TextView) dialog.findViewById(R.id.tv_duration_minus);
            TextView tv_duration_value = (TextView) dialog.findViewById(R.id.tv_duration_value);


            duration_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DURATION_COUNT++;
                    tv_duration_value.setText("" + DURATION_COUNT);

                }
            });
            tv_duration_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (DURATION_COUNT > 0) {
                        DURATION_COUNT--;
                        tv_duration_value.setText("" + DURATION_COUNT);

                    } else {

                    }

                }
            });

            //duration ends


            RadioGroup RadioGroupNight = (RadioGroup) dialog.findViewById(R.id.RadioGroupNight);


            RadioGroupNight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.night_before: {
                            NIGHT_STATUS = 0;
                            Toast.makeText(context, "before", Toast.LENGTH_SHORT).show();

                            break;
                        }
                        case R.id.night_after: {
                            NIGHT_STATUS = 1;
                            Toast.makeText(context, "after", Toast.LENGTH_SHORT).show();


                            break;
                        }
                    }


                }
            });


            CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.getId() == R.id.checkbox_1) {
                        if (b == true) {
                            morning = 1;
                        } else {
                            morning = 0;
                        }
                    }
                    if (compoundButton.getId() == R.id.checkbox_2) {
                        if (b == true) {
                            noon = 1;
                        } else {
                            noon = 0;
                        }
                    }
                    if (compoundButton.getId() == R.id.checkbox_3) {
                        if (b == true) {
                            night = 1;
                        } else {
                            night = 0;
                        }
                    }


                }
            };

            checkBox_1.setOnCheckedChangeListener(listener);
            checkbox_2.setOnCheckedChangeListener(listener);
            checkbox_3.setOnCheckedChangeListener(listener);

            List<String> medicines = new ArrayList<>();
            medicines.add("Select");
            for (int i = 0; i < ALL_MEDICINE.size(); i++) {
                medicines.add(ALL_MEDICINE.get(i).getName());
            }

            TextView tv_add = (TextView) dialog.findViewById(R.id.tv_add);
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedDuration = SELECTED_DURATION;
                    if (selected_med > 0) {
                        if (morning == 1 || noon == 1 || night == 1) {
                            if (selectedDuration != null && (selectedDuration.length() > 0)) {
                                String dose = "" + MORNING_DOSE_COUNT + "-" + NOON_DOSE_COUNT + "-" + NIGHT_DOSE_COUNT;
                                medicineList.add(new PrescriptionMedicineModel(1, SELECTED_DURATION, ALL_MEDICINE.get(selected_med - 1).getName(), "" + DURATION_COUNT, dose, NIGHT_STATUS));
                                mAdapter.notifyItemInserted(medicineList.size() - 1);
                                spinnerMedicineName.setSelection(0);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Please add medicine dose duration", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Please add medicine dose", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(context, "Please add medicine dose", Toast.LENGTH_SHORT).show();

                    }
                }
            });


            spinnerMedicineName = (Spinner) dialog.findViewById(R.id.spinnerMedicineName);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, medicines);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMedicineName.setAdapter(dataAdapter);
            spinnerMedicineName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (true) {
                        selected_med = i;
                        Toast.makeText(context, "" + selected_med, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //duration
            RadioGroup durationRadio = (RadioGroup) dialog.findViewById(R.id.readioGrpDuration);
            durationRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.radioDay: {
                            SELECTED_DURATION = "d";
                            break;

                        }
                        case R.id.radioWeek: {
                            SELECTED_DURATION = "w";
                            break;


                        }
                        case R.id.radioMonth: {
                            SELECTED_DURATION = "m";
                            break;


                        }

                    }
                }
            });
        }


    }

    @Override
    public void onDownloadMedicinesListInfoSuccess(List<MedicineModel> status) {
        ALL_MEDICINE.clear();

        if (status != null) {
            ALL_MEDICINE.addAll(status);
        } else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
        initMedicineRecycler();
    }

    private void initMedicineRecycler() {
        mAdapter = new PrescriptionGivingAdapterDoctor(medicineList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recycler_view.addItemDecoration(dividerItemDecoration);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }
    @Override
    public void onDownloadMedicinesListFailed(String msg) {
        ALL_MEDICINE.clear();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
    private void setSwitchListener() {
        switch_prescription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    card_add.setEnabled(true);
                    card_add.setClickable(true);
                    linearPrescription.setAlpha(1);
                    isPrescriptionON=true;

                } else {
                    card_add.setClickable(false);
                    card_add.setEnabled(false);
                    linearPrescription.setAlpha(0.2f);
                    isPrescriptionON=false;





                }


            }
        });

    }

    public void back(View view) {
        onBackPressed();
    }

    private void setUpStatusbar() {
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


    @Override
    public void onrescriptionAddSuccess(StatusMessage data) {
        MyProgressBar.dismiss();
        //Toast.makeText(context, data.getMessage(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,HomeActivityDrActivity.class));
        finishAffinity();

    }

    @Override
    public void onrescriptionAddFailed(String msg) {
        MyProgressBar.dismiss();


        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }

    public void submit(View view) {
        String comment=ed_comment.getText().toString().trim();
        String diagonoesDis=ed_diagonoses_diseases.getText().toString().trim();
        Gson gson=new Gson();

        if (isPrescriptionON){

            MyProgressBar.with(context);
            Api.getInstance().replyRecheck(TOKEN,USER_ID,""+REVIEW_MODEL.getPatientInfo().getId(),gson.toJson(medicineList),diagonoesDis,""+REVIEW_MODEL.getId(),comment,"1",SESSION_MANAGER.getUserName(),PRESCRIPTION_REVIEW_ID,this);

        }else {
            MyProgressBar.with(context);
            //Api.getInstance().replyRecheck(TOKEN,USER_ID,""+REVIEW_MODEL.getPatientInfo().getId(),null,null,""+REVIEW_MODEL.getId(),comment,"0",this);
            Api.getInstance().replyRecheck(TOKEN,USER_ID,""+REVIEW_MODEL.getPatientInfo().getId(),gson.toJson(medicineList),diagonoesDis,""+REVIEW_MODEL.getId(),comment,"0",SESSION_MANAGER.getUserName(),PRESCRIPTION_REVIEW_ID,this);


        }
    }
}
