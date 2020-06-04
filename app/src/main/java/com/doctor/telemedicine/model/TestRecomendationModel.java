package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestRecomendationModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("patient_id")
@Expose
private Integer patientId;
@SerializedName("dr_id")
@Expose
private Integer drId;
@SerializedName("dr_info")
@Expose
private DrInfo dr_info;
@SerializedName("problems")
@Expose
private String problems;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("name")
@Expose
private String name;
@SerializedName("chamber_id")
@Expose
private Integer chamberId;
@SerializedName("date")
@Expose
private String date;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("test_recommendation_info")
@Expose
private List<TestRecommendationInfo> testRecommendationInfo = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getPatientId() {
return patientId;
}

public void setPatientId(Integer patientId) {
this.patientId = patientId;
}

public Integer getDrId() {
return drId;
}

public void setDrId(Integer drId) {
this.drId = drId;
}

public String getProblems() {
return problems;
}

public void setProblems(String problems) {
this.problems = problems;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Integer getChamberId() {
return chamberId;
}

public void setChamberId(Integer chamberId) {
this.chamberId = chamberId;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
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

public List<TestRecommendationInfo> getTestRecommendationInfo() {
return testRecommendationInfo;
}

public void setTestRecommendationInfo(List<TestRecommendationInfo> testRecommendationInfo) {
this.testRecommendationInfo = testRecommendationInfo;
}

    public DrInfo getDr_info() {
        return dr_info;
    }

    public void setDr_info(DrInfo dr_info) {
        this.dr_info = dr_info;
    }
}