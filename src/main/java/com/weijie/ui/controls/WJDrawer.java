package com.weijie.ui.controls;

import com.weijie.core.entities.Student;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.FXUtil;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.enums.WJLevel;
import com.weijie.ui.page.WJProfile;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * 抽屉
 */
public final class WJDrawer {

    private static final String STYLE_SHEET = ResourcesLoader.load("/css/wj-drawer.css");
    private final DoubleProperty size = new SimpleDoubleProperty();
    private final ObjectProperty<Node> center = new SimpleObjectProperty<>();
    private final ObjectProperty<Node> bottom = new SimpleObjectProperty<>();
    private final Pane parent;
    private Position position = Position.RIGHT;
    private DrawerNode drawerNode;

    private Student student;

    private Runnable setFinishedEvent;

    /**
     * 提供一个StackPane作为父组件，会将抽屉添加到父组件，在调用show()方法进行展示
     *
     * @param size：Position位置不同 size表示的参数不同：TOP（size=height）, RIGHT（size=width）, BOTTOM（size=height）, LEFT（size=width）
     * @param parent
     */
    public WJDrawer(double size, Pane parent) {
        this.size.set(size);
        this.parent = parent;
    }

    public void setStudent(Student student) {
        this.student = student;
        drawerNode = new DrawerNode();
    }

    public void show(Runnable setFinishedEvent) {
        drawerNode.show();
        this.setFinishedEvent = setFinishedEvent;
    }

    public void hide(Runnable runnable) {
        drawerNode.hide(runnable);
    }

    private class requestPane extends HBox {

        private StackPane checkmark = new StackPane(new FontIcon(Evaicons.CHECKMARK));
        private StackPane close = new StackPane(new FontIcon(Evaicons.CLOSE));
        private HBox button = new HBox(5, checkmark, close);

        public requestPane(Student s, VBox parent) {
            setCheckmarkEvent(s, parent);
            setCloseEvent(parent);
            getChildren().addAll(
                    new VBox(
                            SimpleControl.getText(s.getUsername(), "name"),
                            SimpleControl.getText("Hi~ , Can I be friends with you?", "greeting")),
                    button);
            VBox.setVgrow(button, Priority.ALWAYS);
            button.setAlignment(Pos.CENTER);
            checkmark.getStyleClass().add("checkmark");
            close.getStyleClass().add("closeMark");
            getStyleClass().add("request-pane");
        }

        private void setCheckmarkEvent(Student s, VBox parent) {
            checkmark.setOnMouseClicked(e -> {
                WJMessage.show("Friends have been added successfully", WJLevel.SUCCESS);
                parent.getChildren().remove(this);
                student.getFriends().add(s.getFriendInfo());
                student.getRequest().remove(s);
                ((Student) UserFilterService.getUser(s.getEmail())).getFriends().add(student.getFriendInfo());
                WJProfile.refresh();
            });
        }

        private void setCloseEvent(VBox parent) {
            close.setOnMouseClicked(e -> parent.getChildren().remove(this));
        }
    }

    //抽屉组件
    private class DrawerNode extends StackPane {

        private final StackPane modal = new StackPane(); // 遮罩层
        private final VBox content = new VBox(5); // 内容区域
        private final BorderPane main = new BorderPane();

        public DrawerNode() {
            initialize();
        }

        private void initialize() {
            getChildren().addAll(modal, main);
            FXUtil.clipRect(this); // 裁剪使抽屉移动时不会超出父组件
            // 主要区域
            student.getRequest().forEach(e -> content.getChildren().add(new requestPane(e, content)));
            VBox.setVgrow(content, Priority.ALWAYS);
            center.set(content);
            //遮罩层
            modal.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.3), null, null)));
            modal.setOpacity(0);
            //点击遮罩层关闭
            modal.setOnMouseClicked(event -> hide(setFinishedEvent));
            //styleClass
            main.getStyleClass().add("main");
            main.centerProperty().bind(center);
            main.bottomProperty().bind(bottom);
        }

        // 动画属性
        private final TranslateTransition TT = new TranslateTransition(Duration.millis(300), this.main);
        private final FadeTransition FT = new FadeTransition(Duration.millis(300), this.modal);
        private final ParallelTransition PT = new ParallelTransition(TT, FT);

        //从右侧展示抽屉
        public void show() {
            show(position);
        }

        //指定方向展示抽屉
        public void show(Position pos) {
            position = pos;
            setShowInfo();
            parent.getChildren().add(this);// 添加抽屉
            FT.setFromValue(0);
            FT.setToValue(1);
            PT.setInterpolator(Interpolator.LINEAR);
            PT.play();
            PT.setOnFinished((event) -> {
            });
        }

        //隐藏抽屉
        public void hide(Runnable runnable) {
            setHideInfo();
            FT.setFromValue(1);
            FT.setToValue(0);
            PT.setInterpolator(Interpolator.LINEAR);
            PT.play();
            PT.setOnFinished((event) -> {
                parent.getChildren().remove(this);
                runnable.run();
            });
        }

        private void setHideInfo() {
            double translateSize = getTranslateSize();
            switch (position) {
                case TOP, BOTTOM -> {
                    clearTranslate();
                    TT.setToY(translateSize);
                }
                case RIGHT, LEFT -> {
                    clearTranslate();
                    TT.setToX(translateSize);
                }
            }
        }

        private void setShowInfo() {
            double translateSize = getTranslateSize();
            switch (position) {
                case TOP, BOTTOM -> {
                    main.setPrefHeight(size.get());
                    main.setMaxSize(USE_COMPUTED_SIZE, USE_PREF_SIZE);
                    StackPane.setAlignment(main, Position.BOTTOM.equals(position) ? Pos.BOTTOM_CENTER : Pos.TOP_CENTER);
                    main.setTranslateY(translateSize);
                    main.setTranslateX(0);
                    clearTranslate();
                    TT.setFromY(translateSize);
                }
                case RIGHT, LEFT -> {
                    main.setPrefWidth(size.get());
                    main.setMaxSize(USE_PREF_SIZE, USE_COMPUTED_SIZE);
                    StackPane.setAlignment(main, Position.RIGHT.equals(position) ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
                    main.setTranslateX(translateSize);
                    main.setTranslateY(0);
                    clearTranslate();
                    TT.setFromX(translateSize);
                }
                default -> {
                }
            }
        }

        //清空移动动画属性
        private void clearTranslate() {
            TT.setFromX(0);
            TT.setToX(0);
            TT.setToY(0);
            TT.setFromY(0);
        }

        //获取移动量
        private double getTranslateSize() {
            double translateSize = size.get();
            if (Position.TOP.equals(position) || Position.LEFT.equals(position)) {
                translateSize = -size.get();
            }
            return translateSize;
        }

        @Override
        public String getUserAgentStylesheet() {
            return STYLE_SHEET;
        }
    }

    //抽屉出现的位置
    public enum Position {
        TOP, RIGHT, BOTTOM, LEFT
    }
}
