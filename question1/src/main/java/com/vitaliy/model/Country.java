package com.vitaliy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {
    @SerializedName("country")
    private int countryId;

    @SerializedName("country_text")
    private String countryText;

    private List<String> phoneList;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryText() {
        return countryText;
    }

    public void setCountryText(String countryText) {
        this.countryText = countryText;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + countryId +
                ", countryText='" + countryText + '\'' +
                ", phoneList=" + phoneList +
                '}';
    }
}
