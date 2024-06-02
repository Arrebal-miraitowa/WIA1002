package com.weijie.ui.controls;

import com.weijie.core.entities.User;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.FXUtil;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.WJBounds;
import com.weijie.ui.page.WJLogin;
import com.weijie.ui.page.WJMid;
import com.weijie.ui.page.WJSidebar;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WJStage extends Stage {

    private static final String ROOT_STYLE_SHEET = ResourcesLoader.load("/css/wj-base.css");
    private final ObjectProperty<Insets> insetsPro = new SimpleObjectProperty<>(new Insets(10));
    private final BooleanProperty maximizePro = new SimpleBooleanProperty(false);
    private final DoubleProperty arcPro = new SimpleDoubleProperty(6); // 窗口圆角程度
    private double arc;
    private double height;
    private double width;
    private WJBounds wjBounds;

    private final BorderPane content = new BorderPane(); // 内容区域
    private HBox container = new HBox(new StackPane(), content);
    private final StackPane backdrop = new StackPane(container); // 背景区域，为了展示阴影效果
    private final StackPane root = new StackPane(backdrop); // 根节点
    private final Scene scene = new Scene(root);

    private final WJHeader wjHeader = new WJHeader();

    public WJStage() {}

    public WJStage(double width, double height) {
//        setUserData(new UserFilterService());
        UserFilterService.refresh();
        this.width = width;
        this.height = height;
        initialize();
    }

    public BorderPane getContent() {
        return content;
    }

    public WJHeader getWjHeader() {
        return wjHeader;
    }

    public StackPane getRoot() {
        return root;
    }

    public Insets getInsetsPro() {
        return insetsPro.get();
    }

    final public void setContent(Node main) {
        this.content.setCenter(main);
    }

    final public WJStage setHeaderStyle(WJHeader.HeaderStyle headerStyle) {
        wjHeader.setHeaderStyle(headerStyle);
        return this;
    }

    /**
     * 设置背景图片:默认背景为白色
     */
    final public WJStage setBackdropImage(Image image) {
        BackgroundSize backgroundSize = new BackgroundSize(-1, -1, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, null, null, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);
        this.container.setBackground(background);
        return this;
    }

    public void toLoginPage() {
        root.getChildren().clear();
        close();
        UserFilterService.writeToFile();
        new WJStage(800, 500)
                .setHeaderStyle(WJHeader.HeaderStyle.ICONIFY_CLOSE)
                .setBackdropImage(ResourcesLoader.loadFxImage("/img/in-the-early-morning-of-forest-wallpaper.jpg"))
                .show();
    }

    public void toMainPage(String email) {
        width = 1100;
        height = 650;
        setMinWidth(width);
        setMinHeight(height);
        UserFilterService.setUser(email);
        centerOnScreen();
        setHeaderStyle(WJHeader.HeaderStyle.ALL);
//        content.getChildren().clear();
        content.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F8F9FA"), null, null)));
//        container.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        container.getChildren().set(0, new WJSidebar(this));
    }

    public void toMainPage(User user, WJMid wjMid) {
        root.getChildren().remove(wjMid);
        UserFilterService.addUser(user);
        toMainPage(null);
    }

    private void initialize() {
//        setTitle("STEM");
        getIcons().add(ResourcesLoader.loadFxImage("/img/logo.png"));
        initStyle(StageStyle.TRANSPARENT);
        scene.setFill(null);
        scene.getStylesheets().add(ROOT_STYLE_SHEET);
        setScene(scene);
        root.setBackground(null);
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        HBox.setHgrow(content, Priority.ALWAYS);
        //裁剪为圆角
        FXUtil.clipRect(container, arcPro);
        //显示阴影效果
        backdrop.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(0, 0, 0, 0.5), 10, 0, 1.5, 1.5));
        this.root.setPrefHeight(height + insetsPro.get().getBottom() * 2);
        this.root.setPrefWidth(width + insetsPro.get().getBottom() * 2);
        this.root.paddingProperty().bind(insetsPro);
        // header
        content.setTop(wjHeader);
        content.setCenter(new WJLogin(this));
        wjHeader.bindTitleText(titleProperty());
        headerEvent();
        stageMove();
        centerOnScreen();
    }

    @Override
    public void centerOnScreen() {
        setX((Screen.getPrimary().getVisualBounds().getWidth() - (width + insetsPro.get().getBottom() * 2)) / 2);
        setY((Screen.getPrimary().getVisualBounds().getHeight() - (height + insetsPro.get().getBottom() * 2)) / 2);
    }

    /**
     * 窗口事件监听
     */
    private void headerEvent() {
        wjHeader.doubleClickEvent(maximizePro);
        wjHeader.setMaximizeMouseClicked(event -> maximizePro.set(!maximizePro.get()));
        maximizePro.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                wjBounds = WJBounds.get(new Rectangle2D(getX(), getY(), getWidth(), getHeight()));
                arc = arcPro.get();
                //获取主屏幕的可视边界
                WJBounds visualBounds = WJBounds.get(Screen.getPrimary().getVisualBounds());
                insetsPro.set(new Insets(0));
                arcPro.set(0);
                setWidth(visualBounds.getWidth());
                setHeight(visualBounds.getHeight());
                setX(visualBounds.getMinX());
                setY(visualBounds.getMinY());
            } else {
                insetsPro.set(new Insets(10));
                arcPro.set(arc);
                setWidth(wjBounds.getWidth());
                setHeight(wjBounds.getHeight());
                setX(wjBounds.getMinX());
                setY(wjBounds.getMinY());
            }
            wjHeader.setMaximizeTooltipText(newValue ? "Reduce" : "Maximize");
        });
        wjHeader.setIconifyMouseClicked(event -> setIconified(true));
        wjHeader.setCloseMouseClicked(event -> {
            close();
            UserFilterService.writeToFile();
        });
    }

    private double xOffset;
    private double yOffset;

    private void stageMove() {
        this.wjHeader.setOnMousePressed(event -> {
            event.consume();
            this.xOffset = this.getX() - event.getScreenX();
            this.yOffset = this.getY() - event.getScreenY();
        });
        this.wjHeader.setOnMouseDragged(event -> {
            event.consume();
            if (!maximizePro.get()) {
                this.setX(event.getScreenX() + this.xOffset);
                this.setY(event.getScreenY() + this.yOffset);
            }
        });
    }
}
