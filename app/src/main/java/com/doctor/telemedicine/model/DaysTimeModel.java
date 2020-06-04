package com.doctor.telemedicine.model;

/**
 * Created by mukul on 4/22/2019.
 */

public class DaysTimeModel {
    String dayName;
    String start_time;
    String end_time;

    public DaysTimeModel() {
    }

    public DaysTimeModel(String dayName, String start_time, String end_time) {
        this.dayName = dayName;
        this.start_time = start_time;
        this.end_time = end_time;
    }



    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String startTime) {
        this.start_time = startTime;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEndTime(String endTime) {
        this.end_time = endTime;
    }
}
