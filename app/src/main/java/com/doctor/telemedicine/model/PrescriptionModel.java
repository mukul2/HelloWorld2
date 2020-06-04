package com.doctor.telemedicine.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("patient_id")
    @Expose
    private Integer patientId;
    @SerializedName("dr_id")
    @Expose
    private Integer drId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("diseases_name")
    @Expose
    private String diseases_name;
    @SerializedName("medicine_info")
    @Expose
    private List<MedicineInfo> medicineInfo = null;
    @SerializedName("attachment")
    @Expose
    private List<PhotoPrescriptionModel> attachment = null;
    @SerializedName("dr_info")
    @Expose
    private DrInfo drInfo;
    @SerializedName("patient_info")
    @Expose
    private PatientInfo patientInfo;

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

    public List<MedicineInfo> getMedicineInfo() {
        return medicineInfo;
    }

    public void setMedicineInfo(List<MedicineInfo> medicineInfo) {
        this.medicineInfo = medicineInfo;
    }

    public DrInfo getDrInfo() {
        return drInfo;
    }

    public void setDrInfo(DrInfo drInfo) {
        this.drInfo = drInfo;
    }

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public String getDiseases_name() {
        return diseases_name;
    }

    public void setDiseases_name(String diseases_name) {
        this.diseases_name = diseases_name;
    }

    public List<PhotoPrescriptionModel> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<PhotoPrescriptionModel> attachment) {
        this.attachment = attachment;
    }
}