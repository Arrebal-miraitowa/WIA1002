package com.weijie.core.entities;

import java.util.StringJoiner;

public class Quiz {
    private String title;
    private String description;
    private Theme theme;
    private String content;
    private boolean finished;

    public Quiz(Theme theme, String title, String description, String content) {
        this.theme = theme;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Quiz(Theme theme, String title, String description, String content, boolean finished) {
        this(theme, title, description, content);
        this.finished = finished;
    }

    public Quiz() {}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Theme getTheme() {
        return theme;
    }

    public String getThemeString() {
        return theme.toString().toLowerCase();
    }

    public String getContent() {
        return content;
    }

    public Quiz setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public String toString() {
        return new StringJoiner("|", "", "\n")
                .add(theme.toString()).add(title).add(description).add(content).add(String.valueOf(finished)).toString();
    }

    public enum Theme {
        SCIENCE, TECHNOLOGY, ENGINEERING, MATHEMATICS
    }
}
