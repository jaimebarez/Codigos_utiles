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

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Some list utilities
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class ListUtilities {

    public static <T> boolean isLast(List<T> list, T element) {
        final boolean last;
        int size = list.size();
        if (size > 0) {
            last = list.get(size - 1).equals(element);
        } else {
            last = false;
        }
        return last;
    }

    public static <T> boolean isLastIndex(List<T> list, int index) {
        final boolean last;
        int size = list.size();
        if (size > 0) {
            last = index == size - 1;
        } else {
            last = false;
        }
        return last;
    }

    public static <T> boolean isFirst(List<T> list, T element) {
        final boolean first;
        int size = list.size();
        if (size > 0) {
            first = list.get(0).equals(element);
        } else {
            first = false;
        }
        return first;
    }

    public static <T> boolean isFirstIndex(List<T> list, int index) {
        final boolean last;
        int size = list.size();
        if (size > 0) {
            last = index == 0;
        } else {
            last = false;
        }
        return last;
    }

    /**
     *
     * @param <T>
     * @param list
     * @param element
     * @return
     * @throws jbarezlibs.utilities.lists.ElementIsLastException
     * @throws IndexOutOfBoundsException
     */
    public static <T> T getNext(List<T> list, T element) throws ElementIsLastException, IndexOutOfBoundsException {
        return getNextByIndex(list, list.indexOf(element));
    }

    /**
     *
     * @param <T>
     * @param list
     * @param index
     * @return
     * @throws jbarezlibs.utilities.lists.ElementIsLastException
     * @throws IndexOutOfBoundsException
     */
    public static <T> T getNextByIndex(List<T> list, int index) throws ElementIsLastException, IndexOutOfBoundsException {
        final ListIterator<T> listIterator;
        try {
            listIterator = list.listIterator(index + 1);
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
        if (listIterator.hasNext()) {
            try {
                return listIterator.next();
            } catch (NoSuchElementException ex) {
                throw new ConcurrentModificationException(
                        "List should have a next element, but another thread modified the list", ex);
            }
        } else {
            throw new ElementIsLastException();
        }
    }

    /**
     *
     * @param <T>
     * @param list
     * @param element
     * @return
     * @throws jbarezlibs.utilities.lists.ElementIsFirstException
     * @throws IndexOutOfBoundsException
     */
    public static <T> T getPrevious(List<T> list, T element) throws ElementIsFirstException, IndexOutOfBoundsException {
        return getPreviousByIndex(list, list.indexOf(element));
    }

    /**
     *
     * @param <T>
     * @param list
     * @param index
     * @return
     * @throws jbarezlibs.utilities.lists.ElementIsFirstException
     * @throws IndexOutOfBoundsException
     */
    public static <T> T getPreviousByIndex(List<T> list, int index) throws ElementIsFirstException, IndexOutOfBoundsException {
        final ListIterator<T> listIterator;
        try {
            listIterator = list.listIterator(index);
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
        if (listIterator.hasPrevious()) {
            try {
                return listIterator.previous();
            } catch (NoSuchElementException ex) {
                throw new ConcurrentModificationException(
                        "List should have a previous element, but another thread modified the list", ex);
            }
        } else {
            throw new ElementIsFirstException();
        }
    }

    public static <T> ObservableList<T> unmodifiableObservableList(List<T> list) {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(list));
    }

    public static <T> ObservableList<T> unmodifiableObservableList(T... elements) {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(elements));
    }
}
