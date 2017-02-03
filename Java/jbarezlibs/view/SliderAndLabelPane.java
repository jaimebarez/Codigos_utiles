/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.view;
//15/09/2014

import java.text.Format;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import jbarezlibs.bindings.StringBindingConverter;

/**
 * Pane with an slider and a label that represents slider's value
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class SliderAndLabelPane extends HBox {

    private static final int PADDING = 15;
    private final Slider valueSlider;
    private final Label label;

    public SliderAndLabelPane(String valueName, double minValue, double maxValue) {
        this(valueName, minValue, maxValue, null);
    }

    public SliderAndLabelPane(String valueName, double minValue, double maxValue, Format labelFormat) {
        setPadding(new Insets(PADDING));
        this.valueSlider = new Slider(minValue, maxValue, minValue);
        label = new Label();

        label.textProperty().bind(
                labelFormat == null ? StringExpression.stringExpression(valueSlider.valueProperty())
                : new StringBindingConverter<>(valueSlider.valueProperty(), labelFormat)
        );

        Label labelName = new Label();
        {
            labelName.setText(valueName + ": ");
            labelName.setFont(createTitleLabelFont());
        }
        getChildren().addAll(labelName, valueSlider, label);
        HBox.setHgrow(valueSlider, Priority.ALWAYS);
        setAlignment(Pos.CENTER);
    }

    private Font createTitleLabelFont() {
        Font aDefault = Font.getDefault();
        return aDefault;//Font.font(aDefault.getName(), FontWeight.EXTRA_BOLD, aDefault.getSize() * 1.5d);
    }

    public DoubleProperty sliderValueProperty() {
        return valueSlider.valueProperty();
    }
}
