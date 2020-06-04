package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentModel {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("appointment_id")
    @Expose
    private String appointment_id;
    @SerializedName("problems")
    @Expose
    private String problems;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("dr_name")
    @Expose
    private String drName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("appointment_for")
    @Expose
    private String appointmentFor;
    @SerializedName("spacialist")
    @Expose
    private String spacialist;

    public String getSpacialist() {
        return spacialist;
    }

    public void setSpacialist(String spacialist) {
        this.spacialist = spacialist;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppointmentFor() {
        return appointmentFor;
    }

    public void setAppointmentFor(String appointmentFor) {
        this.appointmentFor = appointmentFor;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }
}