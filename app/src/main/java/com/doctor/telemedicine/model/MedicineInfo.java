package com.doctor.telemedicine.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("prescription_id")
    @Expose
    private Integer prescriptionId;
    @SerializedName("isAfterMeal")
    @Expose
    private Integer isAfterMeal;
    @SerializedName("dose")
    @Expose
    private String dose;
    @SerializedName("medicine_id")
    @Expose
    private Integer medicineId;
    @SerializedName("duration_type")
    @Expose
    private String durationType;
    @SerializedName("duration_length")
    @Expose
    private Integer durationLength;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("medicine_name_info")
    @Expose
    private MedicineNameInfo medicineNameInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public Integer getDurationLength() {
        return durationLength;
    }

    public void setDurationLength(Integer durationLength) {
        this.durationLength = durationLength;
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

    public MedicineNameInfo getMedicineNameInfo() {
        return medicineNameInfo;
    }

    public void setMedicineNameInfo(MedicineNameInfo medicineNameInfo) {
        this.medicineNameInfo = medicineNameInfo;
    }

    public Integer getIsAfterMeal() {
        return isAfterMeal;
    }

    public void setIsAfterMeal(Integer isAfterMeal) {
        this.isAfterMeal = isAfterMeal;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}