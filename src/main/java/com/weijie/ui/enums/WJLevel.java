package com.weijie.ui.enums;

import javafx.scene.Node;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled;

import java.util.Arrays;
import java.util.List;

public enum WJLevel {
    PRIMARY(AntDesignIconsFilled.MESSAGE),
    SUCCESS(AntDesignIconsFilled.CHECK_CIRCLE),
    INFO(AntDesignIconsFilled.INFO_CIRCLE),
    WARN(AntDesignIconsFilled.EXCLAMATION_CIRCLE),
    DANGER(AntDesignIconsFilled.CLOSE_CIRCLE);

    WJLevel(Ikon ikon) {
        this.ikon = ikon;
    }

    private Ikon ikon;

    public Ikon getIkon() {
        return ikon;
    }

    public String getStyleClass() {
        return name().toLowerCase();
    }

    public void resetStyleClass(Node node) {
        List<String> strings = Arrays.stream(values()).map(WJLevel::getStyleClass).toList();
        node.getStyleClass().removeAll(strings);// 删除之前的StyleClass
        node.getStyleClass().add(getStyleClass()); // 添加新的StyleClass
    }
}
