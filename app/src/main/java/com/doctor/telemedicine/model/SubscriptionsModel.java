package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionsModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("patient_id")
@Expose
private Integer patientId;
@SerializedName("dr_id")
@Expose
private Integer drId;
@SerializedName("number_of_months")
@Expose
private Integer numberOfMonths;
@SerializedName("starts")
@Expose
private String starts;
@SerializedName("ends")
@Expose
private String ends;
@SerializedName("payment_details")
@Expose
private String paymentDetails;
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

public Integer getDrId() {
return drId;
}

public void setDrId(Integer drId) {
this.drId = drId;
}

public Integer getNumberOfMonths() {
return numberOfMonths;
}

public void setNumberOfMonths(Integer numberOfMonths) {
this.numberOfMonths = numberOfMonths;
}

public String getStarts() {
return starts;
}

public void setStarts(String starts) {
this.starts = starts;
}

public String getEnds() {
return ends;
}

public void setEnds(String ends) {
this.ends = ends;
}

public String getPaymentDetails() {
return paymentDetails;
}

public void setPaymentDetails(String paymentDetails) {
this.paymentDetails = paymentDetails;
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