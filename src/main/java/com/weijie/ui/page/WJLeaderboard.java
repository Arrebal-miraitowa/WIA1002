package com.weijie.ui.page;

import com.weijie.core.entities.Student;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.enums.WJLevel;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.stream.IntStream;

public class WJLeaderboard extends ScrollPane {

    private static Student student;
    private static List<Student> studentList;
    private static final VBox vBox = new VBox(10);

    public WJLeaderboard() {
        refresh();
        vBox.prefWidthProperty().bind(widthProperty().subtract(21));
        setContent(vBox);
        getStylesheets().add(ResourcesLoader.load("/css/Leaderboard.css"));
        getStyleClass().addAll("leaderboard", "scroll-bar-style");
    }

    public static void refresh() {
        vBox.getChildren().clear();
        student = (Student) UserFilterService.currentUser;
        studentList = UserFilterService.getLeaderboard();
        IntStream.range(0, studentList.size()).forEach(i -> vBox.getChildren().add(new borderPane(i + 1, studentList.get(i))));
    }

    private static class borderPane extends BorderPane {

        private boolean isPressed;
        private final FontIcon fontIcon = new FontIcon(Evaicons.PERSON_ADD_OUTLINE);

        public borderPane(int num, Student s) {
            fontIcon.getStyleClass().add("font-icon");
            if (s.equals(student)) {
                fontIcon.setDisable(true);
                fontIcon.setVisible(false);
            }
            setLeft(getLabel(num + "  " + s.getUsername()));
            setRight(new HBox(30, getLabel(s.getPoint() + " points"), getLabel(s.getPointLastUpdatedString() + " Updated"), fontIcon));
            getStyleClass().add("borderpane");
            setIconEvent(s);
        }

        private Label getLabel(String s) {
            Label l = new Label(s);
            l.getStyleClass().add("borderpane-label");
            return l;
        }

        private void setIconEvent(Student s) {
            fontIcon.setOnMouseClicked(e -> {
                if (student.getFriends().contains(s.getFriendInfo())) {
                    WJMessage.show("Already in the friends list", WJLevel.WARN);
                    return;
                }
                if (isPressed) {
                    WJMessage.show("Friend request has been sent", WJLevel.WARN);
                } else {
                    isPressed = true;
                    s.getRequest().add(student);
                    WJMessage.show("Friend request sent successfully", WJLevel.SUCCESS);
                }
            });
        }
    }
}
