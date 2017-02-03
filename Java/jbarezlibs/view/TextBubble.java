/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.view;
//dd/MM/YYYY
//15/09/2014

import jbarezlibs.utilities.javafx.StyleBuilder;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * Component that imitates a text bubble.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class TextBubble extends VBox {

    private static final Color TOP_COLOR = Color.WHITE;
    private static final Color BOTTOM_COLOR = Color.SILVER;
    private static final Color SHADOW_COLOR = Color.BLACK;
    private static final int H_BORDER = 10;
    private static final int V_BORDER = 5;
    private static final int BOTTOM_HEIGHT = 10;

    private static final String BOTTOM_SHAPE
            = "\"M 0 -20 L 0 -15 L 30 -15 L 40 0 L 50 -15 L 80 -15 L 80 -20 z\"";

    private final Label label;

    public TextBubble(String text) {
        setMouseTransparent(true);

        this.label = new Label();
        {
            label.setText(text);
            label.setAlignment(Pos.CENTER);
            label.setStyle(
                    StyleBuilder.create()
                    .background(Color.TRANSPARENT)
                    .fontSize(17)
                    .build());
        }

        Paint rectanglePaint
                = new LinearGradient(
                        0.5,
                        0,
                        0.5,
                        1,
                        true,
                        CycleMethod.REPEAT,
                        new Stop(1, BOTTOM_COLOR),
                        new Stop(0.5, TOP_COLOR));

        Rectangle border = new Rectangle(0, 0, rectanglePaint) {
            {
                widthProperty().bind(label.widthProperty().add(H_BORDER));
                heightProperty().bind(label.heightProperty().add(V_BORDER));
            }
        };

        StackPane labelAndBorder = new StackPane(border, label);

        Region bottom = new Region();
        {
            bottom.setPrefHeight(BOTTOM_HEIGHT);
            bottom.setStyle(StyleBuilder.create()
                    .background(BOTTOM_COLOR)
                    .shape(BOTTOM_SHAPE)
                    .build());
            bottom.setMouseTransparent(true);
        }

        getChildren().addAll(labelAndBorder, bottom);

        VBox.setVgrow(bottom, Priority.NEVER);
        minHeightProperty().bind(label.heightProperty().add(bottom.heightProperty()));

        setEffect(new InnerShadow(10, SHADOW_COLOR));
    }

    public StringProperty textProperty() {
        return this.label.textProperty();
    }
}
