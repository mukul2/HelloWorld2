package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrService {

@SerializedName("service_id")
@Expose
private String serviceId;
@SerializedName("name")
@Expose
private String name;

public String getServiceId() {
return serviceId;
}

public void setServiceId(String serviceId) {
this.serviceId = serviceId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

}