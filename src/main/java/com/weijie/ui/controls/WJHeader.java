package com.weijie.ui.controls;

import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleControl;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;

public class WJHeader extends HBox {

    private static final String STYLE_SHEET = ResourcesLoader.load("/css/wj-header.css");
    private final ObjectProperty<HeaderStyle> headerStylePro = new SimpleObjectProperty<>(HeaderStyle.ALL);

    private final Label titleLabel = SimpleControl.getLabel("");
    private final StackPane titleBox = new StackPane(titleLabel);
    private final Button minimizeBut = getButton("minimize-but", FontIcon.of(Evaicons.MINUS), "Minimize");
    private final Button maximizeBut = getButton("maximize-but", FontIcon.of(Evaicons.EXPAND), "Maximize");
    private final Button closeBut = getButton("close-but", FontIcon.of(Evaicons.CLOSE), "Close");
    private final HBox buttonBox = new HBox(minimizeBut, maximizeBut, closeBut);

    public WJHeader() {
        initialize();
    }

    public void bindTitleText(StringProperty val) {
        titleLabel.textProperty().bind(val);
    }

    public void setMaximizeMouseClicked(Consumer<MouseEvent> consumer) {
        maximizeBut.setOnMouseClicked(consumer::accept);
    }

    public void setIconifyMouseClicked(Consumer<MouseEvent> consumer) {
        minimizeBut.setOnMouseClicked(consumer::accept);
    }

    public void setCloseMouseClicked(Consumer<MouseEvent> consumer) {
        closeBut.setOnMouseClicked(consumer::accept);
    }

    public void setHeaderStyle(HeaderStyle headerStyle) {
        this.headerStylePro.set(headerStyle);
    }

    public void setMaximizeTooltipText(String text) {
        maximizeBut.getTooltip().setText(text);
    }

    public HeaderStyle getHeaderStyle() {
        return this.headerStylePro.get();
    }

    public void setTitle(Node node) {
        titleBox.getChildren().clear();
        titleBox.getChildren().add(node);
    }

    public void doubleClickEvent(BooleanProperty booleanProperty) {
        this.setOnMouseClicked(event -> {
            if (!headerStylePro.get().equals(HeaderStyle.ALL)) return;
            if (event.getClickCount() == 2) {
                booleanProperty.set(!booleanProperty.get());
            }
        });
    }

    private void initialize() {
//        setBackground(new Background(new BackgroundFill(Color.SALMON, null, null)));
        setMaxHeight(USE_PREF_SIZE);
        getChildren().addAll(titleBox, buttonBox);
        buttonBox.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        HBox.setHgrow(titleBox, Priority.ALWAYS);
        titleLabel.setWrapText(false);
        titleLabel.paddingProperty()
                .bind(Bindings.createObjectBinding(
                        () -> new Insets(0, 0, 0, buttonBox.getWidth()), buttonBox.widthProperty()));
        getStyleClass().add("wj-header");
        buttonBox.getStyleClass().add("button-box");
        headerStylePro.addListener((observable, oldValue, newValue) -> headerStyleChange(newValue));
    }

    private Button getButton(String styleClass, FontIcon graphic, String tooltip) {
        Button button = new Button();
        button.setGraphic(graphic);
        button.setTooltip(SimpleControl.getTooltip(tooltip));
        button.getStyleClass().add(styleClass);
        return button;
    }

    private void headerStyleChange(HeaderStyle headerStyle) {
        switch (headerStyle) {
            case ICONIFY_CLOSE -> {
                showHideNode(new Node[]{minimizeBut, closeBut}, new Node[]{maximizeBut});
                getStyleClass().add("transparent");
                getStyleClass().remove("white");
            }
            case CLOSE -> showHideNode(new Node[]{closeBut}, new Node[]{minimizeBut, maximizeBut});
            case NONE -> showHideNode(new Node[]{}, new Node[]{this});
            default -> {
                showHideNode(new Node[]{closeBut, minimizeBut, maximizeBut}, new Node[]{});
                getStyleClass().remove("transparent");
                getStyleClass().add("white");
            }
        }
    }

    private void showHideNode(Node[] show, Node[] hide) {
        for (Node node : show) {
            node.setVisible(true);
            node.setManaged(true);
        }
        for (Node node : hide) {
            node.setVisible(false);
            node.setManaged(false);
        }
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLE_SHEET;
    }

    public enum HeaderStyle {
        ALL, // 显示所有按钮
        ICONIFY_CLOSE, // 最小化和关闭按钮
        CLOSE, // 关闭按钮
        NONE // 隐藏所有按钮
    }
}
