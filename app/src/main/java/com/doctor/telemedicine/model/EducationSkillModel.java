package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationSkillModel {

@SerializedName("id")
@Expose
private String id;
@SerializedName("dr_id")
@Expose
private String drId;
@SerializedName("education")
@Expose
private String education;
@SerializedName("skill")
@Expose
private String skill;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getDrId() {
return drId;
}

public void setDrId(String drId) {
this.drId = drId;
}

public String getEducation() {
return education;
}

public void setEducation(String education) {
this.education = education;
}

public String getSkill() {
return skill;
}

public void setSkill(String skill) {
this.skill = skill;
}

}