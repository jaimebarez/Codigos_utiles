/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings.numerical;
//dd/MM/YYYY
//12/09/2014

import javafx.beans.value.ObservableNumberValue;

/**
 * DoubleBinding that calculates the cosine of the value from the given
 * ObservableNumberValue.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class CosBinding extends AbstractDoubleBinding {

    public CosBinding(ObservableNumberValue observableNumberValue) {
        super(observableNumberValue);
    }

    @Override
    protected double computeValue() {
        return Math.cos(observableNumberValue.doubleValue());
    }
}
