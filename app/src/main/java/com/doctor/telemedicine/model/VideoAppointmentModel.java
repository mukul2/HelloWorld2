package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoAppointmentModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("patient_id")
@Expose
private Integer patientId;
@SerializedName("doctor_id")
@Expose
private Integer doctorId;
@SerializedName("payment_status")
@Expose
private Integer paymentStatus;
@SerializedName("payment_details")
@Expose
private String paymentDetails;
@SerializedName("appointment_status")
@Expose
private Integer appointmentStatus;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("dr_info")
@Expose
private DrInfo drInfo;
@SerializedName("patient_info")
@Expose
private PatientInfo patientInfo;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getPatientId() {
return patientId;
}

public void setPatientId(Integer patientId) {
this.patientId = patientId;
}

public Integer getDoctorId() {
return doctorId;
}

public void setDoctorId(Integer doctorId) {
this.doctorId = doctorId;
}

public Integer getPaymentStatus() {
return paymentStatus;
}

public void setPaymentStatus(Integer paymentStatus) {
this.paymentStatus = paymentStatus;
}

public String getPaymentDetails() {
return paymentDetails;
}

public void setPaymentDetails(String paymentDetails) {
this.paymentDetails = paymentDetails;
}

public Integer getAppointmentStatus() {
return appointmentStatus;
}

public void setAppointmentStatus(Integer appointmentStatus) {
this.appointmentStatus = appointmentStatus;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

public DrInfo getDrInfo() {
return drInfo;
}

public void setDrInfo(DrInfo drInfo) {
this.drInfo = drInfo;
}

public PatientInfo getPatientInfo() {
return patientInfo;
}

public void setPatientInfo(PatientInfo patientInfo) {
this.patientInfo = patientInfo;
}

}