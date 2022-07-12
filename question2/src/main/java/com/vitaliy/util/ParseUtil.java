package com.vitaliy.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URL;

public interface ParseUtil {
    String getJsonFromURL(URL url);

    JsonObject createJsonObjectFromJsonByKey(String json, String arrayKey);

}
