package com.vitaliy.util;

import com.google.gson.JsonArray;
import com.vitaliy.model.Country;

import java.net.URL;
import java.util.List;

public interface ParseUtil {
    String getJsonFromURL(URL url);

    JsonArray createJsonArrayFromJsonByKey(String json, String arrayKey);

    List<Country> createCountryList(JsonArray jsonArray);

    List<String> createPhoneList(JsonArray jsonArray);
}
