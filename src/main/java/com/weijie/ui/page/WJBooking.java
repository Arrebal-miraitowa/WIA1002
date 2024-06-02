package com.weijie.ui.page;

import com.weijie.core.entities.Parent;
import com.weijie.core.entities.Student;
import com.weijie.core.service.DestinationService;
import com.weijie.core.service.EventFilterService;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleButton;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.controls.WJRadioButton;
import com.weijie.ui.enums.WJLevel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class WJBooking extends ScrollPane {

    private final SimpleButton button = new SimpleButton("Booking");
    private final ToggleGroup selectPlace = new ToggleGroup();
    private final ToggleGroup selectChild = new ToggleGroup();
    private ToggleGroup selectDate;
    private final FlowPane selectChildPane = new FlowPane(10, 10);
    private final VBox content = new VBox();

    private Student selectedStudent;

    public WJBooking(Parent parent) {
        BorderPane buttonPane = new BorderPane();
        buttonPane.setRight(button);
        content.prefWidthProperty().bind(widthProperty().subtract(30));
        content.getChildren().addAll(
                group("Select Child:", parent.getChildren().stream()
                        .map(s -> new WJRadioButton(s, selectChild))
                        .toArray(Node[]::new)),
                SimpleControl.getSeparator(),
                group("Available Time Slots:", selectChildPane),
                SimpleControl.getSeparator(),
                SimpleControl.getText("Select A Place:", "text")
        );
        content.getChildren().addAll(DestinationService.getEntry().stream().map(e -> placeCell(e.getKey(), e.getValue())).toArray(Node[]::new));
        content.getChildren().add(buttonPane);
        selectChild.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            selectedStudent = (Student) newValue.getUserData();
            refreshSelectChildPane(selectedStudent);
        });
        selectChild.getToggles().get(0).setSelected(true);
        selectPlace.getToggles().get(0).setSelected(true);
        setContent(content);
        setButtonClicked(parent);
        getStylesheets().add(ResourcesLoader.load("/css/Booking.css"));
        getStyleClass().add("wj-booking");
        content.getStyleClass().add("content");
    }

    private void refreshSelectChildPane(Student student) {
        selectDate = new ToggleGroup();
        selectChildPane.getChildren().clear();
        selectChildPane.getChildren().addAll(EventFilterService.getAvailableTimeSlots(student).stream()
                .map(s -> new WJRadioButton(s, selectDate))
                .toArray(Node[]::new));
        selectDate.getToggles().get(0).setSelected(true);
    }

    private HBox group(String text, FlowPane flowPane) {
        HBox.setHgrow(flowPane, Priority.ALWAYS);
        return new HBox(10, SimpleControl.getText(text, "text"), flowPane);
    }

    private HBox group(String text, Node... nodes) {
        FlowPane flowPane = new FlowPane(10, 10, nodes);
        return group(text, flowPane);
    }

    private BorderPane placeCell(String s, double d) {
        Text t = SimpleControl.getText(s, "place-text");
        HBox hBox = new HBox(10,
                SimpleControl.getText(String.format("%.2f km away", d), "place-text"),
                new WJRadioButton(selectPlace, s));
        hBox.setAlignment(Pos.CENTER);
        BorderPane borderPane = new BorderPane(null, null, hBox, null, t);
        BorderPane.setAlignment(t, Pos.CENTER);
        borderPane.getStyleClass().add("border-pane");
        return borderPane;
    }

    private void setButtonClicked(Parent parent) {
        button.setOnMouseClicked(e -> {
            WJMessage.show("Success", WJLevel.SUCCESS);
            Student s = (Student) selectChild.getSelectedToggle().getUserData();
            String p = (String) selectPlace.getSelectedToggle().getUserData();
            String d = (String) selectDate.getSelectedToggle().getUserData();
            String studentBooking = String.join("|", p, d);
            parent.getBooking().add(String.join("|", s.getUsername(), p, d));
            s.getEvent().add(studentBooking);
            refreshSelectChildPane(s);
            WJProfile.refresh();
            ((Student) UserFilterService.getUser(s.getEmail())).getEvent().add(studentBooking);
        });
    }
}
