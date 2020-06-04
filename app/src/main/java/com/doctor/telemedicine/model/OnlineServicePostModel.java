package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineServicePostModel {

@SerializedName("online_service_id")
@Expose
private Integer onlineServiceId;

public Integer getOnlineServiceId() {
return onlineServiceId;
}

public void setOnlineServiceId(Integer onlineServiceId) {
this.onlineServiceId = onlineServiceId;
}

}