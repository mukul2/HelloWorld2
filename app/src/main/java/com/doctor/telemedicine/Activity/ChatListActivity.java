package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.LastChatListAdapter;
import com.doctor.telemedicine.model.ChatModelFullBody;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListActivity extends BaseActivity {
    LastChatListAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Context context=this;
    List<ChatModelFullBody> list = new ArrayList<>();
    DatabaseReference databaseReference;

    String CLIEND_ID = "xploreDoc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);
        init_recycler();
        list.clear();
        mAdapter.notifyDataSetChanged();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(CLIEND_ID).child("lastChatHistory").child(DataStore.USER_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    ChatModelFullBody employeeModelFullProfile = childSnapshot.getValue(ChatModelFullBody.class);

                    list.add(employeeModelFullProfile);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void init_recycler() {
        mAdapter = new LastChatListAdapter(context, list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerview.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));

        recyclerview.setAdapter(mAdapter);
    }
    public void back(View view) {
        onBackPressed();
    }
}
