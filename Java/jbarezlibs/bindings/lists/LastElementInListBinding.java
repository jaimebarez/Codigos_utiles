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
import javafx.collections.ObservableList;

/**
 * ObjectBinding that calculates the last element in a list
 *
 * @param <T>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class LastElementInListBinding<T> extends RelativeElementInListBinding<T> {

    public LastElementInListBinding(ObservableList<T> list) {
        super(list, Bindings.size(list), -1);
    }
}
