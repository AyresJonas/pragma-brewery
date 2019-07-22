package com.pragma.brewery.converter;

import org.junit.Test;

import com.pragma.brewery.domain.Beer;

import com.pragma.brewery.dto.BeerDTO;

import static org.junit.Assert.*;

import org.junit.Before;

public class BeerConverterTests {
  private BeerConverter beerConverter;

  @Before
  public void setUp() {
    this.beerConverter = new BeerConverter();
  }

  public BeerDTO getDefaultBeerDTO() {
    BeerDTO defaultDTO = new BeerDTO();
    defaultDTO.setName("Pilsen");
    defaultDTO.setMaxTemp(6D);
    defaultDTO.setMinTemp(4D);
    return defaultDTO;
  }

  public Beer getDefaultBeerEntity() {
    Beer defaultEntity = new Beer();
    defaultEntity.setName("IPA");
    defaultEntity.setMaxTemp(6D);
    defaultEntity.setMinTemp(5D);
    return defaultEntity;
  }

  @Test
  public void shouldConvertEntityToDTO() {
    Beer defaultEntity = getDefaultBeerEntity();
    BeerDTO convertedDTO = beerConverter.toDTO(defaultEntity);
    assertEquals(defaultEntity.getName(), convertedDTO.getName());
    assertEquals(defaultEntity.getMaxTemp(), convertedDTO.getMaxTemp());
    assertEquals(defaultEntity.getMinTemp(), convertedDTO.getMinTemp());
  }

  @Test
  public void shouldConvertDTOToEntity() {
    BeerDTO defaultDTO = getDefaultBeerDTO();
    Beer convertedEntity = beerConverter.toEntity(defaultDTO);
    assertEquals(defaultDTO.getName(), convertedEntity.getName());
    assertEquals(defaultDTO.getMaxTemp(), convertedEntity.getMaxTemp());
    assertEquals(defaultDTO.getMinTemp(), convertedEntity.getMinTemp());
    assertTrue(convertedEntity.getId() != null && convertedEntity.getId() != "");
  }
}
