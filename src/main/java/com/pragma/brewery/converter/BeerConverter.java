package com.pragma.brewery.converter;

import com.pragma.brewery.domain.Beer;
import com.pragma.brewery.dto.BeerDTO;

public class BeerConverter {
  public BeerConverter() { }

  public BeerDTO toDTO(Beer entity) {
    return new BeerDTO(
      entity.getId(),
      entity.getName(),
      entity.getMaxTemp(),
      entity.getMinTemp()
    );
  }

  public Beer toEntity(BeerDTO dto) {
    return new Beer(
      dto.getId(),
      dto.getName(),
      dto.getMaxTemp(),
      dto.getMinTemp()
    );
  }
}
