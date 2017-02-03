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

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a new javafx.beans.binding.BooleanBinding that calculates if the
 * given element is the last element in the given list.
 *
 * @author Jaime Bárez Lobato
 */
public class IsLastBinding extends BooleanBinding {

    private final BooleanBinding isLast;
    private final IntegerBinding listSize;

    public <T> IsLastBinding(ObservableList<T> oList, T element) {

        listSize = Bindings.size(oList);
        isLast = listSize
                .greaterThanOrEqualTo(0)
                .and(
                        ListBindings.valueAt(oList, listSize.subtract(1))
                        .isEqualTo(element)
                );
        bind(isLast);
    }

    public <T> IsLastBinding(ObservableList<T> oList, ObservableObjectValue<T> element) {

        listSize = Bindings.size(oList);
        isLast = listSize
                .greaterThanOrEqualTo(0)
                .and(
                        ListBindings.valueAt(oList, listSize.subtract(1))
                        .isEqualTo(((ObservableObjectValue<T>) element))
                );
        bind(isLast);
    }

    public IsLastBinding(ObservableList<?> oList, ObservableIntegerValue positionInList) {

        listSize = Bindings.size(oList);
        isLast = listSize
                .greaterThanOrEqualTo(0)
                .and(
                        ListBindings.valueAt(oList, listSize.subtract(1))
                        .isEqualTo(ListBindings.valueAt(oList, positionInList))
                );
        bind(isLast);
    }

    public IsLastBinding(ObservableList<?> oList, int positionInList) {

        listSize = Bindings.size(oList);
        isLast = listSize
                .greaterThanOrEqualTo(0)
                .and(
                        ListBindings.valueAt(oList, listSize.subtract(1))
                        .isEqualTo(ListBindings.valueAt(oList, positionInList))
                );
        bind(isLast);

    }

    @Override
    protected boolean computeValue() {

        return isLast.get();
    }

    @Override
    public void dispose() {

        super.dispose();
        unbind(isLast);
        isLast.dispose();
        listSize.dispose();
    }

    @Override
    public ObservableList<?> getDependencies() {

        return FXCollections.observableArrayList(isLast, listSize);
    }
}
