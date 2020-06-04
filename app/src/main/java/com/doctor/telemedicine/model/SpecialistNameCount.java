package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialistNameCount {

@SerializedName("name")
@Expose
private String name;
@SerializedName("count")
@Expose
private String count;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCount() {
return count;
}

public void setCount(String count) {
this.count = count;
}

}