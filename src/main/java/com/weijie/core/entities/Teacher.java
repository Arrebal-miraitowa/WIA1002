package com.weijie.core.entities;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Teacher extends User {
    private int events;
    private int quizzes;

    public Teacher(String username, String email, String password, int events, int quizzes) {
        super(Role.TEACHER, username, email, password);
        this.events = events;
        this.quizzes = quizzes;
    }

    public Teacher(String username, String email, String password) {
        super(Role.TEACHER, username, email, password);
    }

    public Teacher() {}

    public int getEvents() {
        return events;
    }

    public void addEvents() {
        events++;
    }

    public int getQuizzes() {
        return quizzes;
    }

    public void addQuizzes() {
        quizzes++;
    }

    @Override
    public ArrayList<String> getInfoList() {
        ArrayList<String> list = super.getInfoList();
        list.add(0, "Role:| Teacher");
        list.add("Created events:| " + events);
        list.add("Created quizzes:| " + quizzes);
        return list;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "", "\n");
        sj.add(super.toString()).add(String.valueOf(events)).add(String.valueOf(quizzes));
        return sj.toString();
    }
}
