package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.Day;
import com.doctor.telemedicine.model.TestList;
import com.doctor.telemedicine.model.TestName;
import com.doctor.telemedicine.model.TestRecommendationInfo;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.singleDrModel;

/**
 * Created by mukul on 3/10/2019.
 */


public class TestListTypeAdapter extends RecyclerView.Adapter<TestListTypeAdapter.MyViewHolder> {

    Context context;
    List<TestRecommendationInfo>testLists=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_type,tv_sl;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_sl = (TextView) view.findViewById(R.id.tv_sl);


        }
    }


    public TestListTypeAdapter(List<TestRecommendationInfo> testLists_ ) {
        this.testLists=testLists_;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_names_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final TestRecommendationInfo movie = testLists.get(position);
        holder.tv_type.setText(movie.getTestInfo().getType());
        holder.tv_name.setText(movie.getTestInfo().getName());
        holder.tv_sl.setText(""+(position+1));




    }

    @Override
    public int getItemCount() {
        return testLists.size();
    }
}