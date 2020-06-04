package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecomentationModel {

@SerializedName("appointment_id")
@Expose
private String appointmentId;
@SerializedName("address")
@Expose
private String address;
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
@SerializedName("testList")
@Expose
private List<TestName> testList = null;

public String getAppointmentId() {
return appointmentId;
}

public void setAppointmentId(String appointmentId) {
this.appointmentId = appointmentId;
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

public List<TestName> getTestList() {
return testList;
}

public void setTestList(List<TestName> testList) {
this.testList = testList;
}

}