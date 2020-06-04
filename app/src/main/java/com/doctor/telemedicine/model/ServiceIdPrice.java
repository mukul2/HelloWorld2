package com.doctor.telemedicine.model;

/**
 * Created by mukul on 5/31/2019.
 */

public class ServiceIdPrice {
    String service_id,price;

    public ServiceIdPrice(String service_id, String price) {
        this.service_id = service_id;
        this.price = price;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
