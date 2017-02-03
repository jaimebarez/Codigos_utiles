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

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.util.Builder;

/**
 * Builds JavaFX Styles
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class StyleBuilder implements Builder<String> {

    private final Map<String, String> elements;

    private static final Pattern colorsToCssColorsPattern = Pattern.compile("0x[0-9,a-f]{8}");
    private static final String URL = "url('%1$s')";
    //
    public static final String FX_BASE = "-fx-base";
    public static final String FX_PADDING = "-fx-padding";
    public static final String FX_SHAPE = "-fx-shape";
    public static final String FX_BACKGROUNDCOLOR = "-fx-background-color";
    public static final String FX_EFFECT = "-fx-effect";
    public static final String FX_BACKGROUNDIMAGE = "-fx-background-image";
    public static final String FX_OPACITY = "-fx-opacity";
    public static final String FX_COLOR = "-fx-color";
    public static final String FX_FONT_SIZE = "-fx-font-size";
    public static final String FX_TEXT_FILL = "-fx-text-fill";

    private StyleBuilder() {
        elements = new HashMap<>();
    }

    public static StyleBuilder create() {
        return new StyleBuilder();
    }

    public StyleBuilder base(Paint base) {
        elements.put(FX_BASE, colorsToCssColors(String.valueOf(base)));
        return this;
    }

    public StyleBuilder shape(String shape) {
        elements.put(FX_SHAPE, shape);
        return this;
    }

    public StyleBuilder effect(String effect) {
        elements.put(FX_EFFECT, effect);
        return this;
    }

    public StyleBuilder background(Color color) {
        elements.put(FX_BACKGROUNDCOLOR, colorToCssColor(color));
        return this;
    }

    public StyleBuilder background(Paint background) {

        elements.put(FX_BACKGROUNDCOLOR, colorsToCssColors(String.valueOf(background)));
        return this;
    }

    public StyleBuilder backgroundImage(File file) {
        System.out.println(file);
        elements.put(FX_BACKGROUNDIMAGE, String.format(URL, file.toURI().toString()));
        return this;
    }

    public StyleBuilder fontSize(int size) {
        elements.put(FX_FONT_SIZE, size + "pt");
        return this;
    }

    public StyleBuilder textFill(Color color) {
        elements.put(FX_TEXT_FILL, colorToCssColor(color));
        return this;
    }

    public StyleBuilder padding(Insets insets) {
        elements.put(FX_PADDING, insetsToString(insets));
        return this;
    }

    public StyleBuilder color(Color color) {
        elements.put(FX_COLOR, colorToCssColor(color));
        return this;
    }

    public StyleBuilder custom(String name, String value) {
        elements.put(name, value);
        return this;
    }

    public StyleBuilder opacity(float opacity) {
        elements.put(FX_OPACITY, String.valueOf(opacity));
        return this;
    }

    @Override
    public String build() {
        StringBuilder build = new StringBuilder();
        for (Map.Entry<String, String> entry : elements.entrySet()) {
            build
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("; ");
        }

        return build.toString();
    }

    private static String insetsToString(Insets insets) {
        return insets.getTop() + " " + insets.getRight() + " " + insets.getBottom() + " " + insets.getLeft();
    }

    /**
     * Converts a String with javaFX colors inside to css colors
     *
     * @param stringWithColors
     * @return
     */
    public static String colorsToCssColors(String stringWithColors) {

        StringBuilder builder = new StringBuilder();
        Matcher matcher = colorsToCssColorsPattern.matcher(stringWithColors);

        int lastTextIndex = 0;

        while (matcher.find()) {
            int colorStart = matcher.start();
            builder.append(stringWithColors, lastTextIndex, colorStart);

            lastTextIndex = matcher.end();
            Color colorFound = Color.valueOf(matcher.group());
            builder.append(colorToCssColor(colorFound));
        }
        builder.append(stringWithColors.substring(lastTextIndex));
        return builder.toString();
    }

    public static String colorToCssColor(final Color COLOR) {
        return null == COLOR ? "#000000" : COLOR.toString().replace("0x", "#");
    }
//    public static String colorToCssColor(final Color color) {
//
//        return new StringBuilder(19).append("rgba(")
//                .append((int) (color.getRed() * 255)).append(", ")
//                .append((int) (color.getGreen() * 255)).append(", ")
//                .append((int) (color.getBlue() * 255)).append(", ")
//                .append(color.getOpacity()).append(")").toString();
//    }

    public static LinearGradient createSimpleLinearGradient(Color... colors) {

        List<Stop> stops = new LinkedList<>();
        if (colors != null) {
            int numColors = colors.length;
            if (numColors > 0) {

                double offset = numColors == 1 ? 0 : (1 / (numColors - 1));
                for (int i = 0; i < numColors; i++) {
                    Color color = colors[i];
                    Stop stop = new Stop(offset * i, color);
                    stops.add(stop);
                }
            }
        }

        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        return linearGradient;
    }

}
