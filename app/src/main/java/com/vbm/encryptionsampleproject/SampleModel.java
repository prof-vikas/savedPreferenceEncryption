package com.vbm.encryptionsampleproject;

public class SampleModel {

    private String value;
    private String encryptedValue;

    public SampleModel(String value, String encryptedValue) {
        this.value = value;
        this.encryptedValue = encryptedValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEncryptedValue() {
        return encryptedValue;
    }

    public void setEncryptedValue(String encryptedValue) {
        this.encryptedValue = encryptedValue;
    }
}
