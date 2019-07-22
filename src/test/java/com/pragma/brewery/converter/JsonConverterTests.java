package com.pragma.brewery.converter;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.pragma.brewery.dto.BeerDTO;

import static org.junit.Assert.*;

import org.junit.Before;

public class JsonConverterTests {
  private Gson gson;

  @Before
  public void setUp() {
    this.gson = new GsonBuilder().create();
  }

  @Test
  public void shouldConvertObjectToJson() {
    BeerDTO dto = new BeerDTO();
    dto.setName("IPA");

    assertEquals(JsonConverter.toJson(dto), gson.toJson(dto));
  }

  @Test
  public void shouldConvertJsonToObject() {
    BeerDTO dto = new BeerDTO();
    dto.setName("IPA");
    String dtoJson = gson.toJson(dto);
    BeerDTO convertedDTO = (BeerDTO)JsonConverter.toObject(dtoJson, BeerDTO.class);
    assertTrue(convertedDTO.getName().equals("IPA"));
  }
}
