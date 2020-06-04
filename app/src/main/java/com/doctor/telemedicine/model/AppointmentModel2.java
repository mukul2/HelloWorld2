package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentModel2 {

@SerializedName("id")
@Expose
private String id;
@SerializedName("problems")
@Expose
private String problems;
@SerializedName("status")
@Expose
private String status;
@SerializedName("chamber_id")
@Expose
private String chamberId;
@SerializedName("patient_id")
@Expose
private String patientId;
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

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getProblems() {
return problems;
}

public void setProblems(String problems) {
this.problems = problems;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getChamberId() {
return chamberId;
}

public void setChamberId(String chamberId) {
this.chamberId = chamberId;
}

public String getPatientId() {
return patientId;
}

public void setPatientId(String patientId) {
this.patientId = patientId;
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

}