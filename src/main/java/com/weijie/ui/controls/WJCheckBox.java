package com.weijie.ui.controls;


import com.weijie.core.entities.Parent;
import com.weijie.core.entities.Student;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.controls.skins.WJCheckBoxSkin;
import javafx.scene.control.CheckBox;

public class WJCheckBox extends CheckBox {

    private static final String STYLE_SHEET = ResourcesLoader.load("/css/wj-check-box.css");
    private static final String STYLE_CLASS = "wj-check-box";

    public WJCheckBox() {
        initialize();
    }

    public WJCheckBox(String text) {
        setText(text);
        initialize();
    }

    public WJCheckBox(Parent parent, Student student) {
        this(student.getUsername());
        setOnAction(event -> {
            if (isSelected()) parent.getChildren().add(student);
            else parent.getChildren().remove(student);
        });
    }

    public WJCheckBox(String text, boolean selected) {
        setText(text);
        setSelected(selected);
        initialize();
    }

    private void initialize() {
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        getStyleClass().add(STYLE_CLASS);
    }

    @Override
    protected WJCheckBoxSkin createDefaultSkin() {
        return new WJCheckBoxSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLE_SHEET;
    }

}
