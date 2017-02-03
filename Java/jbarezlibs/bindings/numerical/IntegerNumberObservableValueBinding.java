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

import javafx.beans.value.ObservableValue;
import jbarezlibs.bindings.BindingConverter;

/**
 * <PRE>Converts a ObservableValue<Number>  to an ObservableValue<Integer> </PRE>
 * Can hold null
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class IntegerNumberObservableValueBinding extends BindingConverter<Number, Integer> {

    public IntegerNumberObservableValueBinding(ObservableValue<Number> observableValueFrom) {
        super(observableValueFrom);
    }

    @Override
    protected Integer convert(final Number valueFrom) {
        return valueFrom == null ? null : valueFrom.intValue();
    }
}
