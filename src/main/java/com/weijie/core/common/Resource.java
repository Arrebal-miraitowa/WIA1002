package com.weijie.core.common;

import com.weijie.core.entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Resource {

    public static final String UserDataPath = Objects.requireNonNull(Resource.class.getResource("/data/UserData.txt")).getPath();
    public static final String EventPath = Objects.requireNonNull(Resource.class.getResource("/data/Event.txt")).getPath();
    public static final String QuizPath = Objects.requireNonNull(Resource.class.getResource("/data/Quiz.txt")).getPath();


    public static void fileWriter(Object object, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Event> eventLoader() {
        ArrayList<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EventPath))) {
            for (String str; (str = br.readLine()) != null; ) {
                String[] s = str.split("\\|");
                events.add(new Event(s[0], s[1], s[2], s[3], s[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static ArrayList<Quiz> quizLoader(ArrayList<String> title) {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(QuizPath))) {
            for (String str; (str = br.readLine()) != null; ) {
                String[] s = str.split("\\|");
                quizzes.add(new Quiz(Quiz.Theme.valueOf(s[0]), s[1], s[2], s[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public static <E extends User> E roleLoader(String Email) {
        return roleFinder(fileSearcher(Email, 2));
    }

    public static String fileSearcher(String str, int position) {
        try (BufferedReader br = new BufferedReader(new FileReader(UserDataPath))) {
            for (String s; (s = br.readLine()) != null; ) {
                if (s.split(",")[position].equals(str)) return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <E extends User> E roleFinder(String str) {
        if (str == null) return null;
        String[] s = str.split(",");
        return switch (s[0]) {
//            case "STUDENT" -> (E) new Student(s[1], s[2], s[3], s[4], Integer.parseInt(s[5]), s[6], s[7],
//                    Boolean.getBoolean(s[8]), listLoader(s[9]), listLoader(s[10]), listLoader(s[11]));
            case "PARENT" -> (E) new Parent(s[1], s[2], s[3]);
            case "TEACHER" -> (E) new Teacher(s[1], s[2], s[3]);
            default -> throw new IllegalStateException("Unexpected value: " + s[0]);
        };
    }

    private static ArrayList<String> listLoader(String s) {
        return new ArrayList<>(Arrays.asList(s.split(";")));
    }
}
