package com.pragma.brewery.dto;

import java.util.List;

public class WarningReport {
  private final String currentTemp;
  private final List<BeerDTO> outsideTempRangeBeers;

  public WarningReport(String currentTemp, List<BeerDTO> outsideTempRangeBeers) {
    this.currentTemp = currentTemp;
    this.outsideTempRangeBeers = outsideTempRangeBeers;
  }

  public String getCurrentTemp() {
    return currentTemp;
  }

  public List<BeerDTO> getOutsideTempRangeBeers() {
    return outsideTempRangeBeers;
  }
}
