package com.doctor.telemedicine.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dr_name")
    @Expose
    private String drName;
    @SerializedName("dr_id")
    @Expose
    private String dr_id;
    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("lastDegree")
    @Expose
    private String lastDegree;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("specialist")
    @Expose
    private String specialist;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("visit_fee")
    @Expose
    private String visitFee;
    @SerializedName("days")
    @Expose
    private List<Day> days = null;

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDr_id() {
        return dr_id;
    }

    public void setDr_id(String dr_id) {
        this.dr_id = dr_id;
    }
}