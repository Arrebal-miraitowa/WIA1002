package com.weijie.ui.controls;

import com.weijie.core.entities.Quiz;
import com.weijie.core.entities.Student;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.controls.skins.WJRadioButtonSkin;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class WJRadioButton extends RadioButton {

    private static final String STYLE_SHEET = ResourcesLoader.load("/css/wj-radio-button.css");
    private static final String STYLE_CLASS = "wj-radio-button";

    public WJRadioButton(ToggleGroup toggleGroup) {
        setToggleGroup(toggleGroup);
        initialize();
    }

    public WJRadioButton(ToggleGroup toggleGroup, Object o) {
        this(toggleGroup);
        setUserData(o);
    }

    public WJRadioButton(Student s, ToggleGroup toggleGroup) {
        this(toggleGroup);
        setText(s.getUsername());
        setUserData(s);
    }

    public WJRadioButton(String text, ToggleGroup toggleGroup) {
        this(text, toggleGroup, false);
    }

    public WJRadioButton(String text, ToggleGroup toggleGroup, boolean isQuiz) {
        this(toggleGroup);
        setText(text);
        setUserData(isQuiz ? Quiz.Theme.valueOf(text.toUpperCase()) : text);
    }

    private void initialize() {
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        getStyleClass().add(STYLE_CLASS);
    }

    @Override
    protected WJRadioButtonSkin createDefaultSkin() {
        return new WJRadioButtonSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLE_SHEET;
    }
}
