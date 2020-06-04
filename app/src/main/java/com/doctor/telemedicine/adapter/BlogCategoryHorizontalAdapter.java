package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Fragments.BlogFragmentPatient;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AmbulanceModel;
import com.doctor.telemedicine.model.BlogCategoryNameID;
import com.doctor.telemedicine.model.BlogCategorySelectedBoolean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class BlogCategoryHorizontalAdapter extends RecyclerView.Adapter<BlogCategoryHorizontalAdapter.MyViewHolder> {

    Context context;
    BlogCategorySelectListener blogCategorySelectListener;

    public void setBlogCategorySelectListener(BlogCategorySelectListener b) {
        this.blogCategorySelectListener = b;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        CardView card;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            card = (CardView) view.findViewById(R.id.card);


        }
    }

    public interface BlogCategorySelectListener {
        public void onSelected(int i);
    }


    public BlogCategoryHorizontalAdapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_category_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BlogCategorySelectedBoolean movie = BlogFragmentPatient.list_.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getBlogCategoryNameID().getName());
        if (movie.isSelected()) {
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.white));

        } else {
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movie.isSelected()) {

                } else {
                    for (int i = 0; i < BlogFragmentPatient.list_.size(); i++) {
                        BlogFragmentPatient.list_.get(i).setSelected(false);
                    }
                    BlogFragmentPatient.list_.get(position).setSelected(true);
                    notifyDataSetChanged();
                    blogCategorySelectListener.onSelected(movie.getBlogCategoryNameID().getId());
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return BlogFragmentPatient.list_.size();
    }
}