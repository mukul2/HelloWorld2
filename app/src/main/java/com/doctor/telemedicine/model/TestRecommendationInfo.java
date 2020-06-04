package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestRecommendationInfo {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("appointment_id")
@Expose
private Integer appointmentId;
@SerializedName("test_id")
@Expose
private Integer testId;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("test_info")
@Expose
private TestInfo testInfo;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getAppointmentId() {
return appointmentId;
}

public void setAppointmentId(Integer appointmentId) {
this.appointmentId = appointmentId;
}

public Integer getTestId() {
return testId;
}

public void setTestId(Integer testId) {
this.testId = testId;
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

public TestInfo getTestInfo() {
return testInfo;
}

public void setTestInfo(TestInfo testInfo) {
this.testInfo = testInfo;
}

}