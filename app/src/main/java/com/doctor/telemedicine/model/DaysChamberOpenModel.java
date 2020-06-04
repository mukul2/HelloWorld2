package com.doctor.telemedicine.model;

public class DaysChamberOpenModel {
    boolean isSelected=false;
    int date;
    int day;

    public DaysChamberOpenModel(boolean isSelected, int date, int day) {
        this.isSelected = isSelected;
        this.date = date;
        this.day = day;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
