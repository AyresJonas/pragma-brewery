package com.pragma.brewery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pragma.brewery.converter.BeerConverter;
import com.pragma.brewery.domain.Beer;
import com.pragma.brewery.dto.BeerControl;
import com.pragma.brewery.dto.BeerDTO;
import com.pragma.brewery.dto.GenericResponseDTO;
import com.pragma.brewery.dto.WarningReport;
import com.pragma.brewery.repository.BeerRepository;

public class BeerService {
  private final BeerRepository repository;
  private final BeerConverter converter;
  private final BeerControl beerControl;

  public static final String TEMPERATURE_CONTROL_URL = "/temperature";

  private static final String BEER_ADDED_MSG = "%s is now being monitored";
  private static final String BEER_REMOVED_MSG = "%s is no longer being monitored";
  private static final String BEER_NOT_FOUND_MSG = "Beer not found";
  private static final String OK_MSG = "Everything is fine, current temperature is %s";

  public BeerService() {
    this.repository = new BeerRepository();
    this.converter = new BeerConverter();
    this.beerControl = new BeerControl();
  }

  public List<BeerDTO> getAllBeers() {
    return repository.findAll().stream().map(converter::toDTO).collect(Collectors.toList());
  }

  public boolean updateCurrentTemperature(String newTempString) {
    try {
      Double newTemp = Double.parseDouble(newTempString);
      beerControl.setCurrentTemp(newTemp);
    }
    catch(NumberFormatException e) {
      return false;
    }

    return true;
  }

  public BeerDTO createBeer(BeerDTO dto) {
    if(isInvalidBeer(dto)) {
      return null;
    }

    Beer entity = converter.toEntity(dto);
    dto = converter.toDTO(repository.insert(entity));

    return dto;
  }

  private boolean isInvalidBeer(BeerDTO dto) {
    return dto.getMaxTemp() == null || dto.getMinTemp() == null || dto.getName() == null;
  }

  public BeerDTO updateBeer(BeerDTO dto) {
    if(isInvalidBeer(dto)) {
      return null;
    }

    Beer entity = repository.update(converter.toEntity(dto));
    if(entity == null) {
      return null;
    }

    return converter.toDTO(entity);
  }

  public BeerDTO findBeerById(String id) {
    Beer entity = repository.findById(id);
    if(entity == null) {
      return null;
    }

    return converter.toDTO(entity);
  }

  public GenericResponseDTO addBeerToMonitoring(BeerDTO dto) {
    if(findBeerById(dto.getId()) == null) {
      return genericResponse(BEER_NOT_FOUND_MSG);
    }

    beerControl.addBeer(dto);

    return genericResponse(String.format(BEER_ADDED_MSG, dto.getName()));
  }

  public GenericResponseDTO removeMonitoredBeer(BeerDTO dto) {
    if(findBeerById(dto.getId()) == null || !beerControl.getMonitoredBeers().contains(dto)) {
      return genericResponse(BEER_NOT_FOUND_MSG);
    }

    beerControl.removeBeer(dto);

    return genericResponse(String.format(BEER_REMOVED_MSG, dto.getName()));
  }

  public boolean deleteBeer(BeerDTO dto) {
    return repository.delete(converter.toEntity(dto));
  }

  public GenericResponseDTO buildAndReturnReport() {
    List<BeerDTO> beers = beerControl.getOutsideTempRangeBeers();
    if(beers.size() == 0) {
      return genericResponse(
        String.format(OK_MSG, beerControl.getCurrentTempString()),
        new ArrayList<>(beerControl.getMonitoredBeers())
      );
    }

    return genericResponse("", new WarningReport(beerControl.getCurrentTempString(), beers));
  }

  private GenericResponseDTO genericResponse(String message, Object content) {
    return new GenericResponseDTO(message, content);
  }

  private GenericResponseDTO genericResponse(String message) {
    return genericResponse(message, "");
  }
}
