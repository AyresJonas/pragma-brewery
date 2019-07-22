package com.pragma.brewery.converter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.stream.Collectors;

public class StreamConverter {
  public static String toString(InputStream stream) {
    return new BufferedReader(new InputStreamReader(stream))
        .lines().collect(Collectors.joining("\n"));
  }
}
