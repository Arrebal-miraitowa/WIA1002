package com.weijie.core.service;

import com.weijie.core.entities.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EventFilterService {

    private static final String currentDate = "03/02/2022";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static List<Event> getDateList(ArrayList<Event> events) {
        return events.stream()
                .filter(event -> !event.getDate().before(toDate(currentDate)))
                .sorted(Comparator.comparing(Event::getDate))
                .toList();
    }

    public static List<Event> getTodayList(List<Event> events) {
        return events.stream()
                .filter(event -> event.getDate().equals(toDate(currentDate)))
                .toList();
    }

    public static List<Event> getNextList(List<Event> events) {
        return events.stream()
                .filter(event -> !event.getDate().equals(toDate(currentDate)))
                .limit(3)
                .collect(Collectors.toList());
    }

    public static Date toDate(String s) {
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateString(Date date) {
        return sdf.format(date);
    }
}
