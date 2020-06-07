package com.doctor.telemedicine.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.telemedicine.Activity.PaymentsHistoryActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.GeneralListener;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.doForMe;
import com.doctor.telemedicine.adapter.PaymentListDoctorAdapter;
import com.doctor.telemedicine.adapter.WidhdrawListDoctorAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.CURRENCY_USD_SIGN;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllWidthdrawFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.cardAddRequest)
    CardView cardAddRequest;
    Context context;
    View view;

    public AllWidthdrawFragment() {
        // Required empty public constructor
    }

    boolean isLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_widthdraw, container, false);
        ButterKnife.bind(this, view);
        context = view.getContext();

        // Toast.makeText(context, "widh draw "+PaymentsHistoryActivity.ALL_COLLECTION_WIDTHDRAWL.getWidthdrawModel().size(), Toast.LENGTH_SHORT).show();
        if (true) {
            WidhdrawListDoctorAdapter mAdapter = new WidhdrawListDoctorAdapter(PaymentsHistoryActivity.ALL_COLLECTION_WIDTHDRAWL.getWidthdrawModel());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view.getContext(), recycler_view.VERTICAL, false);
           // recycler_view.addItemDecoration(dividerItemDecoration);
            isLoaded = true;
        }
        //add_withdrawal_request

        cardAddRequest.setOnClickListener((View view) -> {
            Dialog dialog = doForMe.showDialog(context, R.layout.widthdraw_request_dialog);
            CardView cardRequest = (CardView) dialog.findViewById(R.id.cardRequest);
            EditText ed_amount = (EditText) dialog.findViewById(R.id.ed_amount);
            EditText ed_bank = (EditText) dialog.findViewById(R.id.ed_bank);
            TextView tv_balance = (TextView) dialog.findViewById(R.id.tv_balance);
            float balance_ = (PaymentsHistoryActivity.ALL_COLLECTION_WIDTHDRAWL.getTotal_bill() - PaymentsHistoryActivity.ALL_COLLECTION_WIDTHDRAWL.getAll_widthdraw());
            String balance = "Wallet ballance :" + balance_ + CURRENCY_USD_SIGN;
            tv_balance.setText(balance);

            cardRequest.setOnClickListener((View view_) -> {
                String amount = ed_amount.getText().toString().trim();
                String bank = ed_bank.getText().toString().trim();
                if (amount.trim().length() > 0) {
                    if (Integer.parseInt(amount) <= balance_) {
                        if (bank.trim().length() > 0) {
                            MyProgressBar.with(context);
                            Api.getInstance().add_withdrawal_request(TOKEN, USER_ID, amount, bank, new ApiListener.basicApiListener() {
                                @Override
                                public void onBasicSuccess(StatusMessage response) {
                                    MyProgressBar.dismiss();
                                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    startActivity(new Intent(context,PaymentsHistoryActivity.class));
                                    ((Activity)context).finish();
                                }

                                @Override
                                public void onBasicApiFailed(String msg) {
                                    MyProgressBar.dismiss();
                                    Toast.makeText(context,msg, Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                    }
                }

            });
        });


        return view;
    }
}
