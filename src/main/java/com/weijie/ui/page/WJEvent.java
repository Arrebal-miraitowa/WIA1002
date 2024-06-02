package com.weijie.ui.page;

import com.weijie.core.entities.Event;
import com.weijie.core.entities.Student;
import com.weijie.core.service.EventFilterService;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.enums.WJLevel;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Random;

public class WJEvent extends ScrollPane {

    private static final StackPane sp = new StackPane(new Label("No Events"));
    private static final VBox vBox = new VBox(10);

    private static boolean isStudent;
    private static ArrayList<String> existEvent;
    private static Student student;

    public WJEvent() {
        EventFilterService.refresh();
        if (UserFilterService.currentUser instanceof Student s) {
            student = s;
            isStudent = true;
            EventFilterService.setStuFilteredList(s);
            existEvent = EventFilterService.getExistEvent();
        }
        sp.prefHeightProperty().bind(heightProperty().subtract(22));
        sp.prefWidthProperty().bind(widthProperty().subtract(22));
        sp.getStyleClass().add("sp");
        //TODO 2024/5/13 20:43: 滚动条遮挡bug
        getStylesheets().add(ResourcesLoader.load("/css/Event.css"));
        getStyleClass().addAll("wj-event", "scroll-bar-style");
        vBox.prefWidthProperty().bind(widthProperty().subtract(22));
        setContent(vBox);
        refresh();
    }

    public static void refresh() {
        isStudent = false;
        vBox.getChildren().clear();
        displayLogic();
    }

    private static void displayLogic() {
        if (EventFilterService.isListEmpty()) vBox.getChildren().add(sp);
        else {
            if (!EventFilterService.getTodayList().isEmpty()) {
                Label today = new Label("Today");
                today.getStyleClass().add("today");
                today.setMaxWidth(Double.MAX_VALUE);
                vBox.getChildren().addAll(today, SimpleControl.getGridPane(EventFilterService.getTodayList(), EventBlock::new));
            }
            if (!EventFilterService.getNextList().isEmpty()) {
                Label Upcoming = new Label("Closest 3 Upcoming Events");
                Upcoming.getStyleClass().add("upcoming");
                Upcoming.setMaxWidth(Double.MAX_VALUE);
                vBox.getChildren().addAll(Upcoming, SimpleControl.getGridPane(EventFilterService.getNextList(), EventBlock::new));
            }
        }
    }

    private static class EventBlock extends HBox {

        private boolean isRegistered;
        private final Theme t = Theme.getRandom();

        private EventBlock(Event event) {
            isRegistered = event.isRegistered();
            if (isStudent) {
                getStyleClass().add("event-block-hover");
                setMouseEvent(event);
            }
            BorderPane datetime = new BorderPane();
            datetime.setLeft(setLabel("Time: " + event.getTime(), "l4"));
            datetime.setRight(setLabel(event.getDateString(), "l5"));
            VBox container = new VBox(setLabel(event.getTitle(), "l1"), setLabel(event.getDescription(), "l2"), setLabel("Venue: " + event.getVenue(), "l3"), datetime);
            container.getStyleClass().add("container");
            container.minWidthProperty().bind(prefWidthProperty().subtract(5));
            container.setBackground(new Background(new BackgroundFill(t.getBack(), new CornerRadii(0, 6, 6, 0, false), null)));
            StackPane myStackPane = new StackPane();
            myStackPane.getStyleClass().add("my-stack-pane");
            myStackPane.setBackground(new Background(new BackgroundFill(t.getRect(), new CornerRadii(6, 0, 0, 6, false), null)));
            getChildren().addAll(myStackPane, container);
            getStyleClass().add("event-block");
        }

        private Label setLabel(String text, String style) {
            Label l = new Label(text);
            l.getStyleClass().setAll(style);
            l.setTextFill(t.getText());
            return l;
        }

        private void setMouseEvent(Event event) {
            setOnMouseClicked(e -> {
                if (isRegistered) WJMessage.show("You have registered for this event", WJLevel.WARN);
                else {
                    if (existEvent.contains(event.getDateString())) {
                        WJMessage.show("You can only register one event a day", WJLevel.WARN);
                        return;
                    }
                    isRegistered = true;
                    student.addPoint(5);
                    student.getEvent().add(event.getTitleDate());
                    existEvent.add(event.getDateString());
                    WJMessage.show("Score +5", WJLevel.SUCCESS);
                    WJLeaderboard.refresh();
                    WJProfile.refresh();
                }
            });
        }
    }

    private enum Theme {

        BLUE("#004EEB", "#2970FF", "#D1E0FF33"),
        GREEN("#067647", "#17B26A", "#DCFAE633"),
        PURPLE("#6941C6", "#9E77ED", "#F4EBFF33"),
        RED("#B42318", "#F04438", "#FEE4E233"),
        ORANGE("#B54708", "#F79009", "#FEF0C733");
//        GRAY("#384250", "#6C737F", "#E4E4E433");

        private String Text;
        private String Rect;
        private String Back;

        private static final Random r = new Random();

        Theme(String text, String rect, String back) {
            Text = text;
            Rect = rect;
            Back = back;
        }

        public Paint getText() {
            return Paint.valueOf(Text);
        }

        public Paint getRect() {
            return Paint.valueOf(Rect);
        }

        public Paint getBack() {
            return Paint.valueOf(Back);
        }

        public static Theme getRandom() {
            return Theme.values()[r.nextInt(5)];
        }
    }
}