/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.javafx;
//dd/MM/YYYY
//15/09/2014

import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Helps to calculate some text width and height at the screen
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class TextSizeTool {

    private final Text helper;

    public TextSizeTool() {
        helper = new Text();
    }

    public Bounds computeTextBounds(Font font, String text) {
        helper.setText(text);
        helper.setFont(font);
        helper.applyCss();
        return helper.getLayoutBounds();
    }

    public double computeTextWidth(Font font, String text) {
        return computeTextBounds(font, text).getWidth();
    }

    public double computeTextHeight(Font font, String text) {
        return computeTextBounds(font, text).getHeight();
    }
}
