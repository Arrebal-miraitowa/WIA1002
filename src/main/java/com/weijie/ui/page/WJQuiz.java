package com.weijie.ui.page;

import com.weijie.core.entities.Quiz;
import com.weijie.core.entities.Student;
import com.weijie.core.service.QuizFilterService;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.controls.WJStage;
import com.weijie.ui.enums.WJLevel;
import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.stream.Stream;

public class WJQuiz extends BorderPane {

    private final Student student  = (Student) UserFilterService.currentUser;;

    private final HBox navigationBar = new HBox();
    private final ScrollPane scrollPane = new ScrollPane();

    private final ObservableList<Quiz.Theme> tagList = FXCollections.observableArrayList();

    public WJQuiz(WJStage wjStage) {
        QuizFilterService.refresh();
        maxHeightProperty().bind(wjStage.getContent().heightProperty());
        maxWidthProperty().bind(wjStage.getContent().widthProperty());
        navigationBar.setMaxWidth(780);
        Arrays.stream(new String[]{"All", "Science", "Technology", "Engineering", "Mathematics"})
                .forEach(tag -> navigationBar.getChildren().add(new TagButton(tag)));
        tagList.addListener((InvalidationListener) e ->
                scrollPane.setContent(SimpleControl.getGridPane(QuizFilterService.getFilteredQuizzes(tagList), widthProperty().subtract(22), quizContainer::new)));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //TODO 2024/5/13 20:44: 滚动条遮挡bug
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(SimpleControl.getGridPane(QuizFilterService.getFilteredQuizzes(tagList), widthProperty().subtract(22), quizContainer::new));
        setTop(navigationBar);
        setCenter(scrollPane);
        BorderPane.setAlignment(navigationBar, Pos.CENTER);
        getStylesheets().add(ResourcesLoader.load("/css/Quiz.css"));
        scrollPane.getStyleClass().addAll("scroll-pane", "scroll-bar-style");
        navigationBar.getStyleClass().add("navigation-bar");
        getStyleClass().add("wj-quiz");
    }

    private class quizContainer extends VBox {

        private final FontIcon fi = new FontIcon(Evaicons.CHECKMARK_CIRCLE);
        private final BorderPane borderPane = new BorderPane();

        private boolean finished;

        public quizContainer(Quiz quiz) {
            borderPane.setLeft(smallButton(quiz));
            fi.getStyleClass().add("icon");
            if (quiz.isFinished()) {
                borderPane.setRight(fi);
                finished = true;
            }
            setOnMouseClicked(e -> {
                if (finished) WJMessage.show("The quiz has been completed", WJLevel.WARN);
                else {
                    finished = true;
                    quiz.setFinished(true);
                    borderPane.setRight(fi);
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), fi);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                    student.addPoint(2);
                    student.getFinishedQuiz().add(quiz.getTitle());
                    student.setPointLastUpdated();
                    WJLeaderboard.refresh();
                    WJProfile.refresh();
                    WJMessage.show("Score +2", WJLevel.SUCCESS);
//                    System.out.println(student);
                }
            });
            getStyleClass().add("quiz-container");
            Label title = new Label(quiz.getTitle());
            title.getStyleClass().add("title");
            Label description = new Label(quiz.getDescription());
            description.getStyleClass().add("description");
            getChildren().addAll(borderPane, title, description);
        }

        private StackPane smallButton(Quiz quiz) {
            Label l = new Label(quiz.getThemeString());
            l.getStyleClass().add("tag-button-small-label");
            StackPane sp = new StackPane(l);
            sp.getStyleClass().addAll("tag-button-small", quiz.getThemeString());
            return sp;
        }
    }

    public class TagButton extends BorderPane {

        private boolean pressed;
        private final String name;

        private TagButton(String s) {
            Label label = new Label(s);
            this.name = s.toLowerCase();
            label.getStyleClass().add("label");
            getStyleClass().add("tag-button");
            setCenter(label);
            if (s.equals("All")) addStyle();
            setOnMousePressed(event -> {
                if (!pressed) {
                    getStyleClass().add(name + "-pressed");
                    if (s.equals("All")) {
                        getTagButtonList().skip(1).forEach(TagButton::clear);
                        tagList.clear();
                    } else {
                        getFirstTagButton().forEach(TagButton::clear);
                        tagList.add(getTheme());
                    }
                    pressed = !pressed;
                } else {
                    if (s.equals("All")) return;
                    getStyleClass().remove(name + "-pressed");
                    tagList.remove(getTheme());
                    pressed = !pressed;
                    if (getTagButtonList().noneMatch(TagButton::getPressed)) {
                        getFirstTagButton().forEach(TagButton::addStyle);
                    }
                }
            });
        }

        public boolean getPressed() {
            return pressed;
        }

        public String getName() {
            return name;
        }

        public void clear() {
            pressed = false;
            getStyleClass().remove(name + "-pressed");
        }

        public void addStyle() {
            pressed = true;
            getStyleClass().add(name + "-pressed");
        }

        public Quiz.Theme getTheme() {
            return Quiz.Theme.valueOf(name.toUpperCase());
        }
    }

    private Stream<TagButton> getTagButtonList() {
        return navigationBar.getChildren().stream().map(borderPane -> ((TagButton) borderPane));
    }

    private Stream<TagButton> getFirstTagButton() {
        return navigationBar.getChildren().stream().map(borderPane -> ((TagButton) borderPane)).limit(1);
    }
}
