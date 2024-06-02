package com.weijie.core.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

public abstract class User {
    private Role ID;
    private String Username;
    private String Email;
    private String Password;
    private String coordinate;

    public User() {}

    public User(Role id, String username, String email, String password, String coordinate) {
        this(id, username, email, password);
        this.coordinate = coordinate;
    }

    public User(Role id, String username, String email, String password) {
        ID = id;
        Username = username;
        Email = email;
        Password = password;
        coordinate = getRandomCoordinate();
    }

    public Role getID() {
        return ID;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public ArrayList<String> getInfoList() {
        return new ArrayList<>(Arrays.asList("Username:| " + Username, "Email:| " + Email, "Location:| " + coordinate));
    }

    private String getRandomCoordinate() {
        Random r = new Random();
        return (r.nextInt(1001) - 500) + ", " + (r.nextInt(1001) - 500);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        sj.add(ID.name()).add(Username).add(Email).add(Password).add(coordinate);
        return sj.toString();
    }

    public enum Role {
        STUDENT, PARENT, TEACHER
    }
}
