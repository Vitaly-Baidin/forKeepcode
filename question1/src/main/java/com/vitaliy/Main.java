package com.vitaliy;

import com.google.gson.JsonArray;
import com.vitaliy.model.Country;
import com.vitaliy.util.ParseUtil;
import com.vitaliy.util.impl.ParseUtilImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        ParseUtil parseUtil = new ParseUtilImpl();

        URL urlCountry = new URL("https://onlinesim.ru/api/getFreeCountryList");

        String jsonCountry = parseUtil.getJsonFromURL(urlCountry);
        JsonArray countryJsonArray = parseUtil.createJsonArrayFromJsonByKey(jsonCountry, "countries");

        List<Country> countries = parseUtil.createCountryList(countryJsonArray);

        for (Country country : countries) {
            URL urlPhoneNumber = new URL("https://onlinesim.ru/api/getFreePhoneList?country=" + country.getCountryId());

            String jsonPhoneNumber = parseUtil.getJsonFromURL(urlPhoneNumber);
            JsonArray phoneNumberJsonArray = parseUtil.createJsonArrayFromJsonByKey(jsonPhoneNumber, "numbers");

            country.setPhoneList(parseUtil.createPhoneList(phoneNumberJsonArray));
        }

        countries.forEach(System.out::println);
    }

}
