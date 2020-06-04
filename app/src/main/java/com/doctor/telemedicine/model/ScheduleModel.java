package com.doctor.telemedicine.model;

/**
 * Created by mukul on 3/14/2019.
 */

public class ScheduleModel {
    String day,time;

    public ScheduleModel() {
    }

    public ScheduleModel(String day, String time) {
        this.day = day;
        this.time = time;
    }

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
}
