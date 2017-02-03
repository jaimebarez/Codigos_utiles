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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * List that has an internal observable list and caches indexof calls for
 * performance
 *
 * @author Jaime Bárez Lobato - jaimebarez@isLastmail.com
 * @param <T>
 */
public class IndexCachedObservableList<T> implements ObservableList<T>, RandomAccess {

    private final ObservableList<T> internal;
    private final HashMap<Object, Integer> indexes;

    public IndexCachedObservableList(ObservableList<T> internal) {
        this.internal = internal;
        if(!(this.internal instanceof RandomAccess)){
            throw new IllegalArgumentException("List must be random Access");
        }
        indexes = new HashMap<>();
        this.internal.addListener(
                new ListChangeListener<T>() {

                    @Override
                    public void onChanged(ListChangeListener.Change<? extends T> change) {
                        indexes.clear();
                    }
                });
    }

    @Override
    public void addListener(ListChangeListener<? super T> ll) {
        internal.addListener(ll);
    }

    @Override
    public void removeListener(ListChangeListener<? super T> ll) {
        internal.removeListener(ll);
    }

    @Override
    public boolean addAll(T... es) {
        return internal.addAll(es);
    }

    @Override
    public boolean setAll(T... es) {
        return internal.setAll(es);
    }

    @Override
    public boolean setAll(Collection<? extends T> clctn) {
        return internal.setAll(clctn);
    }

    @Override
    public boolean removeAll(T... es) {
        return internal.removeAll(es);
    }

    @Override
    public boolean retainAll(T... es) {
        return internal.retainAll(es);
    }

    @Override
    public void remove(int i, int i1) {
        internal.remove(i, i1);
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return internal.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return internal.iterator();
    }

    @Override
    public Object[] toArray() {
        return internal.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return internal.toArray(a);
    }

    @Override
    public boolean add(T e) {
        return internal.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return internal.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return internal.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return internal.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return internal.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return internal.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return internal.retainAll(c);
    }

    @Override
    public void clear() {
        internal.clear();
    }

    @Override
    public T get(int index) {
        return internal.get(index);
    }

    @Override
    public T set(int index, T element) {
        return internal.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        internal.add(index, element);
    }

    @Override
    public T remove(int index) {
        return internal.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        Integer get = indexes.get(o);
        if (get == null) {
            int indexOf = internal.indexOf(o);
            indexes.put(o, indexOf);
            return indexOf;
        }
        return get;
    }

    @Override
    public int lastIndexOf(Object o) {
        return internal.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return internal.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return internal.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return internal.subList(fromIndex, toIndex);
    }

    @Override
    public void addListener(InvalidationListener il) {
        internal.addListener(il);
    }

    @Override
    public void removeListener(InvalidationListener il) {
        internal.removeListener(il);
    }

    public static <T> IndexCachedObservableList<T> newRandomAccessList() {
        ObservableList<T> observableArrayList = FXCollections.observableArrayList();
        return new IndexCachedObservableList<>(observableArrayList);
    }

//    public static <T> IndexCachedObservableList<T> newSequentialList() {
//        ObservableList<T> observableArrayList = FXCollections.observableList(new LinkedList<T>());
//        return new IndexCachedObservableList<>(observableArrayList);
//    }
}
