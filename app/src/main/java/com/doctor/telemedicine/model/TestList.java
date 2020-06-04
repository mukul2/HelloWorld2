package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestList {

@SerializedName("test_name")
@Expose
private String testName;
@SerializedName("test_type")
@Expose
private String testType;

public String getTestName() {
return testName;
}

public void setTestName(String testName) {
this.testName = testName;
}

public String getTestType() {
return testType;
}

public void setTestType(String testType) {
this.testType = testType;
}

}