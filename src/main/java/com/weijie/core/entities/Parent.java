package com.weijie.core.entities;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Parent extends User {
    private ArrayList<String> childrenInfo;
    private ArrayList<String> previous;

    public Parent(String username, String email, String password,
                  ArrayList<String> children,
                  ArrayList<String> previous) {
        super(Role.PARENT, username, email, password);
        this.childrenInfo = children;
        this.previous = previous;
    }

    public Parent(String username, String email, String password) {
        super(Role.PARENT, username, email, password);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "", "\n");
        sj.add(super.toString())
                .add(String.join(";", childrenInfo))
                .add(String.join(";", previous));
        return sj.toString();
    }
}
