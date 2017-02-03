/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings;
//dd/MM/YYYY
//12/09/2014

import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Binding converter
 *
 * @param <From>
 * @param <To>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class BindingConverter<From, To> extends ObjectBinding<To> {

    private final ObservableValue<From> observableValueFrom;

    public BindingConverter(ObservableValue<From> observableValueFrom) {
        this.observableValueFrom = observableValueFrom;
        bind(this.observableValueFrom);
    }

    @Override
    protected To computeValue() {
        return convert(observableValueFrom.getValue());
    }

    protected abstract To convert(From valueFrom);

    @Override
    public void dispose() {
        super.dispose();
        unbind(observableValueFrom);
    }

    @Override
    public ObservableList<?> getDependencies() {
        return FXCollections.observableArrayList(super.getDependencies(), observableValueFrom);
    }
}
