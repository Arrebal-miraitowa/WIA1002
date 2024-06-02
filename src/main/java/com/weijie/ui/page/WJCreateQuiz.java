package com.weijie.ui.page;

import com.weijie.core.entities.Quiz;
import com.weijie.core.entities.Teacher;
import com.weijie.core.service.QuizFilterService;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.SimpleButton;
import com.weijie.ui.controls.*;
import com.weijie.ui.enums.WJLevel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

//TODO 2024/5/14 16:24: 创建测验面美化
public class WJCreateQuiz extends StackPane {

    private final WJForm wjForm = new WJForm();
    private final WJTextField title = new WJTextField(true);
    private final WJTextField description = new WJTextField(true);
    private final WJTextField content = new WJTextField(true);
    private final SimpleButton button = new SimpleButton("Publish");

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final WJRadioButton science = new WJRadioButton("Science", toggleGroup, true);
    private final WJRadioButton technology = new WJRadioButton("Technology", toggleGroup, true);
    private final WJRadioButton engineering = new WJRadioButton("Engineering", toggleGroup, true);
    private final WJRadioButton mathematics = new WJRadioButton("Mathematics", toggleGroup, true);

    public WJCreateQuiz() {
        wjForm.add("Title", title, 0);
        wjForm.add("Description", description, 1);
        wjForm.add("Content", content, 2);
        wjForm.addNodes("Theme", new WJRadioButton[]{science, technology, engineering, mathematics}, 3);
        wjForm.add(button, 4, HPos.RIGHT, VPos.CENTER);
        getChildren().add(wjForm);
        setOnMouseEvent();
    }

    private void setOnMouseEvent() {
        //TODO 2024/5/14 16:17: 创建测验, 文本格式验证
        button.setOnMouseClicked(e -> {
            //TODO 2024/5/14 21:55: toggleGroup非空检测
            QuizFilterService.addQuiz(new Quiz((Quiz.Theme) toggleGroup.getSelectedToggle().getUserData(), title.getText(), description.getText(), content.getText()));
            WJMessage.show("Success", WJLevel.SUCCESS);
            ((Teacher) UserFilterService.currentUser).addQuizzes();
            toggleGroup.selectToggle(null);
            title.setText("");
            description.setText("");
            content.setText("");
            WJEvent.refresh();
            WJProfile.refresh();
            QuizFilterService.writeToFile();
        });
    }
}
