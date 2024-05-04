package com.weijie.ui.controls;

import com.weijie.ui.FXTool.AnimationUtils;
import com.weijie.ui.FXTool.FXUtil;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.enums.WJLevel;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.Optional;

public class WJMessage {
    public static void show(String message) {
        show((Window) null, message, WJLevel.PRIMARY);
    }

    public static void show(String message, WJLevel WJLevel) {
        show((Window) null, message, WJLevel);
    }

    public static void show(Window window, String message, WJLevel WJLevel) {
        if (window == null) {
            window = FXUtil.getFocusedWindow();
        }
        Optional.ofNullable(window)
                .map(Window::getScene)
                .map(Scene::getRoot)
                .filter(root -> {
                    Class<? extends Parent> nodeClass = root.getClass();
                    return StackPane.class == nodeClass;
                })
                .map(root -> (StackPane) root)
                .ifPresentOrElse(
                        root -> addMessageNode(root, message, WJLevel),
                        () -> System.err.println("未获取到： Scene | Root | StackPane"));
    }


    public static void show(StackPane root, String message, WJLevel WJLevel) {
        addMessageNode(root, message, WJLevel);
    }

    private static void addMessageNode(StackPane root, String message, WJLevel WJLevel) {
        Label messageNode = SimpleControl.getMessage(message, WJLevel);
        messageNode.setOpacity(0);
        StackPane.setAlignment(messageNode, Pos.TOP_CENTER);
        StackPane.setMargin(messageNode, new Insets(30));
        root.getChildren().add(messageNode);
        //启动动画
        play(messageNode);
    }

    /**
     * 开始动画
     *
     * @param node
     */
    private static void play(Node node) {
        Timeline parallel = AnimationUtils.parallel(Duration.millis(200),
                new WritableValue[]{node.opacityProperty(), node.translateYProperty()},
                new Object[]{0.0, -20},
                new Object[]{1.0, 0},
                event -> {
                    // 动画完成，移除该节点。
                    node.setDisable(true); // 解决有残影问题
                    StackPane parent = (StackPane) node.getParent();
                    parent.getChildren().remove(node);
                }, true, Duration.seconds(0.5));
        parallel.play();
    }

}
