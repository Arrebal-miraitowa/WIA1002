package com.weijie.core.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DestinationService {

    private static HashMap<String, Double> bookingDistance = new HashMap<>();
    private static final double[] currentUserCoordinate = getArray(UserFilterService.currentUser.getCoordinate());
    private static final String DestinationPath = Objects.requireNonNull(DestinationService.class.getResource("/data/BookingDestination.txt")).getPath();

    static {
        try (BufferedReader br = new BufferedReader(new FileReader(DestinationPath))) {
            for (String line; (line = br.readLine()) != null; br.readLine()) {
                bookingDistance.put(line, calculateDistance(getArray(br.readLine())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookingDistance = sort();
    }

    private static double[] getArray(String coordinate) {
        return Arrays.stream(coordinate.split(", ")).mapToDouble(Double::parseDouble).toArray();
    }

    private static double calculateDistance(double[] coordinate2) {
        double deltaX = coordinate2[0] - currentUserCoordinate[0];
        double deltaY = coordinate2[1] - currentUserCoordinate[1];
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private static HashMap<String, Double> sort() {
        return bookingDistance.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static Set<Map.Entry<String, Double>> getEntry() {
        return bookingDistance.entrySet();
    }
}
