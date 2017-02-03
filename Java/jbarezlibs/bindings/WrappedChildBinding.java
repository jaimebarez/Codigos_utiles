/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings;
//dd/MM/YYYY
//16/09/2014

import javafx.beans.value.ObservableValue;

/**
 * A WrappedChildBindingConverter that does NOT convert the desired's child
 * wrapped value.
 *
 * @param <WrappedParent>
 * @param <WrappedChild>
 * @author Jaime Bárez Lobato
 */
public abstract class WrappedChildBinding<WrappedParent, WrappedChild>
        extends WrappedChildBindingConverter<WrappedParent, WrappedChild, WrappedChild> {

    /**
     * Constructor.
     *
     * @param parentObservableValue
     */
    public WrappedChildBinding(ObservableValue<WrappedParent> parentObservableValue) {

        super(parentObservableValue);
    }

    public WrappedChildBinding(ObservableValue<WrappedParent> parentProperty, WrappedChild valuewhenParentPropertyIsNull) {
        super(parentProperty, valuewhenParentPropertyIsNull);
    }

    /**
     * @see WrappedChildBindingConverter
     * @param notNullParentWrappedValue
     * @return
     */
    @Override
    protected abstract ObservableValue<WrappedChild> getObservableChildValue(
            WrappedParent notNullParentWrappedValue);

    /**
     * Returns the given wrappedChild. No conversion is done.
     *
     * @param notNullWrappedChildValue
     * @return
     */
    @Override
    protected final WrappedChild convert(WrappedChild notNullWrappedChildValue) {

        return notNullWrappedChildValue;
    }
}
