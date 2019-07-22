package com.pragma.brewery.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import com.pragma.brewery.converter.JsonConverter;
import com.pragma.brewery.converter.StreamConverter;
import com.pragma.brewery.domain.HttpConstants.HttpMethod;
import com.pragma.brewery.domain.HttpConstants.HttpStatus;
import com.pragma.brewery.dto.BeerDTO;
import com.pragma.brewery.service.BeerService;

public class BeerResource {
  private static final BeerService service = new BeerService();

  public static void handleBeerMonitoringExchange(final HttpExchange exchange)
      throws IOException {
    String response = "";
    int statusCode = HttpStatus.OK;
    switch(exchange.getRequestMethod()) {
      case HttpMethod.GET: {
        response = JsonConverter.toJson(service.buildAndReturnReport()); break;
      }
      case HttpMethod.POST: {
        if(exchange.getRequestURI().toString().contains(service.TEMPERATURE_CONTROL_URL)) {
          if(!updateCurrentTemperature(exchange.getRequestURI().toString())) {
            statusCode = HttpStatus.BAD_REQUEST;
          }
        }
        else {
          response = addBeerToMonitoring(exchange.getRequestBody());
        }
        break;
      }
      case HttpMethod.DELETE: {
        response = removeMonitoredBeer(exchange.getRequestBody()); break;
      }
    }

    exchange.getResponseHeaders().set("Content-Type", "application/json");
    exchange.sendResponseHeaders(statusCode, response.getBytes().length);
    OutputStream output = exchange.getResponseBody();
    output.write(response.getBytes());
    output.flush();
    exchange.close();
  }

  private static boolean updateCurrentTemperature(String url) {
    String[] urlParts = url.split("/");
    String newTempString = urlParts[urlParts.length - 1];

    return service.updateCurrentTemperature(newTempString);
  }

  private static String addBeerToMonitoring(InputStream stream) {
    String json = StreamConverter.toString(stream);
    BeerDTO dto = (BeerDTO)JsonConverter.toObject(json, BeerDTO.class);

    return JsonConverter.toJson(service.addBeerToMonitoring(dto));
  }

  private static String removeMonitoredBeer(InputStream stream) {
    String json = StreamConverter.toString(stream);
    BeerDTO dto = (BeerDTO)JsonConverter.toObject(json, BeerDTO.class);

    return JsonConverter.toJson(service.removeMonitoredBeer(dto));
  }

  public static void handleBeerCRUDExchange(final HttpExchange exchange) throws IOException {
    String response = "";
    int statusCode = HttpStatus.OK;
    switch(exchange.getRequestMethod()) {
      case HttpMethod.GET: {
        response = JsonConverter.toJson(service.getAllBeers()); break;
      }
      case HttpMethod.POST: {
        response = createBeer(exchange.getRequestBody());
        if(response == null || response == "") {
          statusCode = HttpStatus.BAD_REQUEST;
        }
        else {
          statusCode = HttpStatus.CREATED;
        }
        break;
      }
      case HttpMethod.PUT: {
        response = updateBeer(exchange.getRequestBody()); break;
      }
      case HttpMethod.DELETE: {
        if(!deleteBeer(exchange.getRequestBody())) {
          statusCode = HttpStatus.NO_CONTENT;
        }
        break;
      }
    }

    exchange.getResponseHeaders().set("Content-Type", "application/json");
    exchange.sendResponseHeaders(statusCode, response.getBytes().length);
    OutputStream output = exchange.getResponseBody();
    output.write(response.getBytes());
    output.flush();
    exchange.close();
  }

  public static String createBeer(InputStream stream) {
    String json = StreamConverter.toString(stream);
    BeerDTO dto = (BeerDTO)JsonConverter.toObject(json, BeerDTO.class);

    return JsonConverter.toJson(service.createBeer(dto));
  }

  private static String updateBeer(InputStream stream) {
    String json = StreamConverter.toString(stream);
    BeerDTO dto = (BeerDTO)JsonConverter.toObject(json, BeerDTO.class);

    return JsonConverter.toJson(service.updateBeer(dto));
  }

  private static boolean deleteBeer(InputStream stream) {
    String json = StreamConverter.toString(stream);
    BeerDTO dto = (BeerDTO)JsonConverter.toObject(json, BeerDTO.class);

    return service.deleteBeer(dto);
  }
}
