package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.EducationInfo;
import com.doctor.telemedicine.model.SkillInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class SkillAdapterDoctor extends RecyclerView.Adapter<SkillAdapterDoctor.MyViewHolder> {
    List<SkillInfo>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView skill, tv_body;
        ImageView circleImageView;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            skill = (TextView) view.findViewById(R.id.skill);
            tv_body = (TextView) view.findViewById(R.id.tv_body);



        }
    }


    public SkillAdapterDoctor(List<SkillInfo> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SkillInfo data = list.get(position);
        context = holder.skill.getContext();
        holder.skill.setText(data.getBody());


    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}