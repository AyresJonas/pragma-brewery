package com.pragma.brewery.domain;

public class Beer extends GenericEntityWithId {
  private static final long serialVersionUID = -7093739382071308219L;

  private String name;
  private Double maxTemp;
  private Double minTemp;

  public Beer() {
    super();
  }

  public Beer(String id, String name, Double maxTemp, Double minTemp) {
    super(id);
    this.name = name;
    this.maxTemp = maxTemp;
    this.minTemp = minTemp;
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
}
