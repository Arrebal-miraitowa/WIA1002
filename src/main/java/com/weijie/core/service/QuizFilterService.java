package com.weijie.core.service;

import com.weijie.core.entities.Quiz;

import java.util.List;

public class QuizFilterService {
    public static List<Quiz> getFilteredQuizzes(List<Quiz> quizzes, List<Quiz.Theme> themes) {
        return quizzes.stream().filter(quiz -> themes.contains(quiz.getTheme())).toList();
    }
}
