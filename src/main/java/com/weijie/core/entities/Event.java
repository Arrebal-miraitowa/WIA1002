package com.weijie.core.entities;

import com.weijie.core.common.MyDate;

import java.util.Date;
import java.util.StringJoiner;

public class Event {

    private String title;
    private String description;
    private String venue;
    private Date date;
    private String time;
    private boolean isRegistered;

    public Event(String title, String description, String venue, String date, String time) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.date = MyDate.getDate(date);
        this.time = time;
    }

    public Event() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return MyDate.toString(date);
    }

    public String getTime() {
        return time;
    }

    public String getTitleDate() {
        return title + "|" + getDateString();
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public Event setRegistered() {
        isRegistered = true;
        return this;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("|", "", "\n");
        sj.add(title).add(description).add(venue).add(getDateString()).add(time).add(String.valueOf(isRegistered));
        return sj.toString();
    }
}
