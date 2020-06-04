package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChamberInfo {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("dr_id")
@Expose
private Integer drId;
@SerializedName("chamber_name")
@Expose
private String chamberName;
@SerializedName("address")
@Expose
private String address;
@SerializedName("fee")
@Expose
private Integer fee;
@SerializedName("follow_up_fee")
@Expose
private Integer followUpFee;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("chamber_days")
@Expose
private List<ChamberDay> chamberDays = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getDrId() {
return drId;
}

public void setDrId(Integer drId) {
this.drId = drId;
}

public String getChamberName() {
return chamberName;
}

public void setChamberName(String chamberName) {
this.chamberName = chamberName;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public Integer getFee() {
return fee;
}

public void setFee(Integer fee) {
this.fee = fee;
}

public Integer getFollowUpFee() {
return followUpFee;
}

public void setFollowUpFee(Integer followUpFee) {
this.followUpFee = followUpFee;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

public List<ChamberDay> getChamberDays() {
return chamberDays;
}

public void setChamberDays(List<ChamberDay> chamberDays) {
this.chamberDays = chamberDays;
}

}