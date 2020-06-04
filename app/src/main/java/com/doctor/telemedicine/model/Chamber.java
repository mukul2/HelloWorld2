package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chamber {

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
@SerializedName("days")
@Expose
private String days;
@SerializedName("visit_fee")
@Expose
private String visitFee;

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

public String getDays() {
return days;
}

public void setDays(String days) {
this.days = days;
}

public String getVisitFee() {
return visitFee;
}

public void setVisitFee(String visitFee) {
this.visitFee = visitFee;
}

}