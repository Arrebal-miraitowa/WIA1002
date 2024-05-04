package com.weijie.ui.FXTool;

import com.weijie.ui.enums.WJLevel;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SimpleControl {

    /********************************* GridPane *********************************/

    public static <T> GridPane getGridPane(List<T> data, ReadOnlyDoubleProperty property, Function<T, ? extends Pane> function) {
        GridPane gridPane = new GridPane(10, 10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column2);

        // 根据 events 列表的大小动态设置行数
        int rowCount = (int) Math.ceil(data.size() / 2.0);
        for (int i = 0; i < rowCount; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }

        // 添加 EventBlock 组件
        IntStream.range(0, data.size()).forEach(i -> gridPane.add(function.apply(data.get(i)), i % 2, i / 2));

        gridPane.prefWidthProperty().bind(property);
//        gridPane.setStyle("-fx-grid-lines-visible: true;");
        return gridPane;
    }

    /********************************* Label *********************************/

    public static Label getLabel(String text) {
        return getLabel(text, LabelEnum.TEXT_DEFAULT);
    }

    public static Label getLabel(String text, LabelEnum labelEnum) {
        Label label = new Label(text);
        label.setWrapText(true);
        setStyleClass(label, getStyleClass(labelEnum.name()));
        return label;
    }

    public enum LabelEnum {
        H1, H2, H3, H4, H5, H6, TEXT_DEFAULT, TEXT_SMALL
    }

    public static Label getMessage(String text, WJLevel WJLevel) {
        Label label = new Label(text);
        label.setGraphic(FontIcon.of(WJLevel.getIkon()));
        label.getStyleClass().addAll("wj-message", WJLevel.getStyleClass());
        return label;
    }

    /********************************* Hyperlink *********************************/

    private static final String HYPERLINK_STYLE_CLASS = "wj-link";

    public static Hyperlink getHyperlink(String text) {
        return getHyperlink(text, null);
    }

    public static Hyperlink getHyperlink(String text, WJLevel WJLevel) {
        Hyperlink hyperlink = new Hyperlink(text);
        if (WJLevel == null) {
            setStyleClass(hyperlink, HYPERLINK_STYLE_CLASS);
        } else {
            setStyleClass(hyperlink, HYPERLINK_STYLE_CLASS, getStyleClass(WJLevel.name()));
        }
        return hyperlink;
    }

    /********************************* Tooltip *********************************/

    private static final String TOOLTIP_STYLE_CLASS = "wj-tooltip";

    public static Tooltip getTooltip(String text) {
        return getTooltip(text, TooltipEnum.LIGHT);
    }

    public static Tooltip getTooltip(String text, TooltipEnum tooltipEnum) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setMaxWidth(500);
        tooltip.setWrapText(true);
        tooltip.setShowDelay(Duration.millis(200));
        setStyleClass(tooltip, TOOLTIP_STYLE_CLASS, getStyleClass(tooltipEnum.name()));
        return tooltip;
    }

    /********************************* Separator *********************************/
    private static final String SEPARATOR_STYLE_CLASS = "wj-separator";

    public static Separator getSeparator() {
        Separator separator = new Separator();
        setStyleClass(separator, SEPARATOR_STYLE_CLASS);
        return separator;
    }


    public enum TooltipEnum {
        DARK, LIGHT
    }

    public static String getStyleClass(String enumName) {
        return enumName.toLowerCase().replaceAll("_", "-");
    }

    private static void setStyleClass(Node node, String... styleClass) {
        node.getStyleClass().addAll(styleClass);
    }

    private static void setStyleClass(PopupControl popup, String... styleClass) {
        popup.getStyleClass().addAll(styleClass);
    }
}
