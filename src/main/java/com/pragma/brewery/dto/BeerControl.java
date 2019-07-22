package com.pragma.brewery.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeerControl {
  private Set<BeerDTO> monitoredBeers;
  private Double currentTemp;
  private Double externalTemp;
  private boolean isDoorOpen;

  public BeerControl() {
    this.monitoredBeers = new HashSet<>();
    this.currentTemp = 4D;
    this.externalTemp = 20D;
    this.isDoorOpen = false;
  }

  public void removeBeer(BeerDTO beer) {
    monitoredBeers.remove(beer);
  }

  public String getCurrentTempString() {
    return String.format("%.2f", currentTemp).replace(",", ".");
  }

  public void setCurrentTemp(Double newTemp) {
    this.currentTemp = newTemp;
  }

  public Double getCurrentTemp() {
    return currentTemp;
  }

  public Set<BeerDTO> getMonitoredBeers() {
    return monitoredBeers;
  }

  public void addBeer(BeerDTO beer) {
    monitoredBeers.add(beer);
  }

  public List<BeerDTO> getOutsideTempRangeBeers() {
    List<BeerDTO> beers = new ArrayList<>();
    for(BeerDTO beer : monitoredBeers) {
      if(beer.isOutsideTempRange(currentTemp)) {
        beers.add(beer);
      }
    }

    return beers;
  }
}
