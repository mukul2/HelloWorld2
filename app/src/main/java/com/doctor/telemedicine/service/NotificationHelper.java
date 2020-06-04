package com.doctor.telemedicine.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.doctor.telemedicine.Activity.ChatListActivity;
import com.doctor.telemedicine.Activity.DrPendingActivity;
import com.doctor.telemedicine.Activity.PublicAskByPatient;
import com.doctor.telemedicine.Activity.SplashActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationHelper {
    SessionManager sessionManager;

    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "100011";
    String userType;

    public NotificationHelper(Context context) {
        mContext = context;
        sessionManager = new SessionManager(context);
        userType = sessionManager.getUserType();

    }

    /**
     * Create and push the notification
     */
    public void createNotification(String title, String body, String intent, Bitmap image, String targetUserType) {
        /**Creates an explicit intent for an Activity in your app**/


        //userType.equals(SharedData.LEVEL_1) && userType.equals(SharedData.LEVEL_2) &&
        //!((userType.equals(SharedData.LEVEL_1) && intent.equals("job")) || (userType.equals(SharedData.LEVEL_2) && intent.equals("job")))
        Intent resultIntent = null;
        if (true) {

            String message = "";


            switch (intent) {
                case "chat": {
                    resultIntent = new Intent(mContext, ChatListActivity.class);
                    message = body;
                    title = title;
                    break;
                }
                case "appointment": {
                    if (targetUserType.equals("d")) {
                        resultIntent = new Intent(mContext, DrPendingActivity.class);
                        message = body;
                        title = title;
                    } else {

                    }
                  /*  resultIntent = new Intent(mContext, PendingJobsByLevel1AdminActivity.class);
                    message = "Appointment Information";
                    title = "Appointment update";

                   */
                    break;
                }
                case "adminResponse": {
                    resultIntent = new Intent(mContext, PublicAskByPatient.class);
                    message = body;
                    title = title;
                    break;
                }
                default: {
                    resultIntent = new Intent(mContext, SplashActivity.class);

                }
            }
            //  resultIntent = new Intent(mContext, SplashActivity.class);


            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.leaf);
            if (image != null) {
                icon = image;
            }
            // newMsgNotification(mContext,title,message,image);


            mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(false)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.drawable.leaf)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(intent, intent, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(intent);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, mBuilder.build());


            //another noti
            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), resultIntent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n = new Notification.Builder(mContext)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.leaf)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);

        } else {

// use System.currentTimeMillis() to have a unique ID for the pending intent

        }
    }

    public static void newMsgNotification(Context context, String sender, String msgBody, Bitmap bitmap) {

        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";

        int NOTIFICATION_ID = 234;


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


        if (false) {

           /*


            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            mChannel.setSound(Uri.parse("android.resource:// ukilchai.limited.com/" + R.raw.iphn),att);


            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(mChannel);
            }
            */
            NotificationManager mNotificationManager;
            NotificationCompat.Builder mBuilder;
            String NOTIFICATION_CHANNEL_ID = "10001";
            Intent resultIntent = new Intent(context, SplashActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle("sss")
                    .setContentText(msgBody)
                    .setAutoCancel(false)

                    .setContentIntent(resultPendingIntent);

            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert mNotificationManager != null;
                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
                assert mNotificationManager != null;
                mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
            } else {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.leaf)//set icon for notification
                                .setContentTitle("Notifications Example") //set title of notification
                                .setContentText("This is a notification message")//this is notification message
                                .setAutoCancel(true) // makes auto cancel of notification
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


                Intent notificationIntent = new Intent(context, SplashActivity.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //notification message will get at NotificationView
                // notificationIntent.putExtra(, "This is a notification message");

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());

            }


/*

            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + context.getPackageName() + "/raw");
            Intent resultIntent1 = new Intent(context, SplashActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(SplashActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent1 = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(sender)
                    .setContentText(sender)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msgBody))
                    .setSmallIcon(R.drawable.leaf)
                    .setLargeIcon(bitmap)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentIntent(resultPendingIntent1)
                    .setAutoCancel(true)


                    .setSound(alarmSound)
                    .setColor(context.getResources().getColor(R.color.colorPrimary));


            if (notificationManager != null) {


                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

 */


        }


    }


}