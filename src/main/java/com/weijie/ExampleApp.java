package com.weijie;

import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.controls.WJHeader;
import com.weijie.ui.controls.WJStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class ExampleApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        WJStage stage = new WJStage(800, 500)
                .setHeaderStyle(WJHeader.HeaderStyle.ICONIFY_CLOSE)
                .setBackdropImage(ResourcesLoader.loadFxImage("/img/in-the-early-morning-of-forest-wallpaper.jpg"));
        stage.show();
    }
}
