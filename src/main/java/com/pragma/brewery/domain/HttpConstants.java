package com.pragma.brewery.domain;

public class HttpConstants {
  public static class HttpMethod {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
  }

  public static class HttpStatus {
    public static final int OK = 200;
    public static final int NO_CONTENT = 204;
    public static final int CREATED = 201;
    public static final int BAD_REQUEST = 400;
  }
}
