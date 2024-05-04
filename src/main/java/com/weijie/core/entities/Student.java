package com.weijie.core.entities;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Student extends User {
    private int point;
    private String father;
    private String mother;
    private ArrayList<String> request = new ArrayList<>();
    private ArrayList<String> friends = new ArrayList<>();
    private ArrayList<String> eventDate = new ArrayList<>();

    public Student(String username, String email, String password, int point, String father, String mother,
                   ArrayList<String> request, ArrayList<String> friends) {
        super(Role.STUDENT, username, email, password);
        this.point = point;
        this.father = father;
        this.mother = mother;
        this.request = request;
        this.friends = friends;
    }

    public Student(String username, String email, String password) {
        super(Role.STUDENT, username, email, password);
        request.add(null);
        friends.add(null);
        eventDate.add(null);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "","\n");
        sj.add(super.toString()).add(String.valueOf(point)).add(father).add(mother)
                .add(String.join(";", request))
                .add(String.join(";", friends))
                .add(String.join(";", eventDate));
        return sj.toString();
    }
}
