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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.REQUEST_TO_PRESCRIBE;
import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.selectedSearchAppointmentModel;

public class PrescriptionGivingActivity extends AppCompatActivity implements ApiListener.DownloadMedicinesListInfoListener,
        ApiListener.PrescriptionAddListener, ApiListener.ReplyPrescriptionRequestListener {
    List<MedicineModel> ALL_MEDICINE = new ArrayList<>();
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.ed_diseases)
    EditText ed_diseases;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_problem)
    TextView tv_problem;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context = this;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_giving);

        setUpStatusbar();
        ButterKnife.bind(this);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String type = bundle.getString("type");
            if (type.equals("chambePrescription")) {
                TYPE = "chambePrescription";
                //selectedSearchAppointmentModel
                Glide.with(context).load(PHOTO_BASE + selectedSearchAppointmentModel.getPatientInfo().getPhoto()).into(image);
                tv_name.setText(selectedSearchAppointmentModel.getPatientInfo().getName());
                tv_problem.setText(selectedSearchAppointmentModel.getProblems());

            } else if (type.equals("prescriptionRequest")) {
                TYPE = "prescriptionRequest";

                requestPrescibe();
            }
        } else {
            Toast.makeText(context, "Unknown type", Toast.LENGTH_LONG).show();


        }

        Api.getInstance().getMedicinesList(TOKEN, this);


    }

    private void requestPrescibe() {
        Glide.with(context).load(PHOTO_BASE + REQUEST_TO_PRESCRIBE.getPatientInfo().getPhoto()).into(image);
        tv_name.setText(REQUEST_TO_PRESCRIBE.getPatientInfo().getName());
        tv_problem.setText(REQUEST_TO_PRESCRIBE.getProblem());
    }

    private void initMedicineRecycler() {
        mAdapter = new PrescriptionGivingAdapterDoctor(medicineList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }

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

    //
    public void AddMedicine(View view) {

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

        final Dialog dialog = new Dialog(PrescriptionGivingActivity.this);
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
                            Toast.makeText(PrescriptionGivingActivity.this, "Please add medicine dose duration", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PrescriptionGivingActivity.this, "Please add medicine dose", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(PrescriptionGivingActivity.this, "Please add medicine dose", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(PrescriptionGivingActivity.this, "" + selected_med, Toast.LENGTH_LONG).show();
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


    @Override
    public void onDownloadMedicinesListFailed(String msg) {
        ALL_MEDICINE.clear();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    public void submit(View view) {
        String diseases = ed_diseases.getText().toString().trim();
        String PRESCRIPTION_SERVICE_ID="5";

        if (diseases.length() > 0) {
            if (true) {
                MyProgressBar.with(context);
                Gson gson = new Gson();
                if (TYPE.equals("chambePrescription")) {
                    Api.getInstance().addPrecription(TOKEN, USER_ID, "" + selectedSearchAppointmentModel.getPatientId(), gson.toJson(medicineList), diseases, "" + selectedSearchAppointmentModel.getId(),PRESCRIPTION_SERVICE_ID ,SESSION_MANAGER.getUserName(),this);

                } else if (TYPE.equals("prescriptionRequest")) {
                    Api.getInstance().replyPrescriptionRequest(TOKEN, USER_ID, "" + REQUEST_TO_PRESCRIBE.getPatientId(), gson.toJson(medicineList), diseases, "" + REQUEST_TO_PRESCRIBE.getId(), PRESCRIPTION_SERVICE_ID,SESSION_MANAGER.getUserName(),this);

                }
            }
        }
    }

    @Override
    public void onrescriptionAddSuccess(StatusMessage data) {
        MyProgressBar.dismiss();
        Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void onrescriptionAddFailed(String msg) {
        MyProgressBar.dismiss();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReplyPrescriptionRequestSuccess(StatusMessage data) {
        MyProgressBar.dismiss();
        Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void onReplyPrescriptionRequestFailed(String msg) {
        MyProgressBar.dismiss();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
