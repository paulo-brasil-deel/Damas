package br.com.poli.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InterfaceCasa extends Rectangle {
    public static final int CASA_SIZE = 40;

    private static final Color LIGHT_COLOR = Color.valueOf("#ff0");
    private static final Color DARK_COLOR = Color.valueOf("#060");

    public InterfaceCasa(boolean dark, int x, int y) {
        setFill(dark ? DARK_COLOR : LIGHT_COLOR);
        setHeight(CASA_SIZE);
        setWidth(CASA_SIZE);
        relocate(x * CASA_SIZE, y * CASA_SIZE);
    }
}
