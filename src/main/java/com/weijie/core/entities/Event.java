package com.weijie.core.entities;

import com.weijie.core.service.EventFilterService;

import java.util.Date;
import java.util.StringJoiner;

public class Event {
    private String title;
    private String description;
    private String venue;
    private Date date;
    private String time;

    public Event(String title, String description, String venue, String date, String time) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.date = EventFilterService.toDate(date);
        this.time = time;
    }

    public Date getDate() {
        return date;
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

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("|", "", "\n");
        sj.add(title).add(description).add(venue).add(EventFilterService.getDateString(date)).add(time);
        return sj.toString();
    }
}
