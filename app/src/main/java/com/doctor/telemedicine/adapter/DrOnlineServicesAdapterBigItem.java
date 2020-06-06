package com.doctor.telemedicine.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.ChatActivityCommon;
import com.doctor.telemedicine.Activity.DialingActivity;
import com.doctor.telemedicine.Activity.NewDialingActivity;
import com.doctor.telemedicine.Activity.PlaceCallActivity;
import com.doctor.telemedicine.Activity.PrescriptionReviewSendingActivity;
import com.doctor.telemedicine.Activity.ProjectPaypalPaymentActivity;
import com.doctor.telemedicine.Activity.VoiceCallDialActivity;
import com.doctor.telemedicine.Fragments.PatientDoctorsOnlineServiceFragment;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.doForMe;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.OnlineDoctorsServiceInfo;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.CALL_TYPE_AUDIO;
import static com.doctor.telemedicine.Data.Data.CALL_TYPE_VIDEO;
import static com.doctor.telemedicine.Data.Data.CURRENCY_USD;
import static com.doctor.telemedicine.Data.Data.NOW_HITTING_SERVICE;
import static com.doctor.telemedicine.Data.Data.TYPE_OF_CALL;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Fragments.PatientDoctorsOnlineServiceFragment.IS_12_SUBSCRIBED;
import static com.doctor.telemedicine.Fragments.PatientDoctorsOnlineServiceFragment.IS_1_SUBSCRIBED;
import static com.doctor.telemedicine.Fragments.PatientDoctorsOnlineServiceFragment.IS_3_SUBSCRIBED;
import static com.doctor.telemedicine.Fragments.PatientDoctorsOnlineServiceFragment.IS_6_SUBSCRIBED;

/**
 * Created by mukul on 3/10/2019.
 */


public class DrOnlineServicesAdapterBigItem extends RecyclerView.Adapter<DrOnlineServicesAdapterBigItem.MyViewHolder> {
    List<OnlineDoctorsServiceInfo> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_action, tv_fees, tv_not_available;
        RelativeLayout relativeView;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_action = (TextView) view.findViewById(R.id.tv_action);
            tv_fees = (TextView) view.findViewById(R.id.tv_fees);
            tv_not_available = (TextView) view.findViewById(R.id.tv_not_available);
            relativeView = (RelativeLayout) view.findViewById(R.id.relativeView);


        }
    }


    public DrOnlineServicesAdapterBigItem(List<OnlineDoctorsServiceInfo> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item_big, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OnlineDoctorsServiceInfo movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_action.setVisibility(View.VISIBLE);
        holder.tv_name.setText(movie.getServiceNameInfo().getName());
        holder.tv_fees.setText("" + movie.getFees_per_unit() + " "+CURRENCY_USD);

        if (movie.getStatus() == 1) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.tv_not_available.setVisibility(View.GONE);
            holder.relativeView.setAlpha(1);
            holder.itemView.setClickable(true);
            holder.itemView.setActivated(true);
            holder.itemView.setEnabled(true);
        } else {
            holder.relativeView.setAlpha(0.3f);
            holder.tv_not_available.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
            holder.itemView.setActivated(false);
            holder.itemView.setEnabled(false);

        }
        if (movie.getOnlineServiceId() == 5) {
            //prescrion request
            holder.itemView.setOnClickListener((View view) -> {
            Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
            CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                tv_chargeShow.setText(ptext);
            linearPaypal.setOnClickListener((View v) -> {
                Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                i.putExtra("credit", movie.getFees_per_unit());
                i.putExtra("type", "prescriptionRequest");
                context.startActivity(i);
                dialog_.dismiss();
            });
            });
        }
        if (movie.getOnlineServiceId() == 1) {
            holder.itemView.setOnClickListener((View view) -> {


                Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                tv_chargeShow.setText(ptext);
                linearPaypal.setOnClickListener((View v) -> {
                    Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                    i.putExtra("credit", movie.getFees_per_unit());
                    i.putExtra("type", "prescriptionReview");
                    context.startActivity(i);
                    dialog_.dismiss();
                });

            });
        }
        if (movie.getOnlineServiceId() == 6) {
            if (PatientDoctorsOnlineServiceFragment.IS_CHAT_SUBSCRIBED) {
                holder.tv_fees.setText("Payment Done");
                holder.tv_action.setText("");

                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", String.valueOf(movie.getDoctorId()));
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });


            } else {
                holder.itemView.setOnClickListener((View view) -> {


                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "chat");
                        i.putExtra("partner_id", String.valueOf(movie.getDoctorId()));
                        i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                        i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 2) {
            if (PatientDoctorsOnlineServiceFragment.IS_VIDEO_CALL_SUBSCRIBED) {
                holder.tv_fees.setText("Payment Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {


                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "videoAppointment");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 8) {
            if (IS_1_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "1MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 9) {
            if (IS_3_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "3MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 10) {
            if (IS_6_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "6MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 11) {
            if (IS_12_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you "+movie.getFees_per_unit()+"  " +CURRENCY_USD+" .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "12MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }



    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}