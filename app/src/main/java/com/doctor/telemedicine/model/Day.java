package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

@SerializedName("day")
@Expose
private String day;
@SerializedName("time")
@Expose
private String time;

public String getDay() {
return day;
}

public void setDay(String day) {
this.day = day;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

    public Day(String day, String time) {
        this.day = day;
        this.time = time;
    }
}