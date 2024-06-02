package com.weijie.core.entities;

import java.util.Date;

public class Destination {
    private String childrenName;
    private String place;
    private Date date;
    private int[] coordinate;

    public Destination(String childrenName, String place, Date date, int[] coordinate) {
        this.childrenName = childrenName;
        this.place = place;
        this.date = date;
        this.coordinate = coordinate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d.%d", place, date, coordinate[0], coordinate[1]);
    }
}
