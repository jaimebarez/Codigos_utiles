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

/**
 * An adapter class for creating a ListChangeListenerHelper. The methods in this
 * class are empty. This class exists as convenience for creating
 * ListChangeListenerHelper objects.
 *
 * @author Jaime Bárez Lobato
 * @param <T>
 */
public class ListChangeListenerHelperAdapter<T> extends ListChangeListenerHelper<T> {

    @Override
    public void reInitializate() {

    }

    @Override
    protected void added(int from, T element) {

    }

    @Override
    protected void removed(int from, T element) {

    }

    @Override
    protected void permutated() {

    }
}
