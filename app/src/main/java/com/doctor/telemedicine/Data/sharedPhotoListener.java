package com.doctor.telemedicine.Data;

import android.net.Uri;

import java.util.ArrayList;

public class sharedPhotoListener {
    public static PhotoPickedListener pListener;
    public static PhotoPickedListenerUri pListenerUri;

    public interface PhotoPickedListener {
        public void onPicSelected(ArrayList<String> data);
    }
    public interface PhotoPickedListenerUri {
        public void onPicSelected(Uri data);
    }

    public static void setpListenerUri(PhotoPickedListenerUri e) {
        sharedPhotoListener.pListenerUri = e;
    }

    public static void setPhotoSelectListener(PhotoPickedListener lis) {
        pListener = lis;


    }
}
