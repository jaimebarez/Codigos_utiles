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

import com.sun.javafx.collections.ImmutableObservableList;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Some list binding utilities
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class ListBindings {

    public static <E> ObjectBinding<E> valueAt(final ObservableList<E> op, final ObservableIntegerValue index) {
        return valueAt(op, (ObservableNumberValue) index);
    }

    public static <E> ObjectBinding<E> valueAt(final ObservableList<E> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        return new ObjectBinding<E>() {
            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected E computeValue() {
                try {
                    return op.get(index);
                } catch (IndexOutOfBoundsException ex) {
                    // Logging.getLogger().warning("Exception while evaluating binding", ex);
                }
                return null;
            }

            @Override
            public ObservableList<?> getDependencies() {
                return FXCollections.singletonObservableList(op);
            }
        };
    }

    public static <E> ObjectBinding<E> valueAt(final ObservableList<E> op, final ObservableNumberValue indexValue) {
        if ((op == null) || (indexValue == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        return new ObjectBinding<E>() {
            {
                super.bind(op, indexValue);
            }

            @Override
            public void dispose() {
                super.unbind(op, indexValue);
            }

            @Override
            protected E computeValue() {
                try {
                    return op.get(indexValue.intValue());
                } catch (IndexOutOfBoundsException ex) {
                    //Logging.getLogger().warning("Exception while evaluating binding", ex);
                }
                return null;
            }

            @Override
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, indexValue);
            }
        };
    }
}
