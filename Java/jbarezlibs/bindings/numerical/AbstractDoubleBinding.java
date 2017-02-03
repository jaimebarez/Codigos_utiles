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
 * DoubleBinding that listen to changes on the given ObservableNumberValue. The
 * extending class will have to override computeValue() method
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class AbstractDoubleBinding extends DoubleBinding {

    protected final ObservableNumberValue observableNumberValue;

    public AbstractDoubleBinding(ObservableNumberValue observableNumberValue) {
        this.observableNumberValue = observableNumberValue;
        bind(this.observableNumberValue);
    }

    /**
     * The extending class will only have to override this method
     *
     * @return the double computed value
     */
    @Override
    protected abstract double computeValue();

    @Override
    public final void dispose() {
        super.dispose();
        unbind(observableNumberValue);
    }

    @Override
    public final ObservableList<?> getDependencies() {
        return FXCollections.singletonObservableList(observableNumberValue);
    }
}
