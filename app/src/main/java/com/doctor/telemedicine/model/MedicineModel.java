package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("manufacture")
@Expose
private String manufacture;
@SerializedName("type")
@Expose
private String type;
@SerializedName("description")
@Expose
private String description;
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

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getManufacture() {
return manufacture;
}

public void setManufacture(String manufacture) {
this.manufacture = manufacture;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
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