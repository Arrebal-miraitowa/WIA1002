package com.weijie.core.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringJoiner;

public class Student extends User {
    private int point;
    private ArrayList<String> parents = new ArrayList<>(2);
    private LocalDateTime pointLastUpdated = LocalDateTime.now();
    private ArrayList<String> event = new ArrayList<>();
    private ArrayList<Student> request = new ArrayList<>();
    private ArrayList<String> friends = new ArrayList<>();
    private ArrayList<String> finishedQuiz = new ArrayList<>();

    public Student(String username, String email, String password, String coordinate, int point, ArrayList<String> parents,
                   ArrayList<String> event, ArrayList<Student> request,
                   ArrayList<String> friends, ArrayList<String> finishedQuiz) {
        super(Role.STUDENT, username, email, password, coordinate);
        this.point = point;
        this.parents = parents;
        this.event = event;
        this.request = request;
        this.friends = friends;
        this.finishedQuiz = finishedQuiz;
    }

    public Student(String username, String email, String password) {
        super(Role.STUDENT, username, email, password);
    }

    public Student() {
    }

    public void addPoint(int num) {
        point += num;
    }

    public int getPoint() {
        return point;
    }

    public ArrayList<String> getParents() {
        return parents;
    }

    public ArrayList<String> getEvent() {
        return event;
    }

    public ArrayList<Student> getRequest() {
        return request;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public String getFriendInfo() {
        return super.getUsername() + "|" + super.getEmail();
    }

    public ArrayList<String> getFinishedQuiz() {
        return finishedQuiz;
    }

    @Override
    public ArrayList<String> getInfoList() {
        ArrayList<String> list = super.getInfoList();
        list.add(0, "Role:| Student");
        list.add("Point:| " + point);
        if (parents.size() != 0) list.add("Parent:| " + String.join(", ", parents));
        return list;
    }

    public LocalDateTime getPointLastUpdated() {
        return pointLastUpdated;
    }

    public String getPointLastUpdatedString() {
        return pointLastUpdated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setPointLastUpdated() {
        pointLastUpdated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return new StringJoiner(",", "@", "\n").add(super.toString())
                .add(String.valueOf(point))
                .add(getPointLastUpdatedString() + "\n")
                .add(String.join(";", event) + "\n")
                .add(String.join(";", request.toString()) + "\n")
                .add(String.join(";", friends) + "\n")
                .add(String.join(";", finishedQuiz))
                .toString();
    }
}
