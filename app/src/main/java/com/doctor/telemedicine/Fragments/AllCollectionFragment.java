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

import com.doctor.telemedicine.Activity.PaymentsHistoryActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.GeneralListener;
import com.doctor.telemedicine.adapter.PaymentListDoctorAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.PaymentModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.USER_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCollectionFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context;
    View view;
    boolean isLoaded = false ;

    public AllCollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_collection, container, false);
        ButterKnife.bind(this, view);

                if (true) {


                    PaymentListDoctorAdapter mAdapter = new PaymentListDoctorAdapter(PaymentsHistoryActivity.ALL_COLLECTION_WIDTHDRAWL.getBill_details());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    recycler_view.setLayoutManager(mLayoutManager);
                    recycler_view.setItemAnimator(new DefaultItemAnimator());
                    recycler_view.setAdapter(mAdapter);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view.getContext(),
                            recycler_view.VERTICAL, false);
                   // recycler_view.addItemDecoration(dividerItemDecoration);

                    isLoaded = true ;
                }





        return view;
    }
}
