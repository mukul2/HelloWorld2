package com.doctor.telemedicine.model;

/**
 * Created by mukul on 3/22/2019.
 */

public class testSelectedModel {
    boolean isSelected;
    TestModel testModel;

    public testSelectedModel() {
    }

    public testSelectedModel(boolean isSelected, TestModel testModel) {
        this.isSelected = isSelected;
        this.testModel = testModel;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public TestModel getTestModel() {
        return testModel;
    }

    public void setTestModel(TestModel testModel) {
        this.testModel = testModel;
    }
}
