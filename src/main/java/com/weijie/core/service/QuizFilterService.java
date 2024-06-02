package com.weijie.core.service;

import com.weijie.core.common.KryoSerializer;
import com.weijie.core.entities.Quiz;
import com.weijie.core.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuizFilterService {

    private static ArrayList<Quiz> rawList;

    public static void refresh() {
        List<Quiz> quizList = KryoSerializer.getQuizList();
        rawList = UserFilterService.currentUser instanceof Student s ? getRemarkedList(s.getFinishedQuiz(), quizList) : new ArrayList<>(quizList);
    }

    private static ArrayList<Quiz> getRemarkedList(ArrayList<String> finishedQuiz, List<Quiz> rawList) {
        return rawList.stream().map(r -> finishedQuiz.contains(r.getTitle()) ? r.setFinished(true) : r).collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Quiz> getFilteredQuizzes(List<Quiz.Theme> themes) {
        return themes.isEmpty() ? rawList : rawList.stream().filter(quiz -> themes.contains(quiz.getTheme())).toList();
    }

    public static void addQuiz(Quiz q) { rawList.add(q); }

    public static void writeToFile() {
        KryoSerializer.quizSerialize(rawList);
    }
}
