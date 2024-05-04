package com.weijie.ui.controls;

import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.controls.skins.WJTextFieldSkin;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

public class WJTextField extends Control {

    private static final String STYLE_SHEET = ResourcesLoader.load("/css/wj-text-field.css");
    private static final String STYLE_CLASS = "wj-text-field";
    private static final PseudoClass PSEUDO_CLASS_FOCUSED = PseudoClass.getPseudoClass("focused");// 自定义伪类
    private Type type = Type.TEXT;
    //
    private StringProperty text = new SimpleStringProperty();
    private StringProperty promptText = new SimpleStringProperty();
    private ObjectProperty<Node> prefixIcon = new SimpleObjectProperty<>();
    private ObjectProperty<Node> suffixIcon = new SimpleObjectProperty<>();
    private BooleanProperty editable = new SimpleBooleanProperty(true); // 可编辑
    private BooleanProperty clearable = new SimpleBooleanProperty(false); // 可清空的

    public WJTextField() {
        initialize();
    }

    public WJTextField(String text) {
        initialize();
        this.text.set(text);
    }

    public WJTextField(Type type) {
        this.type = type;
        initialize();
    }

    public WJTextField(Type type, FontIcon fontIcon) {
        this.type = type;
        setPrefixIcon(fontIcon);
        initialize();
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setPromptText(String promptText) {
        this.promptText.set(promptText);
    }

    public StringProperty promptTextProperty() {
        return promptText;
    }

    public void setEditable(boolean editable) {
        this.editable.set(editable);
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public boolean isEditable() {
        return editable.get();
    }

    public void setClearable(boolean clearable) {
        this.clearable.set(clearable);
    }

    public BooleanProperty clearableProperty() {
        return clearable;
    }

    public ObjectProperty<Node> prefixIconProperty() {
        return prefixIcon;
    }

    public void setPrefixIcon(Node prefixIcon) {
        this.prefixIcon.set(prefixIcon);
    }

    public void setSuffixIcon(Node suffixIcon) {
        this.suffixIcon.set(suffixIcon);
    }

    public ObjectProperty<Node> suffixIconProperty() {
        return suffixIcon;
    }

    /**
     * 右侧图标点击事件
     *
     * @param mouseEventEventHandler
     */
    public void setSuffixIconOnMouseClicked(EventHandler<MouseEvent> mouseEventEventHandler) {
        suffixIcon.get().setOnMouseClicked(mouseEventEventHandler);
    }

    public static PseudoClass getPseudoClassFocused() {
        return PSEUDO_CLASS_FOCUSED;
    }

    private void initialize() {
        setMaxSize(USE_COMPUTED_SIZE, USE_PREF_SIZE);
        getStyleClass().add(STYLE_CLASS);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new WJTextFieldSkin(this, this.type);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLE_SHEET;
    }

    public enum Type {
        TEXT, PASSWORD
    }
}
