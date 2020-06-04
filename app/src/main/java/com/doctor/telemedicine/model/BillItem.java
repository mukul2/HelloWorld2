package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillItem {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("description")
@Expose
private String description;
@SerializedName("service_id")
@Expose
private Integer serviceId;
@SerializedName("rate")
@Expose
private Integer rate;
@SerializedName("unit")
@Expose
private Integer unit;
@SerializedName("total")
@Expose
private Integer total;
@SerializedName("year")
@Expose
private Integer year;
@SerializedName("month")
@Expose
private Integer month;
@SerializedName("date")
@Expose
private Integer date;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Integer getServiceId() {
return serviceId;
}

public void setServiceId(Integer serviceId) {
this.serviceId = serviceId;
}

public Integer getRate() {
return rate;
}

public void setRate(Integer rate) {
this.rate = rate;
}

public Integer getUnit() {
return unit;
}

public void setUnit(Integer unit) {
this.unit = unit;
}

public Integer getTotal() {
return total;
}

public void setTotal(Integer total) {
this.total = total;
}

public Integer getYear() {
return year;
}

public void setYear(Integer year) {
this.year = year;
}

public Integer getMonth() {
return month;
}

public void setMonth(Integer month) {
this.month = month;
}

public Integer getDate() {
return date;
}

public void setDate(Integer date) {
this.date = date;
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

}