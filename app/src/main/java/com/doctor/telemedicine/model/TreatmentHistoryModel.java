package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TreatmentHistoryModel {

@SerializedName("id")
@Expose
private String id;
@SerializedName("appointment_id")
@Expose
private String appointmentId;
@SerializedName("chamber_id")
@Expose
private String chamberId;
@SerializedName("dr_id")
@Expose
private String drId;
@SerializedName("dr_name")
@Expose
private String drName;
@SerializedName("patient_id")
@Expose
private String patientId;
@SerializedName("patient_name")
@Expose
private String patientName;
@SerializedName("comment")
@Expose
private String comment;
@SerializedName("attachment")
@Expose
private String attachment;
@SerializedName("fees")
@Expose
private String fees;
@SerializedName("posted")
@Expose
private String posted;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getAppointmentId() {
return appointmentId;
}

public void setAppointmentId(String appointmentId) {
this.appointmentId = appointmentId;
}

public String getChamberId() {
return chamberId;
}

public void setChamberId(String chamberId) {
this.chamberId = chamberId;
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

public String getPatientId() {
return patientId;
}

public void setPatientId(String patientId) {
this.patientId = patientId;
}

public String getPatientName() {
return patientName;
}

public void setPatientName(String patientName) {
this.patientName = patientName;
}

public String getComment() {
return comment;
}

public void setComment(String comment) {
this.comment = comment;
}

public String getAttachment() {
return attachment;
}

public void setAttachment(String attachment) {
this.attachment = attachment;
}

public String getFees() {
return fees;
}

public void setFees(String fees) {
this.fees = fees;
}

public String getPosted() {
return posted;
}

public void setPosted(String posted) {
this.posted = posted;
}

}