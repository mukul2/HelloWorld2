package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCallModel {

@SerializedName("id")
@Expose
private String id;
@SerializedName("dr_id")
@Expose
private String drId;
@SerializedName("dr_name")
@Expose
private String drName;
@SerializedName("hospital_name")
@Expose
private String hospitalName;
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
@SerializedName("lastDegree")
@Expose
private String lastDegree;
@SerializedName("visit_fee")
@Expose
private String visitFee;
@SerializedName("callTimes")
@Expose
private List<CallTime> callTimes = null;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getDrId() {
return drId;
}

public void setDrId(String drId) {
this.drId = drId;
}

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

public String getLastDegree() {
return lastDegree;
}

public void setLastDegree(String lastDegree) {
this.lastDegree = lastDegree;
}

public String getVisitFee() {
return visitFee;
}

public void setVisitFee(String visitFee) {
this.visitFee = visitFee;
}

public List<CallTime> getCallTimes() {
return callTimes;
}

public void setCallTimes(List<CallTime> callTimes) {
this.callTimes = callTimes;
}

}