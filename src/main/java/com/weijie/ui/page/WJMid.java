package com.weijie.ui.page;

import com.weijie.core.entities.Parent;
import com.weijie.core.entities.Student;
import com.weijie.core.entities.Teacher;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.controls.WJStage;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class WJMid extends StackPane {

    private final VBox student = new VBox(new FontIcon(FontAwesomeSolid.USER), new Label("Student"));
    private final VBox parent = new VBox(new FontIcon(FontAwesomeSolid.USER_FRIENDS), new Label("Parent"));
    private final VBox teacher = new VBox(new FontIcon(FontAwesomeSolid.USER_TIE), new Label("Teacher"));
    private final HBox iconBox = new HBox(student, parent, teacher);
    private final VBox titleBox = new VBox(new Label("Choose your character"), iconBox);

    public WJMid() {
        setMaxSize(290, 370);
        setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), null)));
    }

    public WJMid(WJStage wjStage, String name, String email, String password) {
        setEvent(wjStage, name, email, password);
        wjStage.getRoot().getChildren().add(this);
        setMaxSize(730, 422);
        getChildren().add(titleBox);
        setVisible(false);
        loadStyle();
    }

    private void loadStyle() {
        getStyleClass().add("wj-mid");
        titleBox.getStyleClass().add("title-box");
        iconBox.getStyleClass().add("icon-box");
        student.getStyleClass().add("student");
        parent.getStyleClass().add("parent");
        teacher.getStyleClass().add("teacher");
    }

    private void setEvent(WJStage wjStage, String name, String email, String password) {
        student.setOnMouseClicked(e -> wjStage.initialization(new Student(name, email, password), this));
        parent.setOnMouseClicked(e -> wjStage.initialization(new Parent(name, email, password), this));
        teacher.setOnMouseClicked(e -> wjStage.initialization(new Teacher(name, email, password), this));
    }

    @Override
    public String getUserAgentStylesheet() {
        return ResourcesLoader.load("/css/Mid.css");
    }
}
