package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionRequestModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("patient_id")
@Expose
private Integer patientId;
@SerializedName("dr_id")
@Expose
private Integer drId;
@SerializedName("problem")
@Expose
private String problem;
@SerializedName("attachment")
@Expose
private String attachment;
@SerializedName("status")
@Expose
private Integer status;
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

public String getProblem() {
return problem;
}

public void setProblem(String problem) {
this.problem = problem;
}

public String getAttachment() {
return attachment;
}

public void setAttachment(String attachment) {
this.attachment = attachment;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
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