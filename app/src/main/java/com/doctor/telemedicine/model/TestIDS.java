package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestIDS {

@SerializedName("test_id")
@Expose
private String testId;

public String getTestId() {
return testId;
}

public void setTestId(String testId) {
this.testId = testId;
}

    public TestIDS(String testId) {
        this.testId = testId;
    }
}