package com.weijie.ui.FXTool;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ResourcesLoader {

    public static URL loadURL(String path) {
        return ResourcesLoader.class.getResource(path);
    }

    public static String load(String path) {
        URL url = loadURL(path);
        return url == null ? "null" : url.toExternalForm();
    }

    public static String loadFile(String path) {
        URL url = loadURL(path);
        return url == null ? "null" : url.toExternalForm().replaceAll("/", "\\\\").split("file:\\\\")[1];
    }

    public static InputStream loadStream(String path) {
        return ResourcesLoader.class.getResourceAsStream(path);
    }

    public static Image loadFxImage(String path) {
        return new Image(load(path));
    }

    public static ImageView loadFxImageView(String path, double width, double height) {
        ImageView imageView = new ImageView(loadFxImage(path));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
}
