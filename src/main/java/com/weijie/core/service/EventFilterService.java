package com.weijie.core.service;

import com.weijie.core.common.KryoSerializer;
import com.weijie.core.common.MyDate;
import com.weijie.core.entities.Event;
import com.weijie.core.entities.Student;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventFilterService {

    private static ArrayList<Event> rawList;

    private static final Date currentDate = MyDate.getDate("03/02/2022");

    public static void refresh() {
        rawList = getFilteredList(KryoSerializer.getEventList());
    }

    private static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static ArrayList<Event> getFilteredList(List<Event> rawList) {
        return rawList.stream()
                .filter(event -> !event.getDate().before(currentDate))
                .sorted(Comparator.comparing(Event::getDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void addEvent(Event event) {
        rawList.add(event);
        rawList = getFilteredList(rawList);
    }

    public static void setStuFilteredList(Student s) {
        rawList = rawList.stream()
                .map(e -> s.getEvent().contains(e.getTitleDate()) ? e.setRegistered() : e)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<String> getExistEvent() {
        return rawList.stream()
                .filter(Event::isRegistered)
                .map(Event::getDateString)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean isListEmpty() {
        return rawList.isEmpty();
    }

    public static List<Event> getTodayList() {
        return rawList.stream()
                .filter(event -> event.getDate().equals(currentDate))
                .toList();
    }

    public static List<Event> getNextList() {
        return rawList.stream()
                .filter(event -> !event.getDate().equals(currentDate))
                .limit(3)
                .collect(Collectors.toList());
    }

    public static List<String> getAvailableTimeSlots(Student student) {
        assert currentDate != null;
        return Stream.iterate(toLocalDate(currentDate).plusDays(1), date -> date.plusDays(1))
                .filter(date -> student.getEvent().stream()
                        .map(s -> LocalDate.parse(s.split("\\|")[1], MyDate.getFormatter()))
                        .noneMatch(date::equals))
                .limit(6)
                .map(date -> date.format(MyDate.getFormatter()))
                .toList();
    }

    public static void writeToFile() {
        KryoSerializer.eventSerialize(rawList);
    }
}