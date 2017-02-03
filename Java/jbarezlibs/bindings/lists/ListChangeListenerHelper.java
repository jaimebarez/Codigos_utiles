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

import javafx.collections.ListChangeListener;

/**
 * Helps developers in the repetitive task of writing ListChangeListeners by
 * extending this class.
 *
 * @author Jaime Bárez Lobato
 * @param <T>
 */
public abstract class ListChangeListenerHelper<T> implements ListChangeListener<T> {

    @Override
    public void onChanged(Change<? extends T> change) {
        while (change.next()) {
            if (change.wasPermutated()) {
                permutated();//##
            } else if (change.wasUpdated()) {
                wasUpdated();//##
            } else {

                int from = change.getFrom();
                // System.out.println("IN-added-from: " + from);
                for (T element : change.getAddedSubList()) {

                    added(from, element);//##

                    from++;
                }

                from = change.getFrom();
                for (T element : change.getRemoved()) {
                    removed(from, element);//##
                    from++;
                }
            }
        }
    }

    protected void wasUpdated() {
        reInitializate();
    }

    public abstract void reInitializate();

    protected abstract void added(int from, T element);

    protected abstract void removed(int from, T element);

    protected abstract void permutated();

}
