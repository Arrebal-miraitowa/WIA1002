package com.weijie.ui.page;

import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.controls.WJStage;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class WJQuiz extends VBox {

    private final HBox navigationBar = new HBox();

    private final String[] tag = {"All", "Science", "Technology", "Engineering", "Mathematics"};

    private ObservableList<String> tagList;

    public WJQuiz(WJStage wjStage) {
        maxHeightProperty().bind(wjStage.getContent().heightProperty());
        maxWidthProperty().bind(wjStage.getContent().widthProperty());
        Arrays.stream(tag).forEach(tag -> navigationBar.getChildren().add(new TagButton(tag)));
        navigationBar.getStyleClass().add("navigation-bar");
        navigationBar.maxWidthProperty().bind(maxWidthProperty().subtract(80));
        getChildren().add(navigationBar);
        getStylesheets().add(ResourcesLoader.load("/css/Quiz.css"));
        getStyleClass().add("wj-quiz");
    }

    private static class TagButton extends BorderPane {

        private boolean pressed;

        private TagButton(String s) {
            Label label = new Label(s);
            label.getStyleClass().add("label");
            getStyleClass().addAll("tag-button", s.toLowerCase());
            setCenter(label);
            setOnMousePressed(event -> {
                if (!pressed) getStyleClass().add(s + "-pressed");
                else getStyleClass().remove(s + "-pressed");
                pressed = !pressed;
            });

            // 按钮释放时恢复默认样式
            setOnMouseReleased(event -> setStyle("-fx-background-color: white;"));

        }
    }
}
