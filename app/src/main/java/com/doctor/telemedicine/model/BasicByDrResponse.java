package com.doctor.telemedicine.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicByDrResponse {

@SerializedName("test_names")
@Expose
private List<TestName> testNames = null;
@SerializedName("service_names")
@Expose
private List<ServiceName> serviceNames = null;

public List<TestName> getTestNames() {
return testNames;
}

public void setTestNames(List<TestName> testNames) {
this.testNames = testNames;
}

public List<ServiceName> getServiceNames() {
return serviceNames;
}

public void setServiceNames(List<ServiceName> serviceNames) {
this.serviceNames = serviceNames;
}

}