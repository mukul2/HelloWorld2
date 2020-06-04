package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallHistoryPatient {

@SerializedName("call_time")
@Expose
private String callTime;
@SerializedName("duration")
@Expose
private String duration;
@SerializedName("dr_name")
@Expose
private String drName;
@SerializedName("photo")
@Expose
private String photo;

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

public String getDrName() {
return drName;
}

public void setDrName(String drName) {
this.drName = drName;
}

public String getPhoto() {
return photo;
}

public void setPhoto(String photo) {
this.photo = photo;
}

}