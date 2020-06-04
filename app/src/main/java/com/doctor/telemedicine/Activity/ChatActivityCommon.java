package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.LinearLayoutManagerWithSmoothScroller;
import com.doctor.telemedicine.Utils.PicUploadListener;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.Utils.doForMe;
import com.doctor.telemedicine.adapter.ChatAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.model.ChatModel;
import com.doctor.telemedicine.model.ChatModelFullBody;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Activity.PlaceCallActivity.TARGET_CALL_ID;
import static com.doctor.telemedicine.Data.Data.CALL_TYPE_VIDEO;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.TYPE_OF_CALL;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.USER_TYPE;

public class ChatActivityCommon extends AppCompatActivity {
    String targerUser;
    String roomName = "";
    DatabaseReference databaseReference;
    List<ChatModel> chatMessages = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.ed_message)
    EditText ed_message;
    Context context = this;
    String USER_ID;
    SessionManager sessionManager;
    String partner_name;
    public static String partner_photo;
    String partner_id;
    String CLIEND_ID = "xploreDoc";
    ChatAdapter mAdapter;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.img_call)
    ImageView img_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_common);
        ButterKnife.bind(this);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        sessionManager = new SessionManager(this);
        USER_ID = sessionManager.getUserId();

        if (USER_TYPE.equals("d")){
            img_call.setVisibility(View.VISIBLE);
        }else {
            img_call.setVisibility(View.GONE);

        }


        if (b != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            partner_id = b.getString("partner_id");
            partner_name = (String) b.get("partner_name");
            partner_photo = (String) b.get("partner_photo");
            tv_user_name.setText(partner_name);
            roomName = createChatName(Integer.parseInt(USER_ID), Integer.parseInt(partner_id));
            initChatDatabase();


        }
    }

    private void initChatDatabase() {

        mAdapter = new ChatAdapter(ChatActivityCommon.this, chatMessages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(context));

        recycler_view.setAdapter(mAdapter);
        databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatMessages.add(dataSnapshot.getValue(ChatModel.class));
                mAdapter.notifyItemInserted(chatMessages.size() - 1);
                recycler_view.smoothScrollToPosition(chatMessages.size() - 1);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public String createChatName(Integer myID, Integer partnerID) {
        String ret = "";
        if (myID < partnerID)
            ret = String.valueOf(myID) + "-" + String.valueOf(partnerID);
        else
            ret = String.valueOf(partnerID) + "-" + String.valueOf(myID);

        return ret;
    }

    public void back(View view) {
        onBackPressed();
    }

    public void send(View view) {
        String msg = ed_message.getText().toString();
        if (msg.length() > 0) {
            String key = databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).push().getKey();
            ChatModel model = new ChatModel(String.valueOf(USER_ID), String.valueOf(partner_id), String.valueOf(msg), "TYPE_TEXT", String.valueOf(System.currentTimeMillis()));
            ChatModelFullBody chatModelFullBody = new ChatModelFullBody(String.valueOf(USER_ID), String.valueOf(partner_id), String.valueOf(msg), "TYPE_TEXT", String.valueOf(System.currentTimeMillis()), sessionManager.getUserName(), sessionManager.get_userPhoto(), partner_name, partner_photo);
            databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ChatActivityCommon.this, "Successfully sent", Toast.LENGTH_SHORT).show();
                    ed_message.setText("");

                    //  String k1 = databaseReference.child("nanoExplore").child("lastChatHistory").child(USER_ID).push().getKey();
                    // String k2 = databaseReference.child("nanoExplore").child("lastChatHistory").child(String.valueOf(NOW_SHOWING_CHAT_PARTNER_MODEL.getId())).push().getKey();

                    databaseReference.child(CLIEND_ID).child("lastChatHistory").child(USER_ID).child(String.valueOf(partner_id)).setValue(chatModelFullBody);
                    databaseReference.child(CLIEND_ID).child("lastChatHistory").child(String.valueOf(partner_id)).child(USER_ID).setValue(chatModelFullBody);

                    Api.getInstance().appNotification(String.valueOf(partner_id),partner_name,msg,"chat",PHOTO_BASE+partner_photo,"not_matter");


                }
            });


        }
    }

    public void camera(View view) {
        doForMe.givemePermissionAndTriggerCamera(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //  resultUri = result.getUri();
                //imageView.setImageURI(resultUri);
                ChatModel model = new ChatModel(String.valueOf(USER_ID), String.valueOf(partner_id), String.valueOf(result.getUri()), "TYPE_IMAGE", String.valueOf(System.currentTimeMillis()));

                chatMessages.add(model);
                mAdapter.notifyDataSetChanged();
                recycler_view.smoothScrollToPosition(chatMessages.size() - 1);
                doForMe.uploadPhoto(result.getUri(), context, new PicUploadListener.myPicUploadListener() {
                    @Override
                    public String onPicUploadSucced(String link) {
                        Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show();

                        String key = databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).push().getKey();
                        ChatModel model = new ChatModel(String.valueOf(USER_ID), String.valueOf(partner_id), link, "TYPE_IMAGE", String.valueOf(System.currentTimeMillis()));
                        ChatModelFullBody chatModelFullBody = new ChatModelFullBody(String.valueOf(USER_ID), String.valueOf(partner_id), String.valueOf(link), "TYPE_IMAGE", String.valueOf(System.currentTimeMillis()), sessionManager.getUserName(), sessionManager.get_userPhoto(), partner_name, partner_photo);
                        databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatActivityCommon.this, "Successfully sent", Toast.LENGTH_SHORT).show();
                                ed_message.setText("");
                                clearTheLocalPic(result.getUri());

                                //  String k1 = databaseReference.child("nanoExplore").child("lastChatHistory").child(USER_ID).push().getKey();
                                // String k2 = databaseReference.child("nanoExplore").child("lastChatHistory").child(String.valueOf(NOW_SHOWING_CHAT_PARTNER_MODEL.getId())).push().getKey();

                                databaseReference.child(CLIEND_ID).child("lastChatHistory").child(USER_ID).child(String.valueOf(partner_id)).setValue(chatModelFullBody);
                                databaseReference.child(CLIEND_ID).child("lastChatHistory").child(String.valueOf(partner_id)).child(USER_ID).setValue(chatModelFullBody);

                                Api.getInstance().appNotification(String.valueOf(partner_id),partner_name,"Image sent","chat",PHOTO_BASE+partner_photo,"not_matter");


                            }
                        });


                        return null;
                    }

                    @Override
                    public String onPicUploadingPercentage(String link) {
                        return null;
                    }

                    @Override
                    public String onPicUploadFailed(String errorMessage) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();

                        return null;
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void clearTheLocalPic(Uri uri) {
        for (int i=chatMessages.size()-1;i>-1;i--){
            if (chatMessages.get(i).getMessage_body().equals(uri.toString())){
                chatMessages.remove(i);
                mAdapter.notifyDataSetChanged();
                recycler_view.smoothScrollToPosition(chatMessages.size() - 1);
            }
        }
    }

    public void call(View view) {

        Intent intent = new Intent(context, PlaceCallActivity.class);
        if (sessionManager.getUserType().equals("d")) {

            intent.putExtra("" + sessionManager.getUserName(), "DOCTOR_NAME");
            intent.putExtra("" +partner_name, "PATIENT_NAME");
            intent.putExtra("" +partner_photo, "USER_PHOTO");

        }else {
            // Toast.makeText(context, "Call can be only done by patient", Toast.LENGTH_SHORT).show();
            intent.putExtra("" +partner_name, "DOCTOR_NAME");
            intent.putExtra("" + sessionManager.getUserName(), "PATIENT_NAME");
            intent.putExtra("" +partner_photo, "USER_PHOTO");

        }
        try {
            TYPE_OF_CALL = CALL_TYPE_VIDEO;

            intent.putExtra("" + partner_id, "targerUser");
            TARGET_CALL_ID=partner_id;


            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }



    }
}
