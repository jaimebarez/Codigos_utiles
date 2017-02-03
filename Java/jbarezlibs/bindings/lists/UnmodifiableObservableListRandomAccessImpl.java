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

import com.sun.javafx.collections.SourceAdapterChange;
import java.util.Collection;
import java.util.RandomAccess;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.WeakListChangeListener;

/**
 * Unmodifiable observable random access list
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 * @param <T>
 */
public class UnmodifiableObservableListRandomAccessImpl<T> extends ObservableListBase<T> implements ObservableList<T>, RandomAccess {

    private final ObservableList<T> backingList;
    private final ListChangeListener<T> listener;

    public UnmodifiableObservableListRandomAccessImpl(ObservableList<T> backingList) {
        this.backingList = backingList;
        if (!(this.backingList instanceof RandomAccess)) {
            throw new IllegalArgumentException("List must be RandomAccess");
        }
        listener = new ListChangeListener<T>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends T> c) {
                fireChange(new SourceAdapterChange<>(UnmodifiableObservableListRandomAccessImpl.this, c));
            }
        };
        this.backingList.addListener(new WeakListChangeListener<>(listener));
    }

    @Override
    public T get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public boolean addAll(T... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setAll(T... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setAll(Collection<? extends T> col) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(T... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(T... elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(int from, int to) {
        throw new UnsupportedOperationException();
    }
}
