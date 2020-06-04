package com.doctor.telemedicine.model;

/**
 * Created by mukul on 3/10/2019.
 */

public class SpacialistModel {
    String name;
    boolean isSelected;


    public SpacialistModel(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public SpacialistModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
