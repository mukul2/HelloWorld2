package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("message")
@Expose
private String message;
@SerializedName("seen")
@Expose
private Integer seen;
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

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getSeen() {
return seen;
}

public void setSeen(Integer seen) {
this.seen = seen;
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