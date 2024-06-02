package com.weijie.ui.controls;

import com.weijie.ui.FXTool.SimpleControl;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class WJForm extends GridPane {

    public WJForm() {
        setHgap(20);
        setVgap(20);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
//        setStyle("-fx-grid-lines-visible: true;-fx-background-color: rgb(165,68,68)");
    }

    public WJForm(double hGap, double vGap) {
        setHgap(hGap);
        setVgap(vGap);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    }

    public void addTitle(String text, int rowIndex) {
        add(SimpleControl.getLabel(text, SimpleControl.LabelEnum.H4), 0, rowIndex);
    }

    public void add(Node node, int rowIndex, HPos hPos, VPos vPos) {
        GridPane.setHalignment(node, hPos);
        GridPane.setValignment(node, vPos);
        wjAdd(null, node, rowIndex, HPos.RIGHT, VPos.TOP);
    }

    public void add(String text, Node node, int rowIndex) {
        wjAdd(text, node, rowIndex, HPos.RIGHT, VPos.TOP);
    }

    public void addNodes(Node[] nodes, int rowIndex) {
        addNodes(null, nodes, rowIndex);
    }

    /**
     * 将多个node添加到FlowPane
     */
    public void addNodes(String text, Node[] nodes, int rowIndex) {
        FlowPane flowPane = new FlowPane(nodes);
        flowPane.hgapProperty().bind(hgapProperty());
        flowPane.vgapProperty().bind(vgapProperty().divide(2));
        wjAdd(text, flowPane, rowIndex, HPos.RIGHT, VPos.TOP);
    }

    public void wjAdd(String text, Node node, int rowIndex, HPos textHPos, VPos textVPos) {
        if (text != null) {
            Label label = SimpleControl.getLabel(text, SimpleControl.LabelEnum.H3, false);
//            label.setPrefHeight(32);
//            label.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
            GridPane.setHalignment(label, textHPos);
            GridPane.setValignment(label, textVPos);
            add(label, 0, rowIndex);
        }
        add(node, 1, rowIndex);
    }

}
