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

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DoubleBinding that calculates the remainder of the division of the value from
 * the given ObservableNumberValue dividend by the given divisor.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class RemainderBinding extends DoubleBinding {

    private final int divisor;
    private ObservableNumberValue dividend;

    /**
     * Remember you can NOT divide by zero. If you try so, you'll get an
     * IllegalArgumentException
     *
     * @param dividend_
     * @param divisor
     */
    public RemainderBinding(ObservableNumberValue dividend_, int divisor) throws IllegalArgumentException {
        // super(dividend);
        this.divisor = divisor;
        if (divisor == 0) {
            throw new IllegalArgumentException("You can NOT divide by zero");
        }

        this.dividend = dividend_;
        bind(this.dividend);
    }

    @Override
    protected double computeValue() {
        return dividend.doubleValue() % divisor;
    }

    @Override
    public final void dispose() {
        super.dispose();
        unbind(dividend);
    }

    @Override
    public final ObservableList<?> getDependencies() {
        return FXCollections.singletonObservableList(dividend);
    }
}
