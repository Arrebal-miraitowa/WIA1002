package com.weijie.core.entities;

public class Destination {
    private String name;
    private String date;
    private int[] coordinate;

    public Destination(String name, int[] coordinate, String date) {
        this.name = name;
        this.coordinate = coordinate;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d.%d", name, date, coordinate[0], coordinate[1]);
    }
}
