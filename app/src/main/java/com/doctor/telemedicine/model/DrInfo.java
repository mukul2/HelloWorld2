package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrInfo {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("photo")
@Expose
private String photo;
@SerializedName("department_info")
@Expose
private DepartmentModel department_info=null;

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

    public DepartmentModel getDepartment_info() {
        return department_info;
    }

    public void setDepartment_info(DepartmentModel department_info) {
        this.department_info = department_info;
    }
}