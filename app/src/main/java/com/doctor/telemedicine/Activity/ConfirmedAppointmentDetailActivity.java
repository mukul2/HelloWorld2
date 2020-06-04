package com.doctor.telemedicine.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.appointmentModel;

public class ConfirmedAppointmentDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_appointmentfor)
    TextView tv_appointmentfor;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_spacialist)
    TextView tv_spacialist;
    @BindView(R.id.profile_pic)
    CircleImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_appointment_detail);
        ButterKnife.bind(this);

        try {
            tv_name.setText(appointmentModel.getDr_info().getName());
            tv_address.setText("No address in DB");
            tv_appointmentfor.setText(appointmentModel.getName());
            tv_date.setText(appointmentModel.getDate());
            tv_spacialist.setText("No department in DB");
            Glide.with(ConfirmedAppointmentDetailActivity.this).load(PHOTO_BASE + appointmentModel.getDr_info().getPhoto()).into(profile_pic);

            Intent iin = getIntent();
            Bundle b = iin.getExtras();

            if (b != null) {

                String j2 = (String) b.get("refID");

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    public void back(View view) {
        onBackPressed();
    }
}
