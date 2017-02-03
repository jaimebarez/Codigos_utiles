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

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

/**
 * Semi-automatic link between view and model.
 *
 * @param <Prop>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class ViewModelNotifier<Prop> extends ViewModelManualNotifier<Prop, Prop> {

    public ViewModelNotifier(
            Property<Prop> componentProp,
            ObservableValue<Prop> modelProp) {

        super(componentProp, modelProp);
    }

    @Override
    protected abstract void tryChangeModel(Prop newVal) throws Exception;

    @Override
    protected void changeComponentValue(Prop val) {
        setComponentProperty(val);
    }

}
