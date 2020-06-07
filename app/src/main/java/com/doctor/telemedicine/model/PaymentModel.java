package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("dr_id")
@Expose
private Integer drId;
@SerializedName("patient_id")
@Expose
private Integer patientId;
@SerializedName("fees")
@Expose
private float fees;
@SerializedName("service_id")
@Expose
private Integer serviceId;
@SerializedName("service_details")
@Expose
private String serviceDetails;
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

public Integer getDrId() {
return drId;
}

public void setDrId(Integer drId) {
this.drId = drId;
}

public Integer getPatientId() {
return patientId;
}

public void setPatientId(Integer patientId) {
this.patientId = patientId;
}

public float getFees() {
return fees;
}

public void setFees(Integer fees) {
this.fees = fees;
}

public Integer getServiceId() {
return serviceId;
}

public void setServiceId(Integer serviceId) {
this.serviceId = serviceId;
}

public String getServiceDetails() {
return serviceDetails;
}

public void setServiceDetails(String serviceDetails) {
this.serviceDetails = serviceDetails;
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