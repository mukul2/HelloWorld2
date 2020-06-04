package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkillInfo {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("body")
@Expose
private String body;
@SerializedName("dr_id")
@Expose
private Integer drId;
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

public String getBody() {
return body;
}

public void setBody(String body) {
this.body = body;
}

public Integer getDrId() {
return drId;
}

public void setDrId(Integer drId) {
this.drId = drId;
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