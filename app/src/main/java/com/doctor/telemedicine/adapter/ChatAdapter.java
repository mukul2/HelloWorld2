package com.doctor.telemedicine.adapter;

/**
 * Created By TAOHID on 10/25/18.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.ChatActivityCommon;
import com.doctor.telemedicine.Activity.ImageFullScreenActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.ChatModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;


/**
 * Created by ravi on 16/11/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private Context context;
    private List<ChatModel> contactListFiltered;
    int INCOMING = 1;
    int OUTGOING = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_body, tv_district, tv_date, tv_attachment_count;
        LinearLayout notification_row;
        CircleImageView image;
        ImageView imageContent;


        public MyViewHolder(View view) {
            super(view);
            tv_body = view.findViewById(R.id.tv_body);
            image = view.findViewById(R.id.image);
            imageContent = view.findViewById(R.id.img_body);


        }
    }


    public ChatAdapter(Context context, List<ChatModel> contactList) {
        this.context = context;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == INCOMING)
            //reverse happended
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_outgoing, parent, false);
        else
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_incomming, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ChatModel contact = contactListFiltered.get(position);

        Glide.with(context).load(PHOTO_BASE + ChatActivityCommon.partner_photo).into(holder.image);
        if (contact.getMessage_type().equals("TYPE_TEXT")) {
            holder.imageContent.setVisibility(View.GONE);
            holder.tv_body.setVisibility(View.VISIBLE);
            holder.tv_body.setText(contact.getMessage_body());
        }else {
            holder.imageContent.setVisibility(View.VISIBLE);
            holder.tv_body.setVisibility(View.GONE);
            Glide.with(context).load(contact.getMessage_body()).into(holder.imageContent);

        }
        holder.imageContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ImageFullScreenActivity.class);
                intent.putExtra("link",contact.getMessage_body());
                context.startActivity(intent);
            }
        });


    }

    private String getAttachmentCount(String attachment) {
        String ret = "0 attachment";
        try {
            JSONArray jsonArray = new JSONArray(attachment);
            ret = String.valueOf(jsonArray.length()) + " attachment";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String changeDateformate(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd");
        return targetFormat.format(sourceDate);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();

    }

    @Override
    public int getItemViewType(int position) {
        if (contactListFiltered.get(position).getSender_id().equals(USER_ID)) {
            return INCOMING;
        } else return OUTGOING;
    }


}