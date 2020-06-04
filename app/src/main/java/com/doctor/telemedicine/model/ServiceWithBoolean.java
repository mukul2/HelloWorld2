package com.doctor.telemedicine.model;

/**
 * Created by mukul on 5/25/2019.
 */

public class ServiceWithBoolean {
    boolean isSelected;
    String fees ;
    ServiceNameInfo serviceName;

    public ServiceWithBoolean() {
    }


    public ServiceWithBoolean(boolean isSelected, String fees, ServiceNameInfo serviceName) {
        this.isSelected = isSelected;
        this.fees = fees;
        this.serviceName = serviceName;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ServiceNameInfo getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceNameInfo serviceName) {
        this.serviceName = serviceName;
    }
}
