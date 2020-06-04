package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.AccountPicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.doctor.telemedicine.Activity.AddBlogActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.BlogAdapterPatient;
import com.doctor.telemedicine.adapter.BlogCategoryHorizontalAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.BlogCategoryNameID;
import com.doctor.telemedicine.model.BlogCategorySelectedBoolean;
import com.doctor.telemedicine.model.BlogModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.Data.USER_ENABLED;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;


public class BlogFragmentPatient extends Fragment implements ApiListener.BlogCategoryDownloadListener {
    View v;
    Context context;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.recycler_viewCategory)
    RecyclerView recycler_viewCategory;
    @BindView(R.id.upload)
    ExtendedFloatingActionButton upload;

    public static List<BlogCategorySelectedBoolean> list_ = new ArrayList<>();

    public static BlogFragmentPatient newInstance() {
        BlogFragmentPatient fragment = new BlogFragmentPatient();
        return fragment;
    }

    public BlogFragmentPatient() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.blog_fragment_patient, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);
        //List<BlogModel>
        Api.getInstance().BlogCategoryNameID(TOKEN, this);
        if (SESSION_MANAGER.getUserType().equals("p")) {
            upload.setVisibility(View.GONE);
        } else {
            upload.setVisibility(View.VISIBLE);

        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USER_ENABLED==true) {
                    startActivity(new Intent(context, AddBlogActivity.class));
                }else {
                    Toast.makeText(context, "Your account is not varified yet", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }


    @Override
    public void onBlogCategoryDownloadSuccess(List<BlogCategoryNameID> list) {
        list_.clear();


        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list_.add(new BlogCategorySelectedBoolean(false, list.get(i)));
            }
            list_.get(0).setSelected(true);
            BlogCategoryHorizontalAdapter mAdapter = new BlogCategoryHorizontalAdapter();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recycler_viewCategory.setLayoutManager(layoutManager);
            recycler_viewCategory.setItemAnimator(new DefaultItemAnimator());
            recycler_viewCategory.setAdapter(mAdapter);

            ApiListener.BlogDownloadListener listener = new ApiListener.BlogDownloadListener() {
                @Override
                public void onBlogDownloaSuccess(List<BlogModel> list) {
                    BlogAdapterPatient mAdapter = new BlogAdapterPatient(list);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    recycler_view.setLayoutManager(mLayoutManager);
                    recycler_view.setItemAnimator(new DefaultItemAnimator());
                    recycler_view.setAdapter(mAdapter);
                }

                @Override
                public void onBlogDownloaSuccessFailed(String msg) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                }
            };
            Api.getInstance().blogsDownload(TOKEN, "" + list_.get(0).getBlogCategoryNameID().getId(), listener);

            mAdapter.setBlogCategorySelectListener(new BlogCategoryHorizontalAdapter.BlogCategorySelectListener() {
                @Override
                public void onSelected(int i) {
                    Api.getInstance().blogsDownload(TOKEN, "" + i, listener);

                }
            });


        }


    }

    @Override
    public void onBlogCategoryDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
