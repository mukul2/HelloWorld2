package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicInfoModel {

@SerializedName("hospitalList")
@Expose
private List<String> hospitalList = null;
@SerializedName("spacialist")
@Expose
private List<SpecialistNameCount> spacialist = null;

public List<String> getHospitalList() {
return hospitalList;
}

public void setHospitalList(List<String> hospitalList) {
this.hospitalList = hospitalList;
}

public List<SpecialistNameCount> getSpacialist() {
return spacialist;
}

public void setSpacialist(List<SpecialistNameCount> spacialist) {
this.spacialist = spacialist;
}

}