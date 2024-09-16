package com.example.org.unizd.rma.petesic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Response;

public class JsonParser {

    public JsonParser(){

    }

    public String readTemp(String string) throws IOException {

        Gson gson = new Gson();

        //fetch data array
        JsonObject jsonObject = gson.fromJson(string, JsonObject.class);
        JsonArray jsonArray = jsonObject.get("timelines").getAsJsonObject().get("hourly").getAsJsonArray();

        //get wanted array element
        JsonObject properties = jsonArray.get(0).getAsJsonObject();
        String data = properties.get("values").getAsJsonObject().get("temperature").getAsString();

        return data;
    }

    public String readWindSpeed(String string) throws IOException {

        Gson gson = new Gson();

        //fetch data array
        JsonObject jsonObject = gson.fromJson(string, JsonObject.class);
        JsonArray jsonArray = jsonObject.get("timelines").getAsJsonObject().get("hourly").getAsJsonArray();

        //get wanted array element
        JsonObject properties = jsonArray.get(0).getAsJsonObject();
        String data = properties.get("values").getAsJsonObject().get("windSpeed").getAsString();

        return data;
    }

    public String readHumidity(String string) throws IOException {

        Gson gson = new Gson();

        //fetch data array
        JsonObject jsonObject = gson.fromJson(string, JsonObject.class);
        JsonArray jsonArray = jsonObject.get("timelines").getAsJsonObject().get("hourly").getAsJsonArray();

        //get wanted array element
        JsonObject properties = jsonArray.get(0).getAsJsonObject();
        String data = properties.get("values").getAsJsonObject().get("humidity").getAsString();

        return data;
    }

}
