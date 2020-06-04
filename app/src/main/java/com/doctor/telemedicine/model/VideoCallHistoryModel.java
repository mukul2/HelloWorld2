package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCallHistoryModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("dr_id")
@Expose
private Integer drId;
@SerializedName("patient_id")
@Expose
private Integer patientId;
@SerializedName("call_time")
@Expose
private String callTime;
@SerializedName("duration")
@Expose
private String duration;
@SerializedName("caller_id")
@Expose
private Integer callerId;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("patient_info")
@Expose
private PatientInfo patientInfo;
@SerializedName("dr_info")
@Expose
private DrInfoNoDept drInfo;

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

public String getCallTime() {
return callTime;
}

public void setCallTime(String callTime) {
this.callTime = callTime;
}

public String getDuration() {
return duration;
}

public void setDuration(String duration) {
this.duration = duration;
}

public Integer getCallerId() {
return callerId;
}

public void setCallerId(Integer callerId) {
this.callerId = callerId;
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

public PatientInfo getPatientInfo() {
return patientInfo;
}

public void setPatientInfo(PatientInfo patientInfo) {
this.patientInfo = patientInfo;
}

public DrInfoNoDept getDrInfo() {
return drInfo;
}

public void setDrInfo(DrInfoNoDept drInfo) {
this.drInfo = drInfo;
}

}