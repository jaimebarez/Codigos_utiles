/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.javafx;
//dd/MM/YYYY
//15/09/2014

import com.google.common.collect.LinkedListMultimap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import jbarezlibs.utilities.Disposable;
import jbarezlibs.utilities.lists.ReverseListIterable;

/**
 * Wen removing a Node with lots of bindings in favaFX, it is very annoying to
 * unbind and dispose everything when the node is not necessary anymore. If you
 * add a new binding to the Node, then you shouldn't forget to unbind and
 * dispose it when the node is not useful anymore. If you don't do so, you have
 * CPU and memory leaks. This class helps with it. If you register in it all the
 * bindings, at the end it is very easy to unbind everything, simply calling
 * dispose()
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class BindsPropsListnrsDisposerHelper implements Disposable {

    private final List<Property<?>> properties;
    private final List<ObservableValue<?>> bindings;
    private final List<Disposable> disposables;
    private final LinkedListMultimap<ObservableValue<?>, ChangeListener> changeListenersMap;
    private final LinkedListMultimap<WritableValue<?>, ObservableValue<?>> listenMap;
    private final LinkedListMultimap<ObservableValue<?>, InvalidationListener> invalidationListenersMap;

    /**
     * Constructor.
     */
    public BindsPropsListnrsDisposerHelper() {
        properties = new ArrayList<>();
        bindings = new ArrayList<>();
        disposables = new ArrayList<>();
        changeListenersMap = LinkedListMultimap.create();
        invalidationListenersMap = LinkedListMultimap.create();
        listenMap = LinkedListMultimap.create();
    }

    /**
     * Adds the property and the observableValue to the list, and binds the
     * property to the observableValue. When dispose() is called, if
     * observableValue is instanceof binding, dispose() of it will be called
     *
     * @param <T>
     * @param property
     * @param observableValue
     */
    public <T> void addAndBind(Property<T> property, ObservableValue<? extends T> observableValue) {
        property.bind(observableValue);
        properties.add(property);
        if (!bindings.contains(observableValue)) {
            bindings.add(observableValue);
        }
    }

    /**
     * Adds the property and the changeListener to the list, and adds the
     * listener to the observableValue
     *
     * @param <T>
     * @param changeListener
     * @param observableValue
     */
    public <T> void addAndListen(ObservableValue<T> observableValue,
            ChangeListener<? super T> changeListener) {
        observableValue.addListener(changeListener);
        changeListenersMap.put(observableValue, changeListener);
    }

    /**
     * Adds the property and the changeListener to the list, and adds the
     * listener to the observableValue
     *
     * @param invalidationListener
     * @param observableValue
     */
    public void addAndListen(ObservableValue<?> observableValue,
            InvalidationListener invalidationListener) {
        observableValue.addListener(invalidationListener);
        invalidationListenersMap.put(observableValue, invalidationListener);
    }

    /**
     * Adds the binding to the list
     *
     * @param <T>
     * @param binding
     * @return the binding. Nothing changes
     */
    public <T extends Binding<?>> T addBinding(T binding) {
        bindings.add(binding);
        return binding;
    }

    /**
     * Adds the disposable to the list
     *
     * @param <T>
     * @param disposable
     * @return the disposable. Nothing changes
     */
    public <T extends Disposable> T addDisposable(T disposable) {
        disposables.add(disposable);
        return disposable;
    }

    @Override
    public void dispose() {
        removeAllBindingsAndListeners(true);
    }

    public void unbindAllWihoutDisposing() {
        removeAllBindingsAndListeners(false);
    }

    private void removeAllBindingsAndListeners(boolean dispose) {
        for (Iterator<Property<?>> it = new ReverseListIterable<>(properties).iterator();
                it.hasNext();) {

            Property<?> nextProperty = it.next();
            nextProperty.unbind();
            it.remove();
        }
        properties.clear();//Defensive programming

        for (Iterator<ObservableValue<?>> it = new ReverseListIterable<>(bindings).iterator();
                it.hasNext();) {

            ObservableValue<?> nextBinding = it.next();
            if (dispose && nextBinding instanceof Binding) {
                ((Binding<?>) nextBinding).dispose();
            }
            it.remove();
        }
        bindings.clear();//Defensive programming

        for (Iterator<Disposable> it = new ReverseListIterable<>(disposables).iterator();
                it.hasNext();) {

            Disposable nextDisposable = it.next();
            if (dispose) {
                nextDisposable.dispose();
            }
            it.remove();
        }
        disposables.clear();//Defensive programming

        for (Iterator<Entry<ObservableValue<?>, ChangeListener>> it
                = changeListenersMap.entries().iterator();
                it.hasNext();) {

            Entry<ObservableValue<?>, ChangeListener> next = it.next();
            next.getKey().removeListener(next.getValue());
            it.remove();
        }
        changeListenersMap.clear();//Defensive programming

        for (Iterator<Entry<ObservableValue<?>, InvalidationListener>> it
                = invalidationListenersMap.entries().iterator();
                it.hasNext();) {

            Entry<ObservableValue<?>, InvalidationListener> next = it.next();
            next.getKey().removeListener(next.getValue());
            it.remove();
        }
        invalidationListenersMap.clear();//Defensive programming

        listenMap.clear();
    }

    /**
     * writableValue will be set in Platform.runlater() thread when
     * observableValue changes. Initial value is set in current thread. To work
     * properly with platformRunLaterUnListen, this method sould be only called
     * once to writableValue
     *
     * @param <T>
     * @param writableValue
     * @param observableValue
     * @return for debugging
     */
    public <T> ChangeListener<T> addAndPlatformRunLaterListen(final WritableValue<T> writableValue,
            final ObservableValue<T> observableValue) {

        return addAndListen(writableValue, observableValue, new WritableValueChangeListenerRunLater<>(writableValue));
    }

    /**
     * writableValue will be set when observableValue changes. To work properly
     * with platformRunLaterUnListen, this method sould be only called once to
     * writableValue
     *
     * @param <T>
     * @param writableValue
     * @param observableValue
     * @return for debugging
     */
    public <T> ChangeListener<T> addAndListen(final WritableValue<T> writableValue,
            final ObservableValue<T> observableValue) {

        return addAndListen(writableValue, observableValue, new WritableValueChangeListener<>(writableValue));
    }

    private <T> ChangeListener<T> addAndListen(final WritableValue<T> writableValue,
            final ObservableValue<T> observableValue, WritableValueChangeListener<T> changeListener) {

        addAndListen(observableValue, changeListener);

        T startVal = observableValue.getValue();
        writableValue.setValue(startVal);
        listenMap.put(writableValue, observableValue);

        return changeListener;
    }

    /**
     * UnListens a previous call to addAndPlatformRunLaterListen
     *
     * @param <T>
     * @param writableValue
     * @return never null
     */
    public <T> List<ObservableValue<T>> unListen(final WritableValue<T> writableValue) {

        if (writableValue instanceof ObservableValue) {
            ObservableValue<T> get = (ObservableValue<T>) writableValue;

            {
                List<ChangeListener> remove = changeListenersMap.get(get);
                if (remove != null) {
                    for (ChangeListener changeListener : remove) {
                        get.removeListener(changeListener);
                    }
                }
                changeListenersMap.removeAll(get);
            }
            {
                List<InvalidationListener> remove = invalidationListenersMap.get(get);
                if (remove != null) {
                    for (InvalidationListener invalidationListener : remove) {
                        get.removeListener(invalidationListener);
                    }
                }
                invalidationListenersMap.removeAll(get);
            }
        }
        //
        {
            List<ObservableValue<?>> get = listenMap.get(writableValue);
            if (get != null) {

                for (ObservableValue<?> observableValue : get) {
                    List<ChangeListener> remove = changeListenersMap.get(observableValue);
                    if (remove != null) {
                        for (ChangeListener changeListener : remove) {
                            observableValue.removeListener(changeListener);
                        }
                    }
                    changeListenersMap.removeAll(observableValue);
                }
                Object o = listenMap.removeAll(writableValue);
                return (List<ObservableValue<T>>) o;
            }
        }
        return Collections.emptyList();
    }

    private static class WritableValueChangeListener<T> implements ChangeListener<T> {

        private final WritableValue<T> writableValue;

        public WritableValueChangeListener(WritableValue<T> writableValue) {
            this.writableValue = writableValue;
        }

        @Override
        public void changed(ObservableValue<? extends T> ov, T oldVal, final T newVal) {
            writeValue(newVal);
        }

        protected void writeValue(T newVal) {
            if (!Objects.equals(writableValue.getValue(), newVal)) {

                writableValue.setValue(newVal);
            }
        }
    }

    private static class WritableValueChangeListenerRunLater<T> extends WritableValueChangeListener<T> {

        public WritableValueChangeListenerRunLater(WritableValue<T> writableValue) {
            super(writableValue);
        }

        @Override
        protected void writeValue(final T newVal) {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    WritableValueChangeListenerRunLater.super.writeValue(newVal);
                }
            });
        }
    }
}
