package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.SpacialistModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.spacialist;

/**
 * Created by mukul on 3/10/2019.
 */


public class SpacialistAdapter extends RecyclerView.Adapter<SpacialistAdapter.MyViewHolder> {


    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView circleImageView;
        RelativeLayout relative_container;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_name);
            circleImageView = (ImageView) view.findViewById(R.id.img);
            relative_container = (RelativeLayout) view.findViewById(R.id.relative_container);

        }
    }


    public SpacialistAdapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spacialist_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
     //   final SpacialistModel movie = spacialist.get(position);
        holder.title.setText( spacialist.get(position).getName());
        context=holder.title.getContext();
        final int sdk = android.os.Build.VERSION.SDK_INT;

        if ( spacialist.get(position).isSelected()){
            //holder.relative_container.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.relative_container.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shadow_colored) );
            } else {
                holder.relative_container.setBackground(ContextCompat.getDrawable(context, R.drawable.shadow_colored));
            }
            holder.circleImageView.setImageResource(R.drawable.doctor_white);
            holder.title.setTextColor(Color.WHITE);
        }else {
          //  holder.relative_container.setBackgroundColor(Color.WHITE);
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.relative_container.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shadow_curved) );
            } else {
                holder.relative_container.setBackground(ContextCompat.getDrawable(context, R.drawable.shadow_curved));
            }
            holder.circleImageView.setImageResource(R.drawable.doctor);
            holder.title.setTextColor(Color.GRAY);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( spacialist.get(position).isSelected()) {
                    for (int i = 0; i < spacialist.size(); i++) {
                        spacialist.get(i).setSelected(false);
                    }
                    notifyDataSetChanged();

                }else {
                    for (int i = 0; i < spacialist.size(); i++) {
                        spacialist.get(i).setSelected(false);
                    }
                    spacialist.get(position).setSelected(true);
                    notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return spacialist.size();
    }
}