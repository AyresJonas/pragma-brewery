package com.pragma.brewery.dto;

import java.io.Serializable;

public class BeerDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String name;
  private Double maxTemp;
  private Double minTemp;

  public BeerDTO() { }

  public BeerDTO(String id, String name, Double maxTemp, Double minTemp) {
    this.name = name;
    this.id = id;
    this.maxTemp = maxTemp;
    this.minTemp = minTemp;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getMinTemp() {
    return minTemp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getMaxTemp() {
    return maxTemp;
  }

  public void setMaxTemp(Double maxTemp) {
    this.maxTemp = maxTemp;
  }

  public void setMinTemp(Double minTemp) {
    this.minTemp = minTemp;
  }

  public boolean isOutsideTempRange(Double externalTemp) {
    return (externalTemp > maxTemp) || (externalTemp < minTemp);
  }
}
