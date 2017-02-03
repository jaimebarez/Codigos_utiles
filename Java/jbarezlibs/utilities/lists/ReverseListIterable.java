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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Reverse list Iterable
 *
 * @param <T>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class ReverseListIterable<T> implements Iterable<T> {

    private final ListIterator<T> listIterator;

    public ReverseListIterable(List<T> wrappedList) {
        this.listIterator = wrappedList.listIterator(wrappedList.size());
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerIterator();
    }

    private class InnerIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return listIterator.hasPrevious();
        }

        @Override
        public T next() {
            return listIterator.previous();
        }

        @Override
        public void remove() {
            listIterator.remove();
        }
    }
}
