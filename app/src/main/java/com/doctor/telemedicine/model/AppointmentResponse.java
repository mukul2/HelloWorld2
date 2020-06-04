package com.doctor.telemedicine.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentResponse {

    @SerializedName("notConfirmed")
    @Expose
    private List<AppointmentModel> notConfirmed = null;
    @SerializedName("confirmed")
    @Expose
    private List<AppointmentModel> confirmed = null;

    @SerializedName("notification")
    @Expose
    private List<RecomentationModel> notification = null;

    public List<RecomentationModel> getNotification() {
        return notification;
    }

    public void setNotification(List<RecomentationModel> notification) {
        this.notification = notification;
    }

    public List<AppointmentModel> getNotConfirmed() {
        return notConfirmed;
    }

    public void setNotConfirmed(List<AppointmentModel> notConfirmed) {
        this.notConfirmed = notConfirmed;
    }

    public List<AppointmentModel> getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(List<AppointmentModel> confirmed) {
        this.confirmed = confirmed;
    }

}