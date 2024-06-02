package com.weijie.core.service;

import com.weijie.core.common.KryoSerializer;
import com.weijie.core.entities.Parent;
import com.weijie.core.entities.Student;
import com.weijie.core.entities.Teacher;
import com.weijie.core.entities.User;

import java.util.Comparator;
import java.util.List;

public class UserFilterService {

    private static List<User> rawList;
    public static User currentUser;

    public static void refresh() {
        rawList = KryoSerializer.getUserList();
    }

    public static void writeToFile() {
        KryoSerializer.userSerialize(rawList);
    }

    public static void setUser(String email) {
        if (email == null) return;
        currentUser = getUser(email);
    }

    public static void addUser(User user) {
        currentUser = user;
        rawList.add(user);
    }

    public static User getUser(String email) {
        return rawList.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public static void fillStudentParents(Parent p) {
        p.getChildren().forEach(c -> ((Student) getUser(c.getEmail())).getParents().add(p.getUsername()));
    }

    public static List<Student> getLeaderboard() {
        return getStudentList().stream()
                .sorted(Comparator.comparingInt(Student::getPoint).reversed().thenComparing(Student::getPointLastUpdated))
                .toList();
    }

    @SuppressWarnings("unchecked")
    private static  <T extends User> List<T> getRoleList(Class<T> targetClass) {
        return rawList.stream()
                .filter(u -> targetClass.isAssignableFrom(u.getClass()))
                .map(r -> (T) r).toList();
    }

    public static List<Student> getStudentList() {
        return getRoleList(Student.class);
    }

    public List<Parent> getParentList() {
        return getRoleList(Parent.class);
    }

    public List<Teacher> getTeacherList() {
        return getRoleList(Teacher.class);
    }

    public static boolean isEmailExist(String email) {
        return rawList.stream().anyMatch(l -> l.getEmail().equals(email));
    }

    public static boolean isNameExist(String name) {
        return rawList.stream().anyMatch(l -> l.getUsername().equals(name));
    }

    public static boolean isPasswordExist(String password) {
        return rawList.stream().anyMatch(l -> l.getPassword().equals(password));
    }
}
