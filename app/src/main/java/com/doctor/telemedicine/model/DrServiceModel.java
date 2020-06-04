package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrServiceModel {

@SerializedName("name")
@Expose
private String name;
@SerializedName("service_id")
@Expose
private String serviceId;
@SerializedName("price")
@Expose
private String price;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getServiceId() {
return serviceId;
}

public void setServiceId(String serviceId) {
this.serviceId = serviceId;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

}