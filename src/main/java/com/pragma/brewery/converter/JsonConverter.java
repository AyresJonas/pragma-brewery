package com.pragma.brewery.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {
  private static final Gson gson = new GsonBuilder().create();

  public static Object toObject(String json, Class type) {
    return gson.fromJson(json, type);
  }

  public static String toJson(Object object) {
    return gson.toJson(object);
  }
}
