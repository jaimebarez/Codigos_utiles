/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings.lists;
//dd/MM/YYYY
//12/09/2014

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * ObjectBinding that calculates the element at left relative to another element
 * given in a list
 *
 * @param <T>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class LeftInListBinding<T> extends RelativeElementInListBinding<T> {

    private static final int LEFT = -1;

    /**
     * Left element relative to given element will be calculated
     *
     * @param list
     * @param value
     */
    public LeftInListBinding(ObservableList<T> list, T value) {

        super(list, value, LEFT);
    }

    /**
     * Left element relative to the value of the ObservableValue will be
     * calculated
     *
     * @param list
     * @param observableValue
     */
    public LeftInListBinding(ObservableList<T> list, ObservableValue<T> observableValue) {

        super(list, observableValue, LEFT);
    }

    /**
     * Left element relative to the value of the observable Index will be
     * calculated
     *
     * @param list
     * @param observableIndex
     */
    public LeftInListBinding(ObservableList<T> list, ObservableIntegerValue observableIndex) {

        super(list, observableIndex, LEFT);
    }
}
