package com.weijie.ui.page;

import com.weijie.core.common.Resource;
import com.weijie.core.entities.Event;
import com.weijie.core.entities.User;
import com.weijie.core.service.EventFilterService;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJStage;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.Random;

public class WJEvent extends ScrollPane {

    private final VBox vBox = new VBox(10);

    private static final Random r = new Random();

    private List<Event> eventList;

    public WJEvent(WJStage wjStage) {
        User user = (User) wjStage.getUserData();
        eventList = EventFilterService.getDateList(Resource.eventLoader());
        maxHeightProperty().bind(wjStage.getContent().heightProperty());
        maxWidthProperty().bind(wjStage.getContent().widthProperty());
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setHvalue(0);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        getStylesheets().add(ResourcesLoader.load("/css/Event.css"));//加载css
        getStyleClass().addAll("wj-event", "scroll-bar-style");
        vBox.prefWidthProperty().bind(widthProperty().subtract(22));
        setContent(displayLogic(eventList));
    }

    private Pane displayLogic(List<Event> events) {
        List<Event> todayList = EventFilterService.getTodayList(events);
        List<Event> Closest = EventFilterService.getNextList(events);
        if (events.isEmpty()) return noEvent();
        else {
            if (!todayList.isEmpty()) {
                Label today = new Label("Today");
                today.getStyleClass().add("today");
                today.setMaxWidth(Double.MAX_VALUE);
                vBox.getChildren().addAll(today, SimpleControl.getGridPane(todayList, vBox.widthProperty(), EventBlock::new));
            }
            if (!Closest.isEmpty()) {
                Label Upcoming = new Label("Closest 3 Upcoming Events");
                Upcoming.getStyleClass().add("upcoming");
                Upcoming.setMaxWidth(Double.MAX_VALUE);
                vBox.getChildren().addAll(Upcoming, SimpleControl.getGridPane(Closest, vBox.widthProperty(), EventBlock::new));
            }
        }
        return vBox;
    }

    private Pane noEvent() {
        StackPane sp = new StackPane(new Label("No Events"));
        sp.prefHeightProperty().bind(heightProperty().subtract(22));
        sp.prefWidthProperty().bind(widthProperty().subtract(22));
        sp.getStyleClass().add("sp");
        return sp;
    }

    private static class EventBlock extends HBox {

        private EventBlock(Event event) {
            Theme t = Theme.getRandom();
            Label title = new Label(event.getTitle());
            Label description = new Label(event.getDescription());
            Label venue = new Label("Venue: " + event.getVenue());
            Label time = new Label("Time: " + event.getTime());
            Label date = new Label(EventFilterService.getDateString(event.getDate()));
            BorderPane datetime = new BorderPane();
            datetime.setLeft(time);
            datetime.setRight(date);
            VBox container = new VBox(title, description, venue, datetime);
            StackPane myStackPane = new StackPane();
            getStyleClass().add("event-block");
            setHgrow(this, Priority.ALWAYS);
            myStackPane.getStyleClass().add("my-stack-pane");
            myStackPane.setBackground(new Background(new BackgroundFill(
                    t.getRect(),
                    new CornerRadii(6, 0, 0, 6, false), null)));
            container.getStyleClass().add("container");
            container.minHeightProperty().bind(heightProperty());
            container.minWidthProperty().bind(prefWidthProperty().subtract(5));
            container.setBackground(new Background(new BackgroundFill(
                    t.getBack(),
                    new CornerRadii(0, 6, 6, 0, false), null)));
            datetime.setMaxWidth(Double.MAX_VALUE);
            title.setTextFill(t.getText());
            title.getStyleClass().setAll("l1");
            description.getStyleClass().setAll("l2");
            description.setTextFill(t.getText());
            venue.getStyleClass().setAll("l3");
            venue.setTextFill(t.getText());
            time.getStyleClass().setAll("l4");
            time.setTextFill(t.getText());
            date.getStyleClass().setAll("l5");
            date.setTextFill(t.getText());
            getChildren().addAll(myStackPane, container);
        }
    }

    private enum Theme {

        BLUE("#004EEB", "#2970FF", "#D1E0FF33"),
        GREEN("#067647", "#17B26A", "#DCFAE633"),
        PURPLE("#6941C6", "#9E77ED", "#F4EBFF33"),
        RED("#B42318", "#F04438", "#FEE4E233"),
        ORANGE("#B54708", "#F79009", "#FEF0C733");

        private String Text;
        private String Rect;
        private String Back;

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