//package com.doctor.telemedicine.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.doctor.telemedicine.Activity.ChatActivityDr;
//import com.doctor.telemedicine.R;
//import com.doctor.telemedicine.Utils.SessionManager;
//import com.doctor.telemedicine.model.AppointmentModel;
//import com.doctor.telemedicine.model.LastMsgModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
//
///**
// * Created by mukul on 3/10/2019.
// */
//
//
//public class LastMessageAdapter extends RecyclerView.Adapter<LastMessageAdapter.MyViewHolder> {
//    List<LastMsgModel>list=new ArrayList<>();
//
//    Context context;
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView tv_message, tv_name;
//        ImageView circleImageView;
//        RelativeLayout relative_container;
//        CircleImageView photo;
//
//
//        public MyViewHolder(View view) {
//            super(view);
//            tv_name = (TextView) view.findViewById(R.id.tv_name);
//            tv_message = (TextView) view.findViewById(R.id.tv_message);
//            photo = (CircleImageView) view.findViewById(R.id.photo);
//
//
//
//        }
//    }
//
//
//    public LastMessageAdapter(List<LastMsgModel> lists ) {
//        this.list=lists;
//
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.last_message_item, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        final LastMsgModel movie = list.get(position);
//        context = holder.tv_name.getContext();
//        holder.tv_name.setText(movie.getPartnerName());
//        holder.tv_message.setText(movie.getMessageBody());
//        SessionManager sessionManager=new SessionManager(context);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(context, ChatActivityDr.class);
//                if (sessionManager.getUserType().equals("d")) {
//                    i.putExtra("partnerID", movie.getPatientID());
//                    i.putExtra("partnerName", movie.getPartnerName());
//                    i.putExtra("partnerPhoto", movie.getPatientPhoto());
//                    context.startActivity(i);
//                }else if (sessionManager.getUserType().equals("p")){
//                    i.putExtra("partnerID", movie.getDoctorID());
//                    i.putExtra("partnerName", movie.getDoctorName());
//                    i.putExtra("partnerPhoto", movie.getDoctorPhoto());
//                    context.startActivity(i);
//                }
//
//            }
//        });
//
//        if (sessionManager.getUserType().equals("d")) {
//            Glide.with(context).load(PHOTO_BASE+movie.getPatientPhoto()).into(holder.photo);
//
//        }else {
//            Glide.with(context).load(PHOTO_BASE+movie.getDoctorPhoto()).into(holder.photo);
//
//        }
//
//    }
//
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//}