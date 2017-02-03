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

import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import jbarezlibs.bindings.StringBindingConverter;

/**
 * Binding converter that converts Paint to String
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 * @param <T>
 */
public class PaintToCssPaintStringBindingConverter<T extends Paint> extends StringBindingConverter<T> {

    public PaintToCssPaintStringBindingConverter(ObservableValue<T> observableValue) {
        super(observableValue, new PaintToCssPaintStringConverter<T>());
    }

}
