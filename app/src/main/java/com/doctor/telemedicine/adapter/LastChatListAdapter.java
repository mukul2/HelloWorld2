package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.ChatActivityCommon;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.ChatModelFullBody;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;


public class LastChatListAdapter extends RecyclerView.Adapter<LastChatListAdapter.MyViewHolder> {
    private Context context;
    private List<ChatModelFullBody> list = new ArrayList<>();
    private List<ChatModelFullBody> listfiltered = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_last_msg;
        CircleImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_last_msg = view.findViewById(R.id.tv_last_msg);
            imageView = view.findViewById(R.id.imageView);


//EditUserActivity


        }
    }


    public LastChatListAdapter(Context context, List<ChatModelFullBody> list_) {
        this.context = context;
        this.list = list_;
        this.listfiltered = list_;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.last_chat_item, parent, false);

        return new MyViewHolder(itemView);
    }

    //https://gl-images.condecdn.net/image/lN39xbMKeop/crop/405/f/Gal-Gadot-1.jpg
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ChatModelFullBody data = listfiltered.get(position);
        String TARGET_USER;
        String TARGET_USER_IMAGE;
        String TARGET_USER_NAME;

        if (data.getSender_id().equals(DataStore.USER_ID)) {

            TARGET_USER = data.getRecever_id();
            TARGET_USER_IMAGE = data.getReceiver_photo();
            TARGET_USER_NAME = data.getReceiver_name();
            holder.tv_name.setText(data.getReceiver_name());
            holder.tv_last_msg.setText(data.getMessage_body());
            Glide.with(context).load(PHOTO_BASE + data.getReceiver_photo()).into(holder.imageView);

        } else {
            //im receiver
            TARGET_USER = data.getSender_id();
            TARGET_USER_IMAGE = data.getSender_photo();
            TARGET_USER_NAME = data.getSender_name();

            holder.tv_name.setText(data.getSender_name());
            holder.tv_last_msg.setText(data.getMessage_body());
            Glide.with(context).load(PHOTO_BASE + data.getSender_photo()).into(holder.imageView);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String TARGET_USER;
                String TARGET_USER_IMAGE;
                String TARGET_USER_NAME;


                if (data.getSender_id().equals(DataStore.USER_ID)) {

                    TARGET_USER = data.getRecever_id();
                    TARGET_USER_IMAGE = data.getReceiver_photo();
                    TARGET_USER_NAME = data.getReceiver_name();
                    holder.tv_name.setText(data.getReceiver_name());
                    holder.tv_last_msg.setText(data.getMessage_body());
                  //  Glide.with(context).load(PHOTO_BASE + data.getReceiver_photo()).into(holder.imageView);


                    Intent intent = new Intent(context, ChatActivityCommon.class);
                    intent.putExtra("partner_id", TARGET_USER);
                    intent.putExtra("partner_name", TARGET_USER_NAME);
                    intent.putExtra("partner_photo",  TARGET_USER_IMAGE);
                    context.startActivity(intent);


                } else {
                    //im receiver
                    TARGET_USER = data.getSender_id();
                    TARGET_USER_IMAGE = data.getSender_photo();
                    TARGET_USER_NAME = data.getSender_name();

                    holder.tv_name.setText(data.getSender_name());
                    holder.tv_last_msg.setText(data.getMessage_body());
                   // Glide.with(context).load(PHOTO_BASE + data.getSender_photo()).into(holder.imageView);


                    Intent intent = new Intent(context, ChatActivityCommon.class);
                    intent.putExtra("partner_id", TARGET_USER);
                    intent.putExtra("partner_name", TARGET_USER_NAME);
                    intent.putExtra("partner_photo", TARGET_USER_IMAGE);
                    context.startActivity(intent);

                }


            }
        });


    }

    public boolean removeItem(int position) {
        if (listfiltered.size() >= position + 1) {
            listfiltered.remove(position);
            return true;
        }
        return false;
    }


    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss EEE dd MMM yy");
        return formatter.format(new Date(milliSeconds));
    }

    @Override
    public int getItemCount() {
        return listfiltered.size();

    }


}