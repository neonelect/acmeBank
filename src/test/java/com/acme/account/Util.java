package com.acme.account;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Util {

  public static <T> T getStringAsObject(String json, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(json, clazz);
    } catch (IOException e) {
      System.out.println("Can't parse JSON");
    }
    return null;
  }
}
