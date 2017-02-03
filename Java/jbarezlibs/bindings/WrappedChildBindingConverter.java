/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings;
//dd/MM/YYYY
//16/09/2014

import java.util.Objects;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is perfect for this use case:
 *
 * Imagine you have an (ObservableValue<Person> observablePerson). And class
 * Person has some fields: (ObservableValue<String> observableName) and an
 * (ObservableAge<Date> age).<br/><br/>
 *
 * If the view of the program has two labels, which show the name and age of the
 * current Person in observablePerson, you will have to add a listener to the
 * observablePerson to bind and unbind the properties you want to attach to
 * these labels.<br/><br/>
 *
 * Whith WrappedChildBindingConverter, you only bind the views once. No needed
 * to add listeners or suscribe and unsuscribe when observableContainer's
 * wrapped value changes.<br/><br/>
 *
 * In addition, this class provides child values conversion for example, if you
 * want to convert from Date to String.<br/><br/>
 * Example:<br/><br/>
 *
 * <pre>
 * WrappedChildBindingConverter<Parent, Date, String> labelText =
 * new WrappedChildBindingConverter<Parent, Date,String>(observablePerson){
 *
 *
 * .     @Override protected ObservableValue<Date> getObservableChildValue(Person notNullParentWrappedValue){
 *          return notNullParentWrappedValue.ageProperty();}
 *
 * .     @Override protected String convert(Date notNullWrappedChildValue){
 *          return myFormatter.format(notNullWrappedChildValue);}<br/>
 * };
 * </pre>
 *
 * myLabel.textProperty().bind(labelText);
 *
 *
 * @param <WrappedParent>
 * @param <WrappedChildNotConverted>
 * @param <WrappedChildConverted>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class WrappedChildBindingConverter<WrappedParent, WrappedChildNotConverted, WrappedChildConverted>
        extends ObjectBinding<WrappedChildConverted> {

    private final ObservableValue<WrappedParent> parentProperty;

    private final ObservableList<ObservableValue<?>> dependencies;

    private ObservableValue<WrappedChildNotConverted> childProperty;
    private WrappedParent oldParentValue;
    private final WrappedChildConverted valuewhenParentPropertyIsNull;
    private final ChangeListener<Object> updater;

    /**
     * Constructor.
     *
     * @param parentProperty Parent Property with child properties we want to
     * bind to. valuewhenParentPropertyIsNull will be null
     */
    public WrappedChildBindingConverter(ObservableValue<WrappedParent> parentProperty) {
        this(parentProperty, null);
    }

    /**
     * Constructor.
     *
     * @param parentProperty Parent Property with child properties we want to
     * bind to.
     * @param valuewhenParentPropertyIsNull
     */
    public WrappedChildBindingConverter(ObservableValue<WrappedParent> parentProperty, WrappedChildConverted valuewhenParentPropertyIsNull) {
        this.valuewhenParentPropertyIsNull = valuewhenParentPropertyIsNull;
        this.parentProperty = parentProperty;
        this.dependencies = FXCollections.observableArrayList();
        this.dependencies.add(this.parentProperty);
        this.oldParentValue = null;
        bind(this.parentProperty);

        this.updater = new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> ov, Object t, Object t1) {
                //Nos cargamos el lazy
            }
        };
        this.addListener(updater);
    }

    /**
     * You must override this to implement the obtainment of the desired
     * ObservableValue from a not null parent wrapped value. This is called when
     * the parentObservableValue invalidates.
     *
     * @param notNullParentWrappedValue
     * @return
     */
    protected abstract ObservableValue<WrappedChildNotConverted>
            getObservableChildValue(WrappedParent notNullParentWrappedValue);

    /**
     * You must override this to implement the conversion of the child's value.
     *
     * @param notNullWrappedChildValue
     * @return
     */
    protected abstract WrappedChildConverted convert(WrappedChildNotConverted notNullWrappedChildValue);

    private void checkValueAndUpdateIfNecessary() {
        WrappedParent currentParentValue = parentProperty.getValue();
        //Check:
        if (!Objects.equals(oldParentValue, currentParentValue)) {
            //Update is necessary
            //We know parent wrapped value has changed
            oldParentValue = currentParentValue;
            if (childProperty != null) {
                unbind(childProperty);
                dependencies.remove(childProperty);
                childProperty = null;
            }
            if (currentParentValue != null) {
                childProperty = getObservableChildValue(currentParentValue);
                bind(childProperty);
                dependencies.add(childProperty);
            }
        }
    }

    @Override
    protected void onInvalidating() {
        super.onInvalidating();
        checkValueAndUpdateIfNecessary();
    }

    /**
     * Computes the desired obtaining value. It is called when parent or child
     * ObservableValue invalidates
     *
     * @return
     */
    @Override
    protected final WrappedChildConverted computeValue() {

        checkValueAndUpdateIfNecessary();

        final WrappedChildConverted wrappedChildConverted;
        if (childProperty != null) {
            WrappedChildNotConverted wrappedChildNotConverted = childProperty.getValue();

            if (wrappedChildNotConverted != null) {
                wrappedChildConverted = convert(wrappedChildNotConverted);
            } else {
                wrappedChildConverted = valueWhenChildPropertyIsNull();
            }
        } else {
            wrappedChildConverted = valueWhenParentPropertyIsNull();
        }
        return wrappedChildConverted;
    }

    /**
     * Disables this object, unbinding all its internal dependencies.
     */
    @Override
    public final void dispose() {

        super.dispose();

        for (ObservableValue<?> observableValue : dependencies) {
            unbind(observableValue);
        }
        dependencies.clear();
        this.removeListener(updater);

    }

    /**
     * Returns the dependencies of a binding in an unmodifiable ObservableList.
     * The implementation is optional. The main purpose of this method is to
     * support developers during development. It allows to explore and monitor
     * dependencies of a binding during runtime.
     *
     * @return An unmodifiable ObservableList of the dependencies.
     */
    @Override
    public final ObservableList<ObservableValue<?>> getDependencies() {

        return FXCollections.unmodifiableObservableList(dependencies);
    }

    /**
     * Returns the default value(null) when child property is null. You can
     * override this to extend functionality
     *
     * @return The default value when child property is null.
     */
    protected WrappedChildConverted valueWhenChildPropertyIsNull() {

        return null;
    }

    /**
     * Returns the default value(null) when parent property is null. You can
     * override this to extend functionality
     *
     * @return The default value when parent property is null.
     */
    protected final WrappedChildConverted valueWhenParentPropertyIsNull() {

        return valuewhenParentPropertyIsNull;
    }
}
