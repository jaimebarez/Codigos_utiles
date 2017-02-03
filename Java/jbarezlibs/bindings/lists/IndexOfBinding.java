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

import jbarezlibs.bindings.MCallable;
import jbarezlibs.utilities.Disposable;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a new IntegerBinding that calculates the index of a value in an
 * ObservableList.
 *
 * @author Jaime Bárez Lobato
 */
public class IndexOfBinding extends IntegerBinding implements Disposable {

    private final ObservableList<?> oList;
    private Object element;
    private ObservableValue<?> observableElement;
    private final MCallable<?> valueGetter;

    /**
     * Computed value will be the index of the element in oList.
     *
     * @param <T>
     * @param oList
     * @param element_
     */
    public <T> IndexOfBinding(ObservableList<T> oList, T element_) {

        this.oList = oList;
        this.element = element_;
        this.valueGetter = new MCallable<Object>() {

            @Override
            public Object call() {
                return element;
            }
        };
        bind(this.oList);
    }

    /**
     * Computed value will be the index of the value from the observableElement_
     * ObservableValue in oList. Whenever observableElement_, this will update
     *
     * @param <T>
     * @param oList
     * @param observableElement_
     */
    public <T> IndexOfBinding(ObservableList<T> oList, ObservableValue<T> observableElement_) {

        this.oList = oList;
        this.observableElement = observableElement_;
        this.valueGetter = new MCallable<Object>() {

            @Override
            public Object call() {
                return observableElement.getValue();
            }
        };
        bind(this.oList, observableElement);
    }

    @Override
    protected int computeValue() {

        return oList.indexOf(valueGetter.call());

    }

    @Override
    public void dispose() {

        super.dispose();
        unbind(oList);
        if (observableElement != null) {
            unbind(observableElement);
        }
    }

    @Override
    public ObservableList<?> getDependencies() {
        if (observableElement == null) {
            return FXCollections.singletonObservableList(oList);
        } else {
            return FXCollections.observableArrayList(oList, observableElement);
        }
    }
}
