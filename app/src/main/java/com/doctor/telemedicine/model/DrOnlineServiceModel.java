package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrOnlineServiceModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("online_service_id")
@Expose
private Integer onlineServiceId;
@SerializedName("fees_per_unit")
@Expose
private Integer fees_per_unit;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("service_name_info")
@Expose
private ServiceNameInfo serviceNameInfo;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getOnlineServiceId() {
return onlineServiceId;
}

public void setOnlineServiceId(Integer onlineServiceId) {
this.onlineServiceId = onlineServiceId;
}

public ServiceNameInfo getServiceNameInfo() {
return serviceNameInfo;
}

public void setServiceNameInfo(ServiceNameInfo serviceNameInfo) {
this.serviceNameInfo = serviceNameInfo;
}

    public Integer getFees_per_unit() {
        return fees_per_unit;
    }

    public void setFees_per_unit(Integer fees_per_unit) {
        this.fees_per_unit = fees_per_unit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}