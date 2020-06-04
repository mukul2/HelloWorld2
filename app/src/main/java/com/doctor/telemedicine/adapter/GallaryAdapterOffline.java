package com.doctor.telemedicine.adapter;

/**
 * Created By TAOHID on 10/25/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.PhotoInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;


/**
 * Created by ravi on 16/11/17.
 */

public class GallaryAdapterOffline extends RecyclerView.Adapter<GallaryAdapterOffline.MyViewHolder> {
    private Context contex;
    private List<PhotoInfo> contactListFiltered;


    public class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img);




            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                  //  listener.onNotificationSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public GallaryAdapterOffline(Context context, List<PhotoInfo> contactList) {
        this.contex = context;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item_no_close, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PhotoInfo data = contactListFiltered.get(position);

       // holder.imageView.setImageURI(Uri.parse(contact));
       //Glide.with(context).load(BaseUrl+"uploads/"+contact).into(holder.imageView);
            Glide.with(contex).load(data.getPhoto()).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    TEMP_LINK=contact;
//                    contex.startActivity(new Intent(contex, ImageFullScreenActivity.class));
                }
            });








    }
    public boolean removeItem(int position) {
        if (contactListFiltered.size() >= position + 1) {
            contactListFiltered.remove(position);
            return true;
        }
        return false;
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
       return  targetFormat.format(sourceDate);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }



}