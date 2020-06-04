package com.doctor.telemedicine.Data;

import com.doctor.telemedicine.api.ApiListener;

/**
 * Created by mukul on 3/20/2019.
 */

public class lis {
    public static ApiListener.dataDownloadListener Confirmedlistener;
    public static ApiListener.dataDownloadListener Pendinglistener;
    public  static void setConfirmedlistener(ApiListener.dataDownloadListener liste) {
        Confirmedlistener = liste;
    }
    public  static void setPendinglistener(ApiListener.dataDownloadListener liste) {
        Pendinglistener = liste;
    }
}
