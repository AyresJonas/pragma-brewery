package com.pragma.brewery.dto;

import org.junit.Test;

import com.pragma.brewery.dto.BeerDTO;

import static org.junit.Assert.*;

import org.junit.Before;

public class BeerControlTest {
  private BeerControl beerControl;

  @Before
  public void setUp() {
    this.beerControl = new BeerControl();
  }

  @Test
  public void shouldUpdateCurrentTemperature() {
    beerControl.setCurrentTemp(6D);
    assertTrue(beerControl.getCurrentTempString().equals("6.00"));
  }

  @Test
  public void shouldAddNewBeerToMonitoringAndReturnItOnOutsideTempRangeBeers() {
    BeerDTO beerDto = new BeerDTO();
    beerDto.setName("Pilsen");
    beerDto.setMaxTemp(6D);
    beerDto.setMinTemp(4D);

    beerControl.addBeer(beerDto);
    beerControl.setCurrentTemp(7D);
    assertTrue(beerControl.getOutsideTempRangeBeers().get(0).getName().equals(beerDto.getName()));
  }
}
