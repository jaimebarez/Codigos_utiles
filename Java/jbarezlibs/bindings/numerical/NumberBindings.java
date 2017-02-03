/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings.numerical;
//dd/MM/YYYY
//12/09/2014

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import jbarezlibs.bindings.MCallable;

/**
 * Some Binding utilities to fill gaps in javafx.beans.binding.Bindings.
 *
 * @author Jaime Bárez Lobato
 */
public class NumberBindings {

    /**
     * Returns a StringBinding that updates with the int value of the given
     * numberBinding.
     *
     * @param numberBinding
     * @return A StringBinding that updates with the int value of the given
     * numberBinding.
     */
    public static StringBinding createIntegerStringBinding(final NumberExpression numberBinding) {

        return Bindings.createStringBinding(
                new MCallable<String>() {

                    @Override
                    public String call() {
                        return Integer.toString(numberBinding.intValue());
                    }
                }, numberBinding);
    }

    /**
     * Returns an IntegerBinding that updates with the Integer value of the
     * given observableNumberValue if it is not null. If the
     * observableNumberValue value is null, it updates with valueWhenNull.
     *
     * @param observableNumberValue
     * @param valueWhenNull
     * @return An IntegerBinding that updates with the Integer value of the
     * given observableNumberValue if it is not null. If the
     * observableNumberValue value is null, it updates with valueWhenNull.
     */
    public static IntegerBinding createIntegerBinding(
            final ObservableValue<? extends Number> observableNumberValue,
            final int valueWhenNull) {

        return Bindings.createIntegerBinding(
                new MCallable<Integer>() {

                    @Override
                    public Integer call() {
                        Number value = observableNumberValue.getValue();
                        return value == null ? valueWhenNull : value.intValue();
                    }
                }, observableNumberValue);
    }

    /**
     * Returns an LongBinding that updates with the Integer value of the given
     * observableNumberValue if it is not null. If the observableNumberValue
     * value is null, it updates with valueWhenNull.
     *
     * @param observableNumberValue
     * @param valueWhenNull
     * @return An LongBinding that updates with the Integer value of the given
     * observableNumberValue if it is not null. If the observableNumberValue
     * value is null, it updates with valueWhenNull.
     */
    public static LongBinding createLongBinding(
            final ObservableValue<? extends Number> observableNumberValue,
            final long valueWhenNull) {

        return Bindings.createLongBinding(
                new MCallable<Long>() {

                    @Override
                    public Long call() {
                        Number value = observableNumberValue.getValue();
                        return value == null ? valueWhenNull : value.longValue();
                    }
                }, observableNumberValue);
    }

    /**
     * Returns an FloatBinding that updates with the Integer value of the given
     * observableNumberValue if it is not null. If the observableNumberValue
     * value is null, it updates with valueWhenNull.
     *
     * @param observableNumberValue
     * @param valueWhenNull
     * @return An FloatBinding that updates with the Integer value of the given
     * observableNumberValue if it is not null. If the observableNumberValue
     * value is null, it updates with valueWhenNull.
     */
    public static FloatBinding createFloatBinding(
            final ObservableValue<? extends Number> observableNumberValue,
            final float valueWhenNull) {

        return Bindings.createFloatBinding(
                new MCallable<Float>() {

                    @Override
                    public Float call() {
                        Number value = observableNumberValue.getValue();
                        return value == null ? valueWhenNull : value.floatValue();
                    }
                }, observableNumberValue);
    }

    /**
     * Returns an DoubleBinding that updates with the Integer value of the given
     * observableNumberValue if it is not null. If the observableNumberValue
     * value is null, it updates with valueWhenNull.
     *
     * @param observableNumberValue
     * @param valueWhenNull
     * @return An IntegerBinding that updates with the Integer value of the
     * given observableNumberValue if it is not null. If the
     * observableNumberValue value is null, it updates with valueWhenNull.
     */
    public static DoubleBinding createDoubleBinding(
            final ObservableValue<? extends Number> observableNumberValue,
            final double valueWhenNull) {

        return Bindings.createDoubleBinding(
                new MCallable<Double>() {

                    @Override
                    public Double call() {
                        Number value = observableNumberValue.getValue();
                        return value == null ? valueWhenNull : value.doubleValue();
                    }
                }, observableNumberValue);
    }

    public static NumberBinding trim(ObservableNumberValue nb, int min, int max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, long min, long max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, float min, float max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static DoubleBinding trim(ObservableNumberValue nb, double min, double max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, ObservableNumberValue min, int max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, ObservableNumberValue min, long max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, ObservableNumberValue min, float max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static DoubleBinding trim(ObservableNumberValue nb, ObservableNumberValue min, double max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, int min, ObservableNumberValue max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, long min, ObservableNumberValue max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static NumberBinding trim(ObservableNumberValue nb, float min, ObservableNumberValue max) {
        return Bindings.min(Bindings.max(nb, min), max);
    }

    public static DoubleBinding trim(ObservableNumberValue nb, double min, ObservableNumberValue max) {
        return Bindings.max(Bindings.min(nb, max), min);
    }

    public static NumberBinding trim(ObservableNumberValue nb, ObservableNumberValue min, ObservableNumberValue max) {
        return Bindings.max(Bindings.min(nb, max), min);
    }
}
