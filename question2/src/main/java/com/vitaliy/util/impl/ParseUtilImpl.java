package com.vitaliy.util.impl;

import com.google.gson.*;
import com.vitaliy.util.ParseUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public JsonObject createJsonObjectFromJsonByKey(String json, String arrayKey) {
        try {
            JsonObject rootJson = JsonParser.parseString(json).getAsJsonObject();
            JsonObject jsonArray = rootJson.getAsJsonObject(arrayKey);

            if (jsonArray.isJsonNull()) {
                throw new JsonParseException("Not found objs by key: " + arrayKey);
            }

            return jsonArray;

        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
        }

        return new JsonObject();
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
