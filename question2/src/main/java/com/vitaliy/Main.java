package com.vitaliy;

import com.google.gson.*;
import com.vitaliy.util.ParseUtil;
import com.vitaliy.util.impl.ParseUtilImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        ParseUtil parseUtil = new ParseUtilImpl();

        URL urlCountry = new URL("https://onlinesim.ru/price-list-data?type=receive");
        String jsonCountry = parseUtil.getJsonFromURL(urlCountry);

        JsonObject rootJson = JsonParser.parseString(jsonCountry).getAsJsonObject();
        JsonObject jsonCountries = parseUtil.createJsonObjectFromJsonByKey(jsonCountry, "countries");

        Map<String, Map<String, String>> priceMap = new HashMap<>();

        for (Map.Entry<String, JsonElement> elementEntry : jsonCountries.entrySet()) {
            JsonObject jsonServicePrice = rootJson.getAsJsonObject("list").getAsJsonObject(elementEntry.getKey());

            Gson gson = new Gson();
            Map<String, String> map = gson.fromJson(jsonServicePrice, Map.class);

            priceMap.put(elementEntry.getValue().toString(), map);

            System.out.println(priceMap);
        }
    }
}
