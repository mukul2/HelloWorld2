package com.doctor.telemedicine.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionReviewModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("patient_id")
    @Expose
    private Integer patientId;
    @SerializedName("dr_id")
    @Expose
    private Integer drId;
    @SerializedName("old_prescription_id")
    @Expose
    private Integer oldPrescriptionId;
    @SerializedName("new_prescription_id")
    @Expose
    private Integer new_prescription_id;
    @SerializedName("is_reviewed")
    @Expose
    private Integer isReviewed;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("dr_comment")
    @Expose
    private String dr_comment;
    @SerializedName("patient_comment")
    @Expose
    private String patient_comment;
    @SerializedName("medicine_info")
    @Expose
    private List<MedicineInfo> medicineInfo = null;
    @SerializedName("patient_info")
    @Expose
    private PatientInfo patientInfo;
    @SerializedName("dr_info")
    @Expose
    private DrInfo drInfo;

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

    public Integer getOldPrescriptionId() {
        return oldPrescriptionId;
    }

    public void setOldPrescriptionId(Integer oldPrescriptionId) {
        this.oldPrescriptionId = oldPrescriptionId;
    }

    public Integer getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(Integer isReviewed) {
        this.isReviewed = isReviewed;
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

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public DrInfo getDrInfo() {
        return drInfo;
    }

    public void setDrInfo(DrInfo drInfo) {
        this.drInfo = drInfo;
    }

    public String getPatient_comment() {
        return patient_comment;
    }

    public void setPatient_comment(String patient_comment) {
        this.patient_comment = patient_comment;
    }

    public String getDr_comment() {
        return dr_comment;
    }

    public void setDr_comment(String dr_comment) {
        this.dr_comment = dr_comment;
    }

    public Integer getNew_prescription_id() {
        return new_prescription_id;
    }

    public void setNew_prescription_id(Integer new_prescription_id) {
        this.new_prescription_id = new_prescription_id;
    }
}