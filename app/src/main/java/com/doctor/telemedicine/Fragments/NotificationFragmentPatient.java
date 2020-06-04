package com.doctor.telemedicine.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.NotificationLitsAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.NoticeModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.doctor.telemedicine.Data.Data.bottom_nav;
import static com.doctor.telemedicine.Data.Data.isBadgeShowing;
import static com.doctor.telemedicine.Data.Data.itemView;
import static com.doctor.telemedicine.Data.Data.notificationBadge;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragmentPatient extends Fragment implements ApiListener.NoticesDownloadListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    View view;
    Context context;
    int COUNTER = 0;


    public NotificationFragmentPatient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification_fragment_patient, container, false);
        ButterKnife.bind(this, view);
        context = view.getContext();


        COUNTER = 0;
        Api.getInstance().getMyNotices(TOKEN, USER_ID, this);

        return view;
    }


    @Override
    public void onNoticesDownloadSuccess(List<NoticeModel> list) {

        if (list != null && list.size() > 0) {


            //badge


            //badge
            NotificationLitsAdapter mAdapter = new NotificationLitsAdapter(list);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(mAdapter);


            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSeen() == 0) {
                    COUNTER++;

                    if (isBadgeShowing == false) {
                        addBadgeView();
                    } else {
                        itemView.removeView(notificationBadge);
                        itemView.addView(notificationBadge);
                        isBadgeShowing = true;
                    }

                    break;
                } else {


                }
            }
            if (COUNTER == 0) {
                refreshBadgeView();
            }

            // Toast.makeText(context, "counter size "+COUNTER, Toast.LENGTH_LONG).show();
            // Toast.makeText(context, "data size "+list.size(), Toast.LENGTH_LONG).show();
            // refreshBadgeView();

        }


    }

    private void addBadgeView() {

        if (notificationBadge != null) {
            notificationBadge.setVisibility(VISIBLE);
            isBadgeShowing = true;
        }
    }

    private void refreshBadgeView() {

        if (isBadgeShowing = true) {

            if (notificationBadge != null) {
                notificationBadge.setVisibility(GONE);
                isBadgeShowing = false;
            }
        }
    }

    @Override
    public void onNoticesDownloadFailed(String msg) {

    }

}
