package com.weijie.ui.FXTool;

import javafx.geometry.Rectangle2D;

public class WJBounds {

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private double width;
    private double height;

    public static WJBounds get(Rectangle2D r) {
        return new WJBounds(r);
    }

    private WJBounds(Rectangle2D r) {
        this.minX = r.getMinX();
        this.minY = r.getMinY();
        this.maxX = r.getMaxX();
        this.maxY = r.getMaxY();
        this.width = r.getWidth();
        this.height = r.getHeight();
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "WJBounds{" +
                "minX=" + minX +
                ", minY=" + minY +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
