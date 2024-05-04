package com.weijie.core.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

public abstract class User {
    private final Role ID;
    private String Username;
    private String Email;
    private String Password;
    private int[] coordinate;

    public User(Role ID, String username, String email, String password) {
        Random r = new Random();
        this.ID = ID;
        Username = username;
        Email = email;
        Password = password;
        coordinate = new int[]{r.nextInt(1001) - 500, r.nextInt(1001) - 500};
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

    public String info() {
        return String.format("%s %s %d.%d", Username, Email, coordinate[0], coordinate[1]);
    }

    public static ArrayList<String> getArrayList(String s) {
        return new ArrayList<>(Arrays.asList(s.split(";")));
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        sj.add(ID.name()).add(Username).add(Email).add(Password).add(String.format("%d.%d", coordinate[0], coordinate[1]));
        return sj.toString();
    }

    public enum Role {
        STUDENT, PARENT, TEACHER
    }
}
