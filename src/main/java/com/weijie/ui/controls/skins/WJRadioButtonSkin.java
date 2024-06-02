package com.weijie.ui.controls.skins;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.ToggleButtonBehavior;
import com.weijie.ui.controls.WJRadioButton;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class WJRadioButtonSkin extends WJLabelSkin<WJRadioButton> {

    private final StackPane box;
    private final Circle radio;
    private final Circle dot;

    // 动画
    private ScaleTransition scaleTransition;

    // 可能需要添加 vm选项 ： --add-exports javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED
    private final BehaviorBase<WJRadioButton> behavior;


    public WJRadioButtonSkin(WJRadioButton control) {
        super(control);
        behavior = new ToggleButtonBehavior<>(control);

        radio = new Circle(8);
        dot = new Circle();
        dot.radiusProperty().bind(radio.radiusProperty().subtract(4));
        box = new StackPane(radio, dot);
        textProperty().bind(control.textProperty());
        setGraphic(box);
        //class
        radio.getStyleClass().add("radio");
        dot.getStyleClass().add("dot");
        box.getStyleClass().add("box");

        setAnimationInfo();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (behavior != null) {
            behavior.dispose();
        }
    }

    private void setAnimationInfo() {
        // 测试：
        WJRadioButton skinnable = getSkinnable();
        if (!skinnable.isSelected()) {
            dot.setScaleX(0);
            dot.setScaleY(0);
        }
        scaleTransition = new ScaleTransition(Duration.millis(200), dot);
        skinnable.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            scaleTransition.stop();
            scaleTransition.setToX(t1 ? 1 : 0);
            scaleTransition.setToY(t1 ? 1 : 0);
            scaleTransition.setFromX(t1 ? 0 : 1);
            scaleTransition.setFromY(t1 ? 0 : 1);
            scaleTransition.play();
        });
    }
}
