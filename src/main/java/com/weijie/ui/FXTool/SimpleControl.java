package com.weijie.ui.FXTool;

import com.weijie.ui.enums.WJLevel;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SimpleControl {

    /********************************** Text **********************************/

    public static Text getText(String s, String style) {
        Text t = new Text(s);
        t.getStyleClass().add(style);
        return t;
    }

    /******************************** GridPane ********************************/

    public static <T> GridPane getGridPane(double var1, double var2, List<T> data, Function<T, ? extends Pane> function) {
        GridPane gridPane = new GridPane(var1, var2);
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

        IntStream.range(0, data.size()).forEach(i -> gridPane.add(function.apply(data.get(i)), i % 2, i / 2));

//        gridPane.setStyle("-fx-grid-lines-visible: true;");
        return gridPane;
    }

    public static <T> GridPane getGridPane(List<T> data, Function<T, ? extends Pane> function) {
        return getGridPane(10, 10, data, function);
    }

    public static <T> GridPane getGridPane(List<T> data, ObservableValue property, Function<T, ? extends Pane> function) {
        GridPane g = getGridPane(data, function);
        g.prefWidthProperty().bind(property);
//        g.setBackground(new Background(new BackgroundFill(Color.TAN, null, null)));
        return g;
    }

    /********************************* Label *********************************/

    public static Label getLabel(String text) {
        return getLabel(text, LabelEnum.TEXT_DEFAULT, true);
    }

    public static Label getLabel(String text, LabelEnum labelEnum) {
        return getLabel(text, labelEnum, true);
    }

    public static Label getLabel(String text, LabelEnum labelEnum, boolean isWrapText) {
        Label label = new Label(text);
        label.setWrapText(isWrapText);
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
