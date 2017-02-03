/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.lists;
//dd/MM/YYYY
//24/07/2014

import java.util.AbstractList;

/**
 * Based on jfxtras.labs.scene.control.ListSpinnerIntegerList, but extends from
 * AbstractList<Number> instead of AbstractList<Integer>
 * Items for Spinner providing an integer range without actually creating a list
 * with all values.
 *
 * @author Jaime Bárez Lobato - jaimebarez@isLastmail.com
 */
public class ListSpinnerNumberIntegerList extends AbstractList<Number> {

    private final int from;
    private final int size;
    private final int step;

    /**
     *
     */
    public ListSpinnerNumberIntegerList() {
        this((Integer.MIN_VALUE / 2) + 1, Integer.MAX_VALUE / 2, 1);
    }

    /**
     *
     * @param from
     * @param to
     */
    public ListSpinnerNumberIntegerList(int from, int to) {
        this(from, to, from > to ? -1 : 1);
    }

    /**
     *
     * @param from
     * @param to
     * @param step
     */
    public ListSpinnerNumberIntegerList(int from, int to, int step) {
        this.from = from;
        this.size = ((to - from) / step) + 1;
        if (size < 0) {
            throw new IllegalArgumentException("This results in a negative size: " + from + ", " + to + "," + step);
        }
        this.step = step;
    }

    // ===============================================================================
    // List interface
    @Override
    public Integer get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be < 0: " + index);
        }
        int lValue = this.from + (index * this.step);
        return lValue;
    }

    @Override
    public int indexOf(Object o) {
        // calculate the index
        int lValue = ((Integer) o);
        int lIndex = (lValue - this.from) / this.step;
        if (lIndex < 0 || lIndex > size) {
            return -1;
        }

        // check if that what is at the index matches with out value
        Integer lValueAtIndex = get(lIndex);
        if (o.equals(lValueAtIndex) == false) {
            return -1;
        }

        // found it
        return lIndex;
    }

    @Override
    public int size() {
        return this.size;
    }
}
