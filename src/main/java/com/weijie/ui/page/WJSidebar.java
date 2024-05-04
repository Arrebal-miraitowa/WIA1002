package com.weijie.ui.page;

import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJBadge;
import com.weijie.ui.controls.WJStage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class WJSidebar extends StackPane {

    private static final String STYLE_SHEET = ResourcesLoader.load("/css/wj-sidebar.css");

    private WJStage wjStage;

    private Label titleLabel = new Label("STEM");
    private ListView<NavItem> menu = new ListView<>();
    private VBox asideContainer = new VBox(titleLabel, SimpleControl.getSeparator(), menu);

    public WJSidebar(WJStage wjStage) {
        this.wjStage = wjStage;
        initialize();
        //测试数据
//        notification.getWjBadge().setValue(3);
        // 模拟数据
        menu.getItems().addAll(
            new NavItem(new FontIcon(Evaicons.CALENDAR), "Events", new WJEvent(wjStage)),
            new NavItem(new FontIcon(Evaicons.BOOK), "Quiz", new WJQuiz(wjStage))
        );
        // 默认选择第一个
        parentProperty().addListener(observable -> menu.getSelectionModel().select(0));
    }

    private void initialize() {
        getStylesheets().add(STYLE_SHEET);//加载css
        getChildren().add(asideContainer);
        VBox.setVgrow(menu, Priority.ALWAYS);
        //styleClass
        asideContainer.getStyleClass().add("aside");
        titleLabel.getStyleClass().add("title-label");
        menu.getStyleClass().addAll("scroll-bar-style", "menu");
        setEvent();
    }

    public void setEvent() {
        menuSelectedListener();
    }

    //菜单选中事件监听
    private void menuSelectedListener() {
        menu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Optional.ofNullable(newValue).ifPresent(menuItem -> wjStage.setContent(menuItem.content));
        });
    }

    public static class NavItem extends HBox {
        private Label iconLabel = new Label();
        private Label nameLabel = new Label();
        private WJBadge wjBadge = new WJBadge();
        private Node content;

        public NavItem(FontIcon fontIcon, String name, Node content) {
            this.content = content;
            iconLabel.setGraphic(fontIcon);
            nameLabel.setText(name);
            getChildren().addAll(iconLabel, nameLabel, wjBadge);
            nameLabel.setMaxSize(Double.MAX_VALUE, USE_PREF_SIZE);
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            getStyleClass().add("nav-item");
            iconLabel.getStyleClass().add("icon-label");
            nameLabel.getStyleClass().add("name-label");
            prefWidthProperty().bind(this.widthProperty().subtract(1));
        }

        public WJBadge getWjBadge() {
            return wjBadge;
        }
    }
}
