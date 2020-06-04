package com.doctor.telemedicine.model;

/**
 * Created by mukul on 6/5/2019.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrChamberResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("specialist")
    @Expose
    private String specialist;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("visit_fee")
    @Expose
    private String visitFee;
    @SerializedName("days")
    @Expose
    private List<Sitingday> sitingdays = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVisitFee() {
        return visitFee;
    }

    public void setVisitFee(String visitFee) {
        this.visitFee = visitFee;
    }

    public List<Sitingday> getSitingdays() {
        return sitingdays;
    }

    public void setSitingdays(List<Sitingday> sitingdays) {
        this.sitingdays = sitingdays;
    }

}