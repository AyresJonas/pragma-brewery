package com.pragma.brewery.dto;

public class GenericResponseDTO {
  private String message;
  private Object content;

  public GenericResponseDTO(String message, Object content) {
    this.content = content;
    this.message = message;
  }

  public Object getContent() {
    return content;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setContent(Object content) {
    this.content = content;
  }
}
