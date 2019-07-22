package com.pragma.brewery.service;

import org.junit.Test;

import com.pragma.brewery.dto.BeerDTO;
import com.pragma.brewery.dto.GenericResponseDTO;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

import org.junit.Before;

public class BeerServiceTest {
  private BeerService beerService;

  @Before
  public void setUp() {
    this.beerService = new BeerService();
  }

  public BeerDTO getDefaultBeerDTO() {
    BeerDTO defaultBeerDTO = new BeerDTO();
    defaultBeerDTO.setMaxTemp(6D);
    defaultBeerDTO.setMinTemp(4D);
    defaultBeerDTO.setName("Pilsen");
    return defaultBeerDTO;
  }

  @Test
  public void shouldCreateANewBeerAndReturnTheCreatedBeerWithId() {
    BeerDTO newBeerDto = beerService.createBeer(getDefaultBeerDTO());
    assumeNotNull(newBeerDto.getId());
  }

  @Test
  public void shouldTryToCreateInvalidBeerAndReturnNull() {
    assertEquals(beerService.createBeer(new BeerDTO()), null);
  }

  @Test
  public void shouldReturnAnEmptyBeerList() {
    assertEquals(beerService.getAllBeers().size(), 0);
  }

  @Test
  public void shouldCreateABeerAndGetABeerListWithOneElement() {
    beerService.createBeer(getDefaultBeerDTO());
    assertEquals(beerService.getAllBeers().size(), 1);
  }

  @Test
  public void shouldUpdateCurrentTemperatureAndReturnTrue() {
    assertEquals(beerService.updateCurrentTemperature("8"), true);
    assertTrue(beerService.buildAndReturnReport().getMessage().contains("8.00"));
  }

  @Test
  public void shouldTryToUpdateCurrentTemperatureWithAnInvalidDoubleAndFail() {
    assertEquals(beerService.updateCurrentTemperature("InvalidDouble"), false);
  }

  @Test
  public void shouldUpdateAndReturnUpdatedBeer() {
    BeerDTO createdBeer = beerService.createBeer(getDefaultBeerDTO());
    createdBeer.setMinTemp(3D);
    BeerDTO updatedBeer = beerService.updateBeer(createdBeer);
    Double expectedValue = 3D;
    assertEquals(updatedBeer.getMinTemp(), expectedValue);
  }

  @Test
  public void shouldTryToUpdateANonExistingBeerAndFail() {
    assertEquals(beerService.updateBeer(new BeerDTO()), null);
  }

  @Test
  public void shouldCreateNewBeerAndGetSameBeerWithId() {
    BeerDTO createdBeer = beerService.createBeer(getDefaultBeerDTO());
    BeerDTO fetchedBeerWithId = beerService.findBeerById(createdBeer.getId());
    assertEquals(createdBeer.getName(), fetchedBeerWithId.getName());
    assertEquals(createdBeer.getId(), fetchedBeerWithId.getId());
    assertEquals(createdBeer.getMinTemp(), fetchedBeerWithId.getMinTemp());
    assertEquals(createdBeer.getMaxTemp(), fetchedBeerWithId.getMaxTemp());
  }

  @Test
  public void shouldAddBeerToMonitoringAndReturnGenericResponseDTOWithBeersName() {
    BeerDTO createdBeer = beerService.createBeer(getDefaultBeerDTO());
    GenericResponseDTO response = beerService.addBeerToMonitoring(createdBeer);
    assertTrue(response.getMessage().contains(createdBeer.getName()));
  }

  @Test
  public void shouldTryToAddNonExistingBeerAndFail() {
    BeerDTO defaultBeerDTO = getDefaultBeerDTO();
    GenericResponseDTO response = beerService.addBeerToMonitoring(defaultBeerDTO);
    assertFalse(response.getMessage().contains(defaultBeerDTO.getName()));
  }

  @Test
  public void shouldRemoveMonitoredBeerAndReturnRemovedBeersName() {
    BeerDTO createdBeer = beerService.createBeer(getDefaultBeerDTO());
    beerService.addBeerToMonitoring(createdBeer);
    GenericResponseDTO response = beerService.removeMonitoredBeer(createdBeer);
    assertTrue(response.getMessage().contains(createdBeer.getName()));
  }

  @Test
  public void shouldTryToRemoveNonExistingMonitoredBeerAndFail() {
    BeerDTO defaultBeerDTO = getDefaultBeerDTO();
    GenericResponseDTO response = beerService.removeMonitoredBeer(defaultBeerDTO);
    assertFalse(response.getMessage().contains(defaultBeerDTO.getName()));
  }

  @Test
  public void shouldDeleteBeerAndReturnTrue() {
    BeerDTO createdBeer = beerService.createBeer(getDefaultBeerDTO());
    assertTrue(beerService.deleteBeer(createdBeer));
  }

  @Test
  public void shouldTryToDeleteNonExistingBeerAndFail() {
    assertFalse(beerService.deleteBeer(new BeerDTO()));
  }
}
