package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiseasesModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("patient_id")
@Expose
private Integer patientId;
@SerializedName("disease_name")
@Expose
private String diseaseName;
@SerializedName("first_notice_date")
@Expose
private String firstNoticeDate;
@SerializedName("status")
@Expose
private String status;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;

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

public String getDiseaseName() {
return diseaseName;
}

public void setDiseaseName(String diseaseName) {
this.diseaseName = diseaseName;
}

public String getFirstNoticeDate() {
return firstNoticeDate;
}

public void setFirstNoticeDate(String firstNoticeDate) {
this.firstNoticeDate = firstNoticeDate;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
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

}