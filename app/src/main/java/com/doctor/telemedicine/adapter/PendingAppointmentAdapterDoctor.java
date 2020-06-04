package com.doctor.telemedicine.adapter;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.doctor.telemedicine.Activity.SendBookingActivity;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentModelNew;
import com.doctor.telemedicine.model.AppointmentResponse;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.StatusResponse;
import com.doctor.telemedicine.model.TestIDS;
import com.doctor.telemedicine.model.TestModel;
import com.doctor.telemedicine.model.testSelectedModel;
import com.doctor.telemedicine.widgets.MyDialogList;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.testModelList;
import static com.doctor.telemedicine.Data.lis.Confirmedlistener;
import static com.doctor.telemedicine.Data.lis.Pendinglistener;

/**
 * Created by mukul on 3/10/2019.
 */


public class PendingAppointmentAdapterDoctor extends RecyclerView.Adapter<PendingAppointmentAdapterDoctor.MyViewHolder> {
    List<AppointmentModelNew> list = new ArrayList<>();

    Context context;
    int triggeredItem = 0;
    List<String> TestList = new ArrayList<>();
    int pos;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_problem, tv_date, tv_confirm;
        ImageView circleImageView;
        RelativeLayout relative_container;
        TextView cardPrescribeTest, tv_serial, tv_prescription;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_problem = (TextView) view.findViewById(R.id.tv_problem);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_serial = (TextView) view.findViewById(R.id.tv_serial);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_prescription = (TextView) view.findViewById(R.id.tv_prescription);
            tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);


        }
    }


    public PendingAppointmentAdapterDoctor(List<AppointmentModelNew> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_appointment_dr, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppointmentModelNew movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getName());
        holder.tv_serial.setText("" + movie.getId());
        holder.tv_problem.setText(movie.getProblems());
        holder.tv_date.setText(movie.getDate());
       // holder.tv_address.setText(movie.getChamber_information().getAddress());

        holder.tv_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Api.getInstance().getAllTest(TOKEN, new ApiListener.DownloadTestListInfoListener() {
                    @Override
                    public void onDownloadTestListInfoSuccess(List<TestModel> data) {
                        DataStore.testModelList.clear();
                        //   Toast.makeText(this, ""+data.size(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < data.size(); i++) {
                            DataStore.testModelList.add(new testSelectedModel(false, data.get(i)));
                        }
                        if (DataStore.testModelList.size() > 0) {
                            MyDialogList.getInstance().with((Activity) context).showTestList(new MyDialogList.testSelectedListener() {
                                @Override
                                public void onDialogCloased(List<String> selectedTest) {
                                    String result = "";
                                    List<TestIDS> ids = new ArrayList<>();
                                    for (int i = 0; i < selectedTest.size(); i++) {
                                        ids.add(new TestIDS(selectedTest.get(i)));

                                    }
                                    if (ids.size() > 0) {
                                        //now post
                                        MyProgressBar.with(context);
                                        Gson gson = new Gson();
                                        Api.getInstance().addTestRec(TOKEN, "" + movie.getId(), gson.toJson(ids), new ApiListener.addTestRecListener() {
                                            @Override
                                            public void onAddTestRecSuccess(StatusMessage status) {
                                                MyProgressBar.dismiss();
                                                if (status.getStatus() == true) {
                                                    if (removeItem(position)) {
                                                        notifyItemRemoved(position);
                                                        notifyItemRangeChanged(position, getItemCount());
                                                    }
                                                    Toast.makeText(context, status.getMessage(), Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(context, "Error occured.Try again", Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onAddTestRecFailed(String msg) {
                                                MyProgressBar.dismiss();
                                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                                            }
                                        });


                                    }
                                }
                            });
                        }else {
                            Toast.makeText(context, "no test data found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDownloadTestListFailed(String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.getInstance().with(context).yesNoConfirmation(new MyDialog.confirmListener() {
                    @Override
                    public void onDialogClicked(boolean result) {
                        if (result == true) {
                            changeState("" + movie.getId(), position, "1");
                        } else {

                        }
                    }
                }, "Do you want to confirm this appointment");

            }
        });

        // holder.itemView.setOnClickListener((View v) -> changeState("" + movie.getId(), position,));
//        holder.cardPrescribeTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyDialogList.getInstance().with((Activity) context).showTestList(new MyDialogList.testSelectedListener() {
//                    @Override
//                    public void onDialogCloased(List<String> selectedTest) {
//                        TestList.clear();
//                        TestList.addAll(selectedTest);
//
//                        if (TestList.size() > 0) {
//                            pos = position;
//                            MyProgressBar.with(context).show();
//                           // addRecommendTest(movie.getId(), TestList.get(0), 0);
//
//                        }
//
//                    }
//                });
//            }
//        });

    }

    private void addRecommendTest(String appointment_id, String s, int index) {
        Api.getInstance().postRecommendationTest(appointment_id, s, new ApiListener.recomendationTestPostListener() {
            @Override
            public void onrecomendationTestPostSuccess(StatusResponse response) {

                int in = 1 + index;
                if (TestList.size() > in) {

                    addRecommendTest(appointment_id, TestList.get(in), in);
                } else {
                    MyProgressBar.dismiss();
                    changeToRecommended(appointment_id, pos);
                }


            }

            @Override
            public void onrecomendationTestPostFailed(String msg) {
                MyProgressBar.dismiss();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


            }
        });
    }


    public void changeState(String appointment_id, int pos, String status) {
        MyProgressBar.with(context);
        triggeredItem = pos;
        Api.getInstance().changeStatus(TOKEN, appointment_id, status, new ApiListener.appointmentStateChangeListener() {
            @Override
            public void onAppointmentChangeSuccess(StatusMessage status) {
                MyProgressBar.dismiss();
                if (status.getStatus()) {
                    MyDialog.getInstance().with((Activity) context)
                            .message("This appointment has been confirmed")
                            .autoBack(false)
                            .autoDismiss(false)
                            .show();
                    // list.remove(triggeredItem);
                    if (removeItem(triggeredItem)) {
                        notifyItemRemoved(triggeredItem);
                        notifyItemRangeChanged(triggeredItem, getItemCount());
                    }
                    // Api.getInstance().getAppointmentsByDoctor(USER_ID, this);

                } else {
                    MyDialog.getInstance().with((Activity) context)
                            .message("Failed")
                            .autoBack(false)
                            .autoDismiss(false)
                            .show();
                }

            }

            @Override
            public void onPppointmentChangeFailed(String msg) {
                MyProgressBar.dismiss();
                MyDialog.getInstance().with((Activity) context)
                        .message("Failed")
                        .autoBack(false)
                        .autoDismiss(false)
                        .showMsgOnly();

            }
        });

    }

    public void changeToRecommended(String appointment_id, int pos) {
        MyProgressBar.with(context);
        triggeredItem = pos;
        //  Api.getInstance().changeStatus(appointment_id, "2", this);

    }


    public boolean removeItem(int position) {
        if (list.size() >= position + 1) {
            list.remove(position);
            return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}