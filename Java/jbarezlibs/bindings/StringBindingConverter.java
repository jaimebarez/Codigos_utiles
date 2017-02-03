/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings;
//dd/MM/YYYY
//12/09/2014

import jbarezlibs.utilities.Disposable;
import java.text.Format;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import javafx.util.StringConverter;

/**
 * StringBinding that binds to a given observableValue, formatting it's String
 * value with a formatter.
 *
 * @author Jaime Bárez Lobato
 * @param <T>
 */
public class StringBindingConverter<T> extends StringBinding implements Disposable {

    private final ObservableValue<T> mObservableValue;
    private final MCallable<String> stringGetter;

    /**
     * Constructor.
     *
     * @param observableValue Value to bind to.
     * @param stringConverter used to convert the String
     */
    public StringBindingConverter(ObservableValue<T> observableValue,
            StringConverter<T> stringConverter) {

        this.mObservableValue = observableValue;
        this.stringGetter = new StringConverterGetter(stringConverter);
        bind(mObservableValue);
    }

    /**
     * Constructor.
     *
     * @param observableValue Value to bind to.
     * @param format Used to convert the String.
     */
    public StringBindingConverter(ObservableValue<T> observableValue, final Format format) {

        this.mObservableValue = observableValue;
        this.stringGetter = new FormatGetter(format);
        bind(this.mObservableValue);
    }

    /**
     * Returns the formatted String representation of the observableValue value.
     *
     * @return The formatted String representation of the observableValue value.
     */
    @Override
    protected String computeValue() {

        return stringGetter.call();
    }

    @Override
    public void dispose() {
        super.dispose();
        unbind(mObservableValue);
    }

    private class FormatGetter extends MCallable<String> {

        private final Format format;

        public FormatGetter(Format format) {

            this.format = format;
        }

        @Override
        public String call() {

            return format.format(mObservableValue.getValue());
        }

    }

    private class StringConverterGetter extends MCallable<String> {

        private final StringConverter<T> stringConverter;

        public StringConverterGetter(StringConverter<T> stringConverter) {

            this.stringConverter = stringConverter;
        }

        @Override
        public String call() {

            return stringConverter.toString(mObservableValue.getValue());
        }
    }
}
