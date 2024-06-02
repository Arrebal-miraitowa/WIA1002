package com.weijie.core.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.weijie.core.entities.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class KryoSerializer {

    public static final String USER_DATA_PATH = Objects.requireNonNull(KryoSerializer.class.getResource("/data/UserData.bin")).getPath();
    public static final String QUIZ_PATH = Objects.requireNonNull(KryoSerializer.class.getResource("/data/Quiz.bin")).getPath();
    public static final String EVENT_PATH = Objects.requireNonNull(KryoSerializer.class.getResource("/data/Event.bin")).getPath();

    private static final Kryo KRYO;

    static {
        KRYO = initKryo();
    }

    private static Kryo initKryo() {
        Kryo kryo = new Kryo();
        kryo.register(ArrayList.class);
        kryo.register(User.Role.class);
        kryo.register(Student.class);
        kryo.register(LocalDateTime.class);
        kryo.register(Parent.class);
        kryo.register(Teacher.class);
        kryo.register(Quiz.class);
        kryo.register(Quiz.Theme.class);
        kryo.register(Event.class);
        kryo.register(Date.class);
        return kryo;
    }

    public static <T> void serialize(String path, List<T> data) {
        try (Output output = new Output(new FileOutputStream(path))) {
            KRYO.writeObject(output, data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void userSerialize(List<User> userList) {
        serialize(USER_DATA_PATH, userList);
    }

    public static void quizSerialize(List<Quiz> quizList) {
        serialize(QUIZ_PATH, quizList);
    }

    public static void eventSerialize(List<Event> eventList) {
        serialize(EVENT_PATH, eventList);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> deserialize(String path) {
        List<T> deserializedData = new ArrayList<>();
        try (Input input = new Input(new FileInputStream(path))) {
            deserializedData = (List<T>) KRYO.readObject(input, ArrayList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedData;
    }

    public static List<User> getUserList() {
        return deserialize(USER_DATA_PATH);
    }

    public static List<Quiz> getQuizList() {
        return deserialize(QUIZ_PATH);
    }

    public static List<Event> getEventList() {
        return deserialize(EVENT_PATH);
    }
}
