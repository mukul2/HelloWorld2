package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineDoctorModel {

    @SerializedName("dr_id")
    @Expose
    private String drId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("dr_service")
    @Expose
    private List<DrService> drService = null;

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<DrService> getDrService() {
        return drService;
    }

    public void setDrService(List<DrService> drService) {
        this.drService = drService;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}