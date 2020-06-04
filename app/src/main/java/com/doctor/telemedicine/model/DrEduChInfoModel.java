package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrEduChInfoModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("skill_info")
@Expose
private List<SkillInfo> skillInfo = null;
@SerializedName("education_info")
@Expose
private List<EducationInfo> educationInfo = null;
@SerializedName("chamber_info")
@Expose
private List<ChamberInfo> chamberInfo = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public List<SkillInfo> getSkillInfo() {
return skillInfo;
}

public void setSkillInfo(List<SkillInfo> skillInfo) {
this.skillInfo = skillInfo;
}

public List<EducationInfo> getEducationInfo() {
return educationInfo;
}

public void setEducationInfo(List<EducationInfo> educationInfo) {
this.educationInfo = educationInfo;
}

public List<ChamberInfo> getChamberInfo() {
return chamberInfo;
}

public void setChamberInfo(List<ChamberInfo> chamberInfo) {
this.chamberInfo = chamberInfo;
}

}