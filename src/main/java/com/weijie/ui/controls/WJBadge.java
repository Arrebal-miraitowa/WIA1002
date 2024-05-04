package com.weijie.ui.controls;

import com.weijie.ui.FXTool.ResourcesLoader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class WJBadge extends Label {

    private static final String STYLE_SHEET = ResourcesLoader.load("/cn/chenfeifx/core/css/cf-badge.css");
    private IntegerProperty valuePro = new SimpleIntegerProperty();

    public WJBadge() {
        getStyleClass().add("cf-badge");
        valuePro.addListener((observable, oldValue, newValue) -> {
            int i = newValue.intValue();
            setManaged(i != -1);
            setVisible(i != -1);
            setText(i > 9 ? "9+" : i + "");
        });
        valuePro.set(-1);// 默认不展示
    }

    public void setValue(int value) {
        if (value <= 0) {
            value = -1;
        }
        this.valuePro.set(value);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLE_SHEET;
    }
}
