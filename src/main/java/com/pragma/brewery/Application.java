package com.pragma.brewery;

import java.io.IOException;

import com.pragma.brewery.rest.RestServer;

import com.sun.net.httpserver.HttpServer;

public class Application {
  public static void main(String[] args) throws IOException {
    RestServer.buildAndReturnHttpServer().start();
    System.out.println("Server is running on port " + RestServer.SERVER_PORT);
  }
}
