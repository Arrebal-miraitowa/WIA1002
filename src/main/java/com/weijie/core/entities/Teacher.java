package com.weijie.core.entities;

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

    public static Teacher get(String[] s) {
        return new Teacher(s[1], s[2], s[3], Integer.parseInt(s[4]), Integer.parseInt(s[5]));
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "", "\n");
        sj.add(super.toString()).add(String.valueOf(events)).add(String.valueOf(quizzes));
        return sj.toString();
    }
}
