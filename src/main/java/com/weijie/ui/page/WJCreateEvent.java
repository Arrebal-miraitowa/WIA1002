package com.weijie.ui.page;

import com.weijie.core.entities.Event;
import com.weijie.core.entities.Teacher;
import com.weijie.core.service.EventFilterService;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.SimpleButton;
import com.weijie.ui.controls.WJForm;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.controls.WJTextField;
import com.weijie.ui.enums.WJLevel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.StackPane;

public class WJCreateEvent extends StackPane {

    //TODO 2024/5/13 21:24: 创建事件界面美化
    private final WJForm wjForm = new WJForm();
    private final WJTextField title = new WJTextField(true);
    private final WJTextField description = new WJTextField(true);
    private final WJTextField venue = new WJTextField(true);
    private final WJTextField date = new WJTextField("dd/mm/yyyy", true);
    private final WJTextField time = new WJTextField(true);
    private final SimpleButton button = new SimpleButton("Publish");

    public WJCreateEvent() {
        wjForm.add("Title", title, 0);
        wjForm.add("Description", description, 1);
        wjForm.add("Venue", venue, 2);
        wjForm.add("Date", date, 3);
        wjForm.add("Time", time, 4);
        wjForm.add(button, 5, HPos.RIGHT, VPos.CENTER);
        getChildren().add(wjForm);
        setOnMouseEvent();
    }

    private void setOnMouseEvent() {
        //TODO 2024/5/13 22:17: 创建事件, 文本格式验证
        button.setOnMouseClicked(e -> {
            EventFilterService.addEvent(new Event(title.getText(), description.getText(), venue.getText(), date.getText(), time.getText()));
            WJMessage.show("Success", WJLevel.SUCCESS);
            ((Teacher) UserFilterService.currentUser).addEvents();
            title.setText("");
            description.setText("");
            venue.setText("");
            date.setText("");
            time.setText("");
            WJEvent.refresh();
            WJProfile.refresh();
            EventFilterService.writeToFile();
        });
    }
}
