package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineDoctorsServiceInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("doctor_id")
    @Expose
    private Integer doctorId;
    @SerializedName("online_service_id")
    @Expose
    private Integer onlineServiceId;
    @SerializedName("fees_per_unit")
    @Expose
    private Integer fees_per_unit;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("service_name_info")
    @Expose
    private ServiceNameInfo serviceNameInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getOnlineServiceId() {
        return onlineServiceId;
    }

    public void setOnlineServiceId(Integer onlineServiceId) {
        this.onlineServiceId = onlineServiceId;
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

    public ServiceNameInfo getServiceNameInfo() {
        return serviceNameInfo;
    }

    public void setServiceNameInfo(ServiceNameInfo serviceNameInfo) {
        this.serviceNameInfo = serviceNameInfo;
    }

    public Integer getFees_per_unit() {
        return fees_per_unit;
    }

    public void setFees_per_unit(Integer fees_per_unit) {
        this.fees_per_unit = fees_per_unit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}