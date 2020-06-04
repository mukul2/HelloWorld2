package com.doctor.telemedicine.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMsgModel {

    @SerializedName("doctorID")
    @Expose
    private String doctorID;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("doctorPhoto")
    @Expose
    private String doctorPhoto;
    @SerializedName("messageBody")
    @Expose
    private String messageBody;
    @SerializedName("partnerID")
    @Expose
    private String partnerID;
    @SerializedName("partnerName")
    @Expose
    private String partnerName;
    @SerializedName("patientID")
    @Expose
    private String patientID;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("patientPhoto")
    @Expose
    private String patientPhoto;

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhoto() {
        return doctorPhoto;
    }

    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhoto() {
        return patientPhoto;
    }

    public void setPatientPhoto(String patientPhoto) {
        this.patientPhoto = patientPhoto;
    }

}