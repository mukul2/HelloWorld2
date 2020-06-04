package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchProfileResponse {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("profile")
@Expose
private UserInfo profile;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public UserInfo getProfile() {
return profile;
}

public void setProfile(UserInfo profile) {
this.profile = profile;
}

}