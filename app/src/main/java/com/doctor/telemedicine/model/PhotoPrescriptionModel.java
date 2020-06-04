package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoPrescriptionModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("prescription_id")
@Expose
private Integer prescriptionId;
@SerializedName("file")
@Expose
private String file;
@SerializedName("title")
@Expose
private String title;

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

public String getFile() {
return file;
}

public void setFile(String file) {
this.file = file;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

}