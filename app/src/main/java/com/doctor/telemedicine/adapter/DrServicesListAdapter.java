package com.doctor.telemedicine.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.doForMe;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.OnlineServicePostModel;
import com.doctor.telemedicine.model.ServiceWithBoolean;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.testModelList;
import static com.doctor.telemedicine.Fragments.OnlineFragmentDr.SERVICES_LIST;


/**
 * Created by mukul on 3/10/2019.
 */


public class DrServicesListAdapter extends RecyclerView.Adapter<DrServicesListAdapter.MyViewHolder> {

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name,tv_fees;
        CheckBox checkbox;
        CardView cardFees;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_fees = (TextView) view.findViewById(R.id.tv_fees);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            cardFees = (CardView) view.findViewById(R.id.cardFees);


        }
    }


    public DrServicesListAdapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dr_services_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ServiceWithBoolean data = SERVICES_LIST.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(data.getServiceName().getName());
        holder.tv_fees.setText(""+data.getFees()+" BDT");
        if (data.isSelected() == true) {
            holder.checkbox.setChecked(true);
            holder.cardFees.setClickable(true);
            holder.cardFees.setEnabled(true);
            holder.cardFees.setVisibility(View.VISIBLE);
        } else {
            holder.checkbox.setChecked(false);
            holder.cardFees.setClickable(false);
            holder.cardFees.setEnabled(false);
            holder.cardFees.setVisibility(View.GONE);

        }
        holder.cardFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = doForMe.showDialog(context,R.layout.dialog_change_fees);
                EditText fees = (EditText) dialog.findViewById(R.id.ed_fees);
                fees.setText(""+data.getFees());
                CardView  saveCard = dialog.findViewById(R.id.cardSave);
                saveCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String feesToSave  = fees.getText().toString().trim();
                        if(feesToSave.length()>0) {
                            MyProgressBar.with(context);
                            if (true) {
                                Api.getInstance().drServiceFeesUpdate(TOKEN, USER_ID, "" + data.getServiceName().getId(), feesToSave, new ApiListener.updateDrServiceListener() {
                                    @Override
                                    public void onDrUpdateServiceSuccess(StatusMessage data) {
                                        Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
                                        MyProgressBar.dismiss();
                                        dialog.dismiss();
                                        if (data.getStatus()) {
                                            holder.tv_fees.setText("" + feesToSave + " BDT");
                                        }
                                    }

                                    @Override
                                    public void onDrUpdateServiceFailed(String msg) {
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                        MyProgressBar.dismiss();
                                    }
                                });

                            }else {
                                Api.getInstance().drServiceAdd(TOKEN, USER_ID, "" + data.getServiceName().getId(),feesToSave, new ApiListener.DrAddServiceListener() {
                                    @Override
                                    public void onDrAddServiceSuccess(StatusMessage data) {
                                        MyProgressBar.dismiss();
                                        dialog.dismiss();
                                        if (data.getStatus() == true) {
                                            Toast.makeText(context, "Add Success", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Add Failed", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onDrAddServiceFailed(String msg) {
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                        MyProgressBar.dismiss();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SERVICES_LIST.get(position).setSelected(true);
                    //add here
                    Api.getInstance().drServiceAdd(TOKEN, USER_ID, "" + data.getServiceName().getId(),"00", new ApiListener.DrAddServiceListener() {
                        @Override
                        public void onDrAddServiceSuccess(StatusMessage data) {
                            if (data.getStatus() == true) {
                                Toast.makeText(context, "Add Success", Toast.LENGTH_SHORT).show();
                                holder.cardFees.setClickable(true);
                                holder.cardFees.setEnabled(true);
                                holder.cardFees.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(context, "Add Failed", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onDrAddServiceFailed(String msg) {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


                        }
                    });


                } else {
                    SERVICES_LIST.get(position).setSelected(false);

                    Api.getInstance().drServiceDelete(TOKEN, USER_ID, "" + data.getServiceName().getId(), new ApiListener.DrdeleteServiceListener() {
                        @Override
                        public void onDrDeleteServiceSuccess(StatusMessage data) {
                            if (data.getStatus() == true) {
                                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onDrDeleteServiceFailed(String msg) {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return SERVICES_LIST.size();
    }
}