package com.weijie.ui.FXTool;

import com.weijie.ui.controls.WJStage;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.concurrent.Callable;

public class FXUtil {


    public static String getResource(String resources) {
        return FXUtil.class.getResource(resources).toExternalForm();
    }

    public static Image getImage(String resources) {
        return new Image(getResource(resources));
    }

    public static ImageView getImageView(String resources, double size) {
        return getImageView(resources, size, size);
    }

    public static ImageView getImageView(String resources, double height, double width) {
        ImageView imageView = new ImageView(getResource(resources));
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }

    public static Bounds localToScreen(Node node) {
        return node.localToScreen(node.getLayoutBounds());
    }

    public static WJStage getWJStage(Node node) {
        return (WJStage) getWindow(node);
    }

    public static Window getFocusedWindow() {
        return Stage.getWindows().stream().filter(Window::isFocused).findFirst().orElse(null);
    }

    /**
     * 获取窗口
     *
     * @param node
     * @return
     */
    public static Window getWindow(Node node) {
        return node.getParent().getScene().getWindow();
    }

    public static Rectangle clipRect(Region region) {
        return clipRect(region, new SimpleDoubleProperty(0));
    }

    public static Rectangle clipRect(Node node, DoubleProperty bindArc) {
        Rectangle rectangle = new Rectangle();
        //rectangle.setSmooth(false);
        rectangle.widthProperty().bind(Bindings.createObjectBinding((Callable<Number>) () ->
                node.getLayoutBounds().getWidth(), node.layoutBoundsProperty()));
        rectangle.heightProperty().bind(Bindings.createObjectBinding((Callable<Number>) () ->
                node.getLayoutBounds().getHeight(), node.layoutBoundsProperty()));
        rectangle.arcWidthProperty().bind(bindArc);
        rectangle.arcHeightProperty().bind(bindArc);
        node.setClip(rectangle);
        return rectangle;
    }
}
