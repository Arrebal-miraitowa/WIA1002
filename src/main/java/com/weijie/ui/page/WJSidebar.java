package com.weijie.ui.page;

import com.weijie.core.entities.Parent;
import com.weijie.core.entities.Student;
import com.weijie.core.entities.Teacher;
import com.weijie.core.entities.User;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJBadge;
import com.weijie.ui.controls.WJDrawer;
import com.weijie.ui.controls.WJStage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

public class WJSidebar extends StackPane {

    private static final String STYLE_SHEET = ResourcesLoader.load("/css/wj-sidebar.css");
    private static boolean isStudent;

    private final WJStage wjStage;
    private final StackPane sp = new StackPane();
    private final WJDrawer drawer = new WJDrawer(335, sp);

    private final Label titleLabel = new Label("STEM");
    private final ListView<NavItem> menu = new ListView<>();
    private final NavItem Logout = new NavItem(new FontIcon(Evaicons.LOG_OUT), "Log out", null);
    private final VBox asideContainer = new VBox(titleLabel, SimpleControl.getSeparator(), menu, SimpleControl.getSeparator(), Logout);

    public WJSidebar(WJStage wjStage) {
        this.wjStage = wjStage;
        isStudent = false;
        initialize();
        menu.getItems().addAll(
                new NavItem(new FontIcon(Evaicons.PERSON_OUTLINE), "Profile", new WJProfile()),
                new NavItem(new FontIcon(Evaicons.CALENDAR_OUTLINE), "Events", new WJEvent())
        );
        accessControl(UserFilterService.currentUser);
        // 默认选择第一个
        parentProperty().addListener(observable -> menu.getSelectionModel().select(0));
    }

    private void accessControl(User user) {
        if (user instanceof Student s) {
            isStudent = true;
            wjStage.setContent(sp);
            menu.getItems().addAll(
                    new NavItem(new FontIcon(Evaicons.BOOK_OUTLINE), "Quiz", new WJQuiz(wjStage)),
                    new NavItem(new FontIcon(Material2OutlinedAL.LEADERBOARD), "Leaderboard", new WJLeaderboard()),
                    new NavItem(new FontIcon(Evaicons.PEOPLE_OUTLINE), "Friend Request", s, drawer)
            );
        } else if (user instanceof Teacher) {
            menu.getItems().addAll(
                    new NavItem(new FontIcon(Evaicons.EDIT_2_OUTLINE), "Create Event", new WJCreateEvent()),
                    new NavItem(new FontIcon(Evaicons.EDIT_2_OUTLINE), "Create Quiz", new WJCreateQuiz())
            );
        } else if (user instanceof Parent p) {
            menu.getItems().addAll(
                    new NavItem(new FontIcon(Material2OutlinedAL.DATE_RANGE), "Booking", new WJBooking(p))
            );
        }
    }

    private void initialize() {
        VBox.setVgrow(menu, Priority.ALWAYS);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        getChildren().add(asideContainer);
        menuSelectedListener();
        Logout.setOnMouseClicked(e -> wjStage.toLoginPage());
        //styleClass
        getStylesheets().add(STYLE_SHEET);
        Logout.getStyleClass().add("logout");
        asideContainer.getStyleClass().add("aside");
        titleLabel.getStyleClass().add("title-label");
        menu.getStyleClass().addAll("scroll-bar-style", "menu");
    }

    //菜单选中事件监听
    private void menuSelectedListener() {
        menu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (isStudent) {
                if (newValue.content == null) {
                    drawer.show(() -> menu.getSelectionModel().select(oldValue));
                    newValue.wjBadge.setValue(0);
                    return;
                }
                if (oldValue != null && oldValue.content == null) {
                    drawer.hide(() -> {
                        sp.getChildren().clear();
                        sp.getChildren().add(newValue.content);
                    });
                    return;
                }
                sp.getChildren().clear();
                sp.getChildren().add(newValue.content);
            } else wjStage.setContent(newValue.content);
        });
    }

    private static class NavItem extends HBox {

        private final Label iconLabel = new Label();
        private final Label nameLabel = new Label();
        private final WJBadge wjBadge = new WJBadge();
        private final Node content;

        public NavItem(FontIcon fontIcon, String name, Node content) {
            this.content = content;
            iconLabel.setGraphic(fontIcon);
            nameLabel.setText(name);
            getChildren().addAll(iconLabel, nameLabel, wjBadge);
            getStyleClass().add("nav-item");
            iconLabel.getStyleClass().add("icon-label");
            nameLabel.getStyleClass().add("name-label");
        }

        public NavItem(FontIcon fontIcon, String name, Student student, WJDrawer wjDrawer) {
            this(fontIcon, name, null);
            wjBadge.setValue(student.getRequest().size());
            wjDrawer.setStudent(student);
        }
    }
}
