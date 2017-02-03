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

import static javafx.beans.binding.Bindings.when;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.IntegerExpression;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a new javafx.beans.bindinisLastBinding.ObjectBindinisLastBinding that
 * calculates the element in a list at a relative position from the
 * isLastBindingiven element
 *
 * @param <T>
 * @author Jaime Bárez Lobato - jaimebarez@isLastBindingmail.com
 */
public class RelativeElementInListBinding<T> extends ObjectBinding<T> {

    private final IntegerBinding indexofBinding;
    private final ObjectBinding<T> nextVal;

    /**
     *
     *
     * @param list
     * @param positionInList
     * @param shift
     */
    public RelativeElementInListBinding(ObservableList<T> list,
            ObservableIntegerValue positionInList, int shift) {

        this(list, IntegerExpression.integerExpression(positionInList), shift);
    }

    /**
     *
     *
     * @param list
     * @param positionInList
     * @param shift
     */
    public RelativeElementInListBinding(ObservableList<T> list,
            IntegerExpression positionInList, int shift) {

        indexofBinding = null;
        nextVal = when(
                positionInList.greaterThanOrEqualTo(0))
                .then(
                        ListBindings.valueAt(list, positionInList.add(shift)))
                .otherwise(
                        (T) null);
        bind(nextVal);
    }

    public RelativeElementInListBinding(ObservableList<T> list, T value, int shift) {

        indexofBinding = new IndexOfBinding(list, value);
        nextVal = when(
                indexofBinding.greaterThanOrEqualTo(0))
                .then(
                        ListBindings.valueAt(list, indexofBinding.add(shift)))
                .otherwise(
                        (T) null);
        bind(nextVal);
    }

    public RelativeElementInListBinding(ObservableList<T> list,
            ObservableValue<T> observableValue, int shift) {

        indexofBinding = new IndexOfBinding(list, observableValue);
        nextVal = when(
                indexofBinding.greaterThanOrEqualTo(0))
                .then(
                        ListBindings.valueAt(list, indexofBinding.add(shift)))
                .otherwise(
                        (T) null);
        bind(nextVal);
    }

    @Override
    protected T computeValue() {

        return nextVal.get();
    }

    @Override
    public void dispose() {

        super.dispose();

        unbind(nextVal);
        nextVal.dispose();
        if (indexofBinding != null) {
            indexofBinding.dispose();
        }
    }

    @Override
    public ObservableList<?> getDependencies() {

        return FXCollections.observableArrayList(indexofBinding, nextVal);
    }
}
