package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDoctorModel {
//DepartmentModel
@SerializedName("department_info")
@Expose
private DepartmentModel departmentModel;
@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("designation_title")
@Expose
private String designation_title;
@SerializedName("photo")
@Expose
private String photo;
@SerializedName("department")
@Expose
private Integer department;
@SerializedName("email")
@Expose
private String email;
@SerializedName("phone")
@Expose
private String phone;
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

public String getPhoto() {
return photo;
}

public void setPhoto(String photo) {
this.photo = photo;
}

public Integer getDepartment() {
return department;
}

public void setDepartment(Integer department) {
this.department = department;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public List<ChamberInfo> getChamberInfo() {
return chamberInfo;
}

public void setChamberInfo(List<ChamberInfo> chamberInfo) {
this.chamberInfo = chamberInfo;
}

    public DepartmentModel getDepartmentModel() {
        return departmentModel;
    }

    public void setDepartmentModel(DepartmentModel departmentModel) {
        this.departmentModel = departmentModel;
    }

    public String getDesignation_title() {
        return designation_title;
    }

    public void setDesignation_title(String designation_title) {
        this.designation_title = designation_title;
    }
}