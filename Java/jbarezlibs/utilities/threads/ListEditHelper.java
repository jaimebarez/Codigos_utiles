/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.threads;
//dd/MM/YYYY
//15/09/2014

import java.util.Collection;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Centralizes all calls to a list
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class ListEditHelper {

    public ListEditHelper() {
    }

    public <T> void setAll(final ObservableList<T> list, final T... element) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                list.setAll(element);
            }
        });

    }

    public <T> void setAll(final ObservableList<T> list, final Collection<T> elements) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                list.setAll(elements);
            }
        });

    }

    public <T> void add(final List<T> list, final T element, final int index) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                list.add(index, element);
            }
        });
    }

    public <T> void addAll(final ObservableList<T> list, final T... elements) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                list.addAll(elements);
            }
        });
    }

    public <T> void remove(final List<T> list, final T element) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                list.remove(element);
            }
        });
    }

    public <T> void clear(final List<T> list) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                list.clear();
            }
        });
    }

    public <X, Y> void setValue(final XYChart.Data<X, Y> data, final X newXvalue, final Y newYvalue) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                data.setXValue(newXvalue);
                data.setYValue(newYvalue);
            }
        });
    }

    public abstract void runSynchronized(Runnable runnable);

    /**
     * If element does not exist in the list, we add it to the specified
     * position
     *
     * @param <T>
     * @param list
     * @param element
     * @param index
     */
    public <T> void addIfDoesNotExist(final List<T> list, final T element, final int index) {
        runSynchronized(new Runnable() {

            @Override
            public void run() {
                if (!list.contains(element)) {
                    list.add(index, element);
                }
            }
        });
    }

}
