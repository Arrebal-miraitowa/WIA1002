package com.weijie.ui.controls.skins;

import com.weijie.ui.controls.WJTextField;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled;
import org.kordamp.ikonli.javafx.FontIcon;

public class WJTextFieldSkin extends SkinBase<WJTextField> {

    private final HBox box;
    private final Label prefixLabel;
    private final TextField textField;
    private final PasswordField passwordField;
    private final FontIcon clearIcon;
    private final Label suffixLabel;

    public WJTextFieldSkin(WJTextField control, WJTextField.Type type) {
        super(control);
        prefixLabel = new Label();// 左侧图标
        textField = new TextField();
        passwordField = new PasswordField();
        clearIcon = FontIcon.of(AntDesignIconsFilled.CLOSE_CIRCLE); // 一键清空
        suffixLabel = new Label();//右侧图标
        box = new HBox(prefixLabel, WJTextField.Type.TEXT.equals(type) ? textField : passwordField, clearIcon, suffixLabel);
        box.setAlignment(Pos.CENTER);
        HBox.setHgrow(this.textField, Priority.ALWAYS);
        HBox.setHgrow(this.passwordField, Priority.ALWAYS);
        textField.prefHeightProperty().bind(control.heightProperty());
        passwordField.prefHeightProperty().bind(control.heightProperty());
        box.getStyleClass().add("box");
        prefixLabel.getStyleClass().add("prefix-label");
        clearIcon.getStyleClass().add("clear-icon");
        suffixLabel.getStyleClass().add("suffix-label");
        clearIcon.setCursor(Cursor.HAND);
        suffixLabel.setCursor(Cursor.HAND);
        if (WJTextField.Type.PASSWORD.equals(type)) {
            control.setSuffixIcon(FontIcon.of(AntDesignIconsFilled.EYE_INVISIBLE));
        }
        // 当选中文本输入框，给当前组件添加伪类
        ChangeListener<Boolean> tChangeListener = (observable, oldValue, newValue) -> control.pseudoClassStateChanged(WJTextField.getPseudoClassFocused(), newValue);
        this.textField.focusedProperty().addListener(tChangeListener);
        this.passwordField.focusedProperty().addListener(tChangeListener);
        //
        getChildren().setAll(box);
        initEvent(control, type);
    }


    // 事件相关
    private void initEvent(WJTextField control, WJTextField.Type type) {
        // 密码框点击事件
        this.suffixLabel.setOnMouseClicked(event -> {
            if (WJTextField.Type.PASSWORD.equals(type)) {
                FontIcon graphic = (FontIcon) this.suffixLabel.getGraphic();
                Ikon iconCode = graphic.getIconCode();
                boolean isEyeInvisible = iconCode.equals(AntDesignIconsFilled.EYE_INVISIBLE);
                if (isEyeInvisible) {
                    setTextField(this.passwordField, this.textField);
                } else {
                    setTextField(this.textField, this.passwordField);
                }
                control.setSuffixIcon(FontIcon.of(isEyeInvisible ? AntDesignIconsFilled.EYE : AntDesignIconsFilled.EYE_INVISIBLE));
            }
        });
        //数据双向绑定
        this.textField.textProperty().bindBidirectional(control.textProperty());
        this.passwordField.textProperty().bindBidirectional(this.textField.textProperty());
        this.textField.promptTextProperty().bindBidirectional(control.promptTextProperty());
        this.passwordField.promptTextProperty().bindBidirectional(this.textField.promptTextProperty());
        //左侧图标
        this.prefixLabel.graphicProperty().bind(control.prefixIconProperty());
        this.prefixLabel.managedProperty().bind(control.prefixIconProperty().isNotNull());
        //右侧图标
        this.suffixLabel.graphicProperty().bind(control.suffixIconProperty());
        this.suffixLabel.managedProperty().bind(control.suffixIconProperty().isNotNull());
        //可清空的
        this.clearIcon.setOnMouseClicked(event -> control.setText(""));
        this.clearIcon.managedProperty().bind(control.clearableProperty().and(control.textProperty().isNotNull().and(control.textProperty().isNotEmpty())));
        this.clearIcon.visibleProperty().bind(this.clearIcon.managedProperty());
        //是否可编辑
        this.textField.editableProperty().bind(control.editableProperty());
        this.passwordField.editableProperty().bind(control.editableProperty());
        // focused
        control.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (WJTextField.Type.PASSWORD.equals(type)) {
                    passwordField.requestFocus();
                } else {
                    textField.requestFocus();
                }
            }
        });
        // 触发父容器的点击事件
        this.textField.setOnMouseClicked(control::fireEvent);
        this.passwordField.setOnMouseClicked(control::fireEvent);
    }

    // 输入框获取焦点，并且光标移动到最后
    private void setTextField(TextField oldTextField, TextField showTextField) {
        box.getChildren().set(1, showTextField);
        showTextField.requestFocus();
        if (showTextField.getText() != null) {
            showTextField.positionCaret(oldTextField.getCaretPosition());
        }
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
    }
}
