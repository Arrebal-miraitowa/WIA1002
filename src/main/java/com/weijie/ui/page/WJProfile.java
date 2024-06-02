package com.weijie.ui.page;

import com.weijie.core.entities.Parent;
import com.weijie.core.entities.Student;
import com.weijie.core.entities.Teacher;
import com.weijie.core.entities.User;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.function.Function;

public class WJProfile extends ScrollPane {

    private static User user;
    private static final VBox content = new VBox();

    public WJProfile() {
        refresh();
        getStylesheets().add(ResourcesLoader.load("/css/Profile.css"));
        content.getStyleClass().add("content");
        content.prefWidthProperty().bind(widthProperty().subtract(30));
        getStyleClass().addAll("wj-profile", "scroll-bar-style");
        setContent(content);
    }

    public static void refresh() {
        content.getChildren().clear();
        user = UserFilterService.currentUser;
        accessControl();
    }

    private static void accessControl() {
        if (user instanceof Student s) {
            addProfile(s.getInfoList());
            addOthers("Friend List", s.getFriends(), WJProfile::friendPane);
        } else if (user instanceof Parent p) {
            addProfile(p.getInfoList());
            addOthers("Past Booking", p.getBooking(), WJProfile::childPane);
        } else if (user instanceof Teacher t) {
            addProfile(t.getInfoList());
        }
    }

    private static void addProfile(ArrayList<String> infoList) {
        content.getChildren().add(titleGroup("Profile Information", SimpleControl.getGridPane(0, 0, infoList, WJProfile::profileText)));
    }

    private static TextFlow profileText(String s) {
        String[] str = s.split("\\|");
        return new TextFlow(SimpleControl.getText(str[0], "field"), SimpleControl.getText(str[1], "value"));
    }

    private static void addOthers(String title, ArrayList<String> list, Function<String, Pane> f) {
        content.getChildren().add(titleGroup(title, SimpleControl.getGridPane(list, f)));
    }

    private static VBox titleGroup(String title, Node content) {
        return new VBox(10, new VBox(5, SimpleControl.getText(title, "title"), SimpleControl.getSeparator()), content);
    }

    private static Text defaultText(String s) {
        return SimpleControl.getText(s, "friend-pane-text");
    }

    private static BorderPane friendPane(String s) {
        String[] str = s.split("\\|");
        BorderPane b = new BorderPane();
        b.setLeft(defaultText(str[0]));
        b.setRight(defaultText(str[1]));
        b.getStyleClass().add("friend-pane");
        return b;
    }

    private static VBox childPane(String s) {
        String[] str = s.split("\\|");
        VBox v =  new VBox(10,
                defaultText("Child: " + str[0]),
                defaultText("Place: " + str[1]),
                defaultText("Date: " + str[2]));
        v.getStyleClass().add("child-pane");
        return v;
    }
}
