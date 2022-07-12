package com.vitaliy.util.impl;

import com.google.gson.*;
import com.vitaliy.model.Country;
import com.vitaliy.util.ParseUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParseUtilImpl implements ParseUtil {
    @Override
    public String getJsonFromURL(URL url) {
        StringBuilder inline = new StringBuilder();

        try {
            checkConnection(url);

            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                inline.append(sc.nextLine());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return inline.toString();
    }

    @Override
    public JsonArray createJsonArrayFromJsonByKey(String json, String arrayKey) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray(arrayKey);

            if (jsonArray.isEmpty()) {
                throw new JsonParseException("Not found objs by key: " + arrayKey);
            }

            return jsonArray;

        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
        }

        return new JsonArray();
    }

    @Override
    public List<Country> createCountryList(JsonArray jsonArray) {
        List<Country> countries = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            Gson gson = new Gson();
            Country country = gson.fromJson(element, Country.class);
            countries.add(country);
        }

        return countries;
    }

    @Override
    public List<String> createPhoneList(JsonArray jsonArray) {
        List<String> phoneNumbers = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            phoneNumbers.add(element.getAsJsonObject().get("number").toString());
        }

        return phoneNumbers;
    }

    private void checkConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            conn.disconnect();
            throw new IOException("failed to connect: " + url);
        }
    }
}
