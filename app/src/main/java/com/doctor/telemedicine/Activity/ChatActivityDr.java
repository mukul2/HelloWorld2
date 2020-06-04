//package com.doctor.telemedicine.Activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.doctor.telemedicine.R;
//import com.doctor.telemedicine.Utils.LinearLayoutManagerWithSmoothScroller;
//import com.doctor.telemedicine.Utils.SessionManager;
//import com.doctor.telemedicine.adapter.ChatAdapter;
//import com.doctor.telemedicine.api.Api;
//import com.doctor.telemedicine.model.ChatMessage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
//import static com.doctor.telemedicine.Data.DataStore.TOKEN;
//import static com.doctor.telemedicine.Data.DataStore.USER_ID;
//
//public class ChatActivityDr extends AppCompatActivity {
//    Context context = this;
//    DatabaseReference databaseReference;
//    EditText chat_edit_text1;
//    String roomName = "";
//    List<ChatMessage> chatMessages = new ArrayList<>();
//
//    ChatAdapter mAdapter;
//    @BindView(R.id.recycler_view)
//    RecyclerView recycler_view;
//    @BindView(R.id.tv_title)
//    TextView tv_title;
//    @BindView(R.id.ed_message)
//    EditText ed_message;
//    public static String MY_ID;
//    public static String PARTNER_ID;
//    public static String PARTNER_NAME;
//    public static String PARTNER_PHOTO;
//    SessionManager sessionManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_dr);
//        ButterKnife.bind(this);
//        sessionManager = new SessionManager(this);
//
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//
//        if (bundle != null) {
//            PARTNER_NAME = bundle.getString("partnerName");
//            PARTNER_PHOTO = bundle.getString("partnerPhoto");
//            String partnerID = bundle.getString("partnerID");
//            tv_title.setText(PARTNER_NAME);
//
//            MY_ID = USER_ID;
//            // PARTNER_ID=String.valueOf(NOW_SHOWING_ONLINE_DOC.getId());
//            PARTNER_ID = partnerID;
//            initChatDatabase();
//
//
//        }
//
//        //  setUpStatusbar();
//
//    }
//
//    private void initChatDatabase() {
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        roomName = createChatName(Integer.parseInt(USER_ID), Integer.parseInt(PARTNER_ID));
//        mAdapter = new ChatAdapter(ChatActivityDr.this, chatMessages);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recycler_view.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(context));
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//
//        recycler_view.setAdapter(mAdapter);
//        databaseReference.child("chat").child(roomName).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                chatMessages.add(dataSnapshot.getValue(ChatMessage.class));
//                mAdapter.notifyItemInserted(chatMessages.size() - 1);
//                recycler_view.smoothScrollToPosition(chatMessages.size() - 1);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    public String createChatName(Integer myID, Integer partnerID) {
//        String ret = "";
//        if (myID < partnerID)
//            ret = String.valueOf(myID) + "-" + String.valueOf(partnerID);
//        else
//            ret = String.valueOf(partnerID) + "-" + String.valueOf(myID);
//
//        return ret;
//    }
//
//    private void setUpStatusbar() {
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        //make fully Android Transparent Status bar
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//
//    }
//
//    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
//        Window win = activity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }
//
//    public void back(View view) {
//        onBackPressed();
//    }
//
//    public void send(View view) {
//        String message = ed_message.getText().toString().trim();
//        ed_message.setText("");
//        if (message.length() > 0) {
//            ChatMessage chatMessage = new ChatMessage(MY_ID, PARTNER_ID, message);
//            String key = databaseReference.child("chat").child(roomName).push().getKey();
//            String LastMessagekey = databaseReference.child("chat").child("lastMsg").child(MY_ID).child(roomName).push().getKey();
//            databaseReference.child("chat").child(roomName).child(key).setValue(chatMessage);
//
//            if (sessionManager.getUserType().equals("d")){
//                //own data
//
//
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientName").setValue(PARTNER_NAME);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientPhoto").setValue(PARTNER_PHOTO);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientID").setValue(PARTNER_ID);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("messageBody").setValue(message);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("doctorName").setValue(sessionManager.getUserName());
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("doctorID").setValue(sessionManager.getUserId());
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("doctorPhoto").setValue(sessionManager.get_userPhoto());
//
//                //partner data                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientName").setValue(PARTNER_NAME);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("patientName").setValue(PARTNER_NAME);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("patientPhoto").setValue(PARTNER_PHOTO);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("patientID").setValue(PARTNER_ID);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("messageBody").setValue(message);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("doctorName").setValue(sessionManager.getUserName());
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("doctorID").setValue(sessionManager.getUserId());
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("doctorPhoto").setValue(sessionManager.get_userPhoto());
//
//
//            }else {
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("doctorName").setValue(PARTNER_NAME);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("doctorPhoto").setValue(PARTNER_PHOTO);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("doctorID").setValue(PARTNER_ID);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("messageBody").setValue(message);
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientName").setValue(sessionManager.getUserName());
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientID").setValue(sessionManager.getUserId());
//                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientPhoto").setValue(sessionManager.get_userPhoto());
//
//                //partner data                databaseReference.child("chat").child("lastMsg").child(MY_ID).child(PARTNER_ID).child("patientName").setValue(PARTNER_NAME);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("doctorName").setValue(PARTNER_NAME);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("doctorPhoto").setValue(PARTNER_PHOTO);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("doctorID").setValue(PARTNER_ID);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("messageBody").setValue(message);
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("patientName").setValue(sessionManager.getUserName());
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("patientID").setValue(sessionManager.getUserId());
//                databaseReference.child("chat").child("lastMsg").child(PARTNER_ID).child(MY_ID).child("patientPhoto").setValue(sessionManager.get_userPhoto());
//            }
//
//
//            //partner data
//
//        }
//    }
//
//}
//
