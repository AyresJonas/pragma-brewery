package com.pragma.brewery.rest;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class RestServer {
  private static final BeerResource beerResource = new BeerResource();
  public static final int SERVER_PORT = 8000;
  private static HttpServer httpServer;

  private static final String BASE_URI = "/api";
  private static final String BEERS_URI = BASE_URI + "/beers";
  private static final String MONITORING_URI = BASE_URI + "/monitoring";

  private static void createContexts() {
    httpServer.createContext(BEERS_URI, (exchange -> {
      beerResource.handleBeerCRUDExchange(exchange);
    }));
    httpServer.createContext(MONITORING_URI, (exchange -> {
      beerResource.handleBeerMonitoringExchange(exchange);
    }));
  }

  public static HttpServer buildAndReturnHttpServer() throws IOException {
    try {
      httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
      createContexts();
      httpServer.setExecutor(null);
    }
    catch(Exception e) {
      System.err.println(e);
    }

    return httpServer;
  }
}
