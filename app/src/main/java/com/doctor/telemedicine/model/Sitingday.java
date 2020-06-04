package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sitingday {

@SerializedName("id")
@Expose
private String id;
@SerializedName("chamber_id")
@Expose
private String chamberId;
@SerializedName("day")
@Expose
private String day;
@SerializedName("start_time")
@Expose
private String startTime;
@SerializedName("end_time")
@Expose
private String endTime;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getChamberId() {
return chamberId;
}

public void setChamberId(String chamberId) {
this.chamberId = chamberId;
}

public String getDay() {
return day;
}

public void setDay(String day) {
this.day = day;
}

public String getStartTime() {
return startTime;
}

public void setStartTime(String startTime) {
this.startTime = startTime;
}

public String getEndTime() {
return endTime;
}

public void setEndTime(String endTime) {
this.endTime = endTime;
}

}