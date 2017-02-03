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

import javafx.scene.paint.Paint;
import javafx.util.StringConverter;

/**
 * Stringconverter that converts Paint to String
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 * @param <T>
 */
public class PaintToCssPaintStringConverter<T extends Paint> extends StringConverter<T> {

    @Override
    public String toString(T t) {
        return t == null ? null : StyleBuilder.colorsToCssColors(t.toString());
    }

    @Override
    public T fromString(String string) {

        return string == null ? null : (T) T.valueOf(string);
    }
}
