package com.pragma.brewery.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class GenericEntityWithId implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private Date createdDate;

  public GenericEntityWithId(String id) {
    if(id == null || id.isEmpty()) {
      this.id = UUID.randomUUID().toString().replace("-", "");
      this.createdDate = new Date();
    }
    else {
      this.id = id;
    }
  }

  public GenericEntityWithId() {
    this.id = UUID.randomUUID().toString().replace("-", "");
    this.createdDate = new Date();
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
