package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentAddResponse {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("appointment_id")
@Expose
private Integer appointmentId;
@SerializedName("message")
@Expose
private String message;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public Integer getAppointmentId() {
return appointmentId;
}

public void setAppointmentId(Integer appointmentId) {
this.appointmentId = appointmentId;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}