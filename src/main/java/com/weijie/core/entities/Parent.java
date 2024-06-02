package com.weijie.core.entities;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class Parent extends User {

    private ArrayList<Student> children;
    private ArrayList<String> booking;

    public Parent(String username, String email, String password, ArrayList<Student> children, ArrayList<String> preBooking) {
        super(Role.PARENT, username, email, password);
        this.children = children;
        this.booking = preBooking;
    }

    public Parent(String username, String email, String password) {
        super(Role.PARENT, username, email, password);
    }

    public Parent(String username, String email, String password, ArrayList<Student> children) {
        super(Role.PARENT, username, email, password);
        this.children = children;
    }

    public Parent() {
    }

    public ArrayList<Student> getChildren() {
        return children;
    }

    public ArrayList<String> getBooking() {
        return booking;
    }

    @Override
    public ArrayList<String> getInfoList() {
        ArrayList<String> list = super.getInfoList();
        list.add(0, "Role:| Parent");
        if (booking != null) list.addAll(IntStream.range(0, children.size()).mapToObj(i -> "Child " + (i + 1) + ":| " + children.get(i).getUsername()).toList());
        return list;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "", "\n");
        sj.add(super.toString()).add(String.join(";", booking));
        return sj.toString();
    }
}
