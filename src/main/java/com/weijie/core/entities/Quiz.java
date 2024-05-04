package com.weijie.core.entities;

import java.util.StringJoiner;

public class Quiz {
    private String title;
    private String description;
    private Theme theme;
    private String content;

    public Quiz(Theme theme, String title, String description, String content) {
        this.theme = theme;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Theme getTheme() {
        return theme;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return new StringJoiner("|", "", "\n").add(theme.toString()).add(title).add(description).add(content).toString();
    }

    public enum Theme {
        SCIENCE, TECHNOLOGY, ENGINEERING, MATHEMATICS
    }
}
