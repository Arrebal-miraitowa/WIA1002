package com.weijie.ui.page;

import com.weijie.core.common.PasswordHashing;
import com.weijie.core.entities.Parent;
import com.weijie.core.entities.Student;
import com.weijie.core.entities.Teacher;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.AnimationUtils;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleButton;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJCheckBox;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.controls.WJStage;
import com.weijie.ui.enums.WJLevel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;

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
        setEvent(wjStage, name, email, PasswordHashing.get(password));
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
        student.setOnMouseClicked(e -> wjStage.toMainPage(new Student(name, email, password), this));
        parent.setOnMouseClicked(e -> {
            titleBox.setDisable(true);
            parentMid parentMid = new parentMid(wjStage, new Parent(name, email, password, new ArrayList<>(), new ArrayList<>()));
            parentMid.setOpacity(0);
            AnimationUtils.fade(Duration.seconds(0.5), titleBox, true, event -> {
                getChildren().clear();
                getChildren().add(parentMid);
                AnimationUtils.fade(Duration.seconds(0.5), parentMid, false).play();
            }).play();
        });
        teacher.setOnMouseClicked(e -> wjStage.toMainPage(new Teacher(name, email, password), this));
    }

    private class parentMid extends BorderPane {

        private final Label label = SimpleControl.getLabel("Please select your child", SimpleControl.LabelEnum.H1);
        private final Button button = new SimpleButton("Done");
        private final FlowPane fp = new FlowPane(10, 10);

        public parentMid(WJStage wjStage, Parent parent) {
            //TODO 2024/6/1 21:59: 如果学生都全父母，会有bug
            fp.getChildren().addAll(UserFilterService.getStudentList().stream()
                    .filter(s -> s.getParents().size() != 2)
                    .map(s -> new WJCheckBox(parent, s))
                    .toArray(Node[]::new));
            fp.setAlignment(Pos.CENTER);
//            fp.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, null, null)));
//            setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, null, null)));
            BorderPane.setAlignment(label, Pos.CENTER);
            BorderPane.setAlignment(fp, Pos.CENTER);
            BorderPane.setAlignment(button, Pos.CENTER);
            setTop(label);
            setCenter(fp);
            setBottom(button);
            setClickedEvent(wjStage, parent);
        }

        private void setClickedEvent(WJStage wjStage, Parent parent) {
            button.setOnMouseClicked(e -> {
                if (fp.getChildren().stream().noneMatch(wj -> ((WJCheckBox) wj).isSelected())) {
                    WJMessage.show("Choose at least one child", WJLevel.DANGER);
                    return;
                }
                UserFilterService.fillStudentParents(parent);
                wjStage.toMainPage(parent, WJMid.this);
            });
        }
    }

    @Override
    public String getUserAgentStylesheet() {
        return ResourcesLoader.load("/css/Mid.css");
    }
}
