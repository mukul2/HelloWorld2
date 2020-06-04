package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

@SerializedName("id")
@Expose
private String id;
@SerializedName("dr_name")
@Expose
private String drName;
@SerializedName("type")
@Expose
private String type;
@SerializedName("gender")
@Expose
private String gender;
@SerializedName("last_education_degree")
@Expose
private String lastEducationDegree;
@SerializedName("hospital_name")
@Expose
private String hospitalName;
@SerializedName("password")
@Expose
private String password;
@SerializedName("email")
@Expose
private String email;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("photo")
@Expose
private String photo;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getDrName() {
return drName;
}

public void setDrName(String drName) {
this.drName = drName;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getLastEducationDegree() {
return lastEducationDegree;
}

public void setLastEducationDegree(String lastEducationDegree) {
this.lastEducationDegree = lastEducationDegree;
}

public String getHospitalName() {
return hospitalName;
}

public void setHospitalName(String hospitalName) {
this.hospitalName = hospitalName;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getPhoto() {
return photo;
}

public void setPhoto(String photo) {
this.photo = photo;
}

}