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

import java.text.ParseException;
import javafx.util.StringConverter;
import jbarezlibs.bindings.MCallable;
import jbarezlibs.utilities.GeneralUtilities;

/**
 * Hours, minutes and seconds string converter. Can convert 00:00:00 or 00:00
 * style.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 * @param <T>
 */
public class HHmmssSSSNumberStringConverter<T extends Number> extends StringConverter<T> {

    private final MCallable<T> valueWhenParseException;
    private final boolean forzeAllHHmmss;

    /**
     * Constructor. Can be mmssSS or HHmmssSS
     *
     * @param valueWhenParseException Returned value when parseException.
     */
    public HHmmssSSSNumberStringConverter(T valueWhenParseException) {
        this(valueWhenParseException, false);
    }

    /**
     * Constructor
     *
     * @param valueWhenParseException Returned value when parseException.
     * @param forzeAllHHmmss Allways HHmmssSS
     */
    public HHmmssSSSNumberStringConverter(final T valueWhenParseException, boolean forzeAllHHmmss) {
        if (valueWhenParseException == null) {
            throw new IllegalArgumentException("Value when parseException must not be null");
        }
        this.valueWhenParseException = new ValueCallable<>(valueWhenParseException);
        this.forzeAllHHmmss = forzeAllHHmmss;
    }

    /**
     * Constructor. Can be mmss or HHmmss
     *
     * @param valueWhenParseExceptionCallable Returned value when
     * parseException.
     */
    public HHmmssSSSNumberStringConverter(final MCallable<T> valueWhenParseExceptionCallable) {
        this(valueWhenParseExceptionCallable, false);
    }

    /**
     * Constructor
     *
     * @param valueWhenParseExceptionCallable Returned value when
     * parseException.
     * @param forzeAllHHmmss Allways HHmmss
     */
    public HHmmssSSSNumberStringConverter(final MCallable<T> valueWhenParseExceptionCallable, boolean forzeAllHHmmss) {
        if (valueWhenParseExceptionCallable == null) {
            throw new IllegalArgumentException("Value when parseException must not be null");
        }
        this.valueWhenParseException = valueWhenParseExceptionCallable;
        this.forzeAllHHmmss = forzeAllHHmmss;
    }

    @Override
    public String toString(T t) {
        if (forzeAllHHmmss) {
            return GeneralUtilities.millisToHHmmssAndMillis(t);
        } else {
            return GeneralUtilities.millisToHHOptionalmmssAndMillis(t);
        }
    }

    @Override
    public T fromString(String string) {
        try {
            if (string != null) {

                String[] split = string.split(":");
                String hh = "00";
                String mm = "00";
                String ss = "00";
                String SSS = "000";
                if (split != null) {
                    int splitLenght = split.length;
                    int i = splitLenght;

                    if (split.length >= 1) {
                        String ssSSS = split[--i];
                        String[] splitssSS = ssSSS.split("\\.");
                        if (splitssSS.length > 0) {
                            ss = formatGood(splitssSS[0], 2);
                            if (splitssSS.length > 1) {
                                SSS = formatGood(splitssSS[1], 3);
                            }
                        }
                        if (split.length >= 2) {
                            mm = formatGood(split[--i], 2);
                            if (split.length >= 3) {
                                hh = formatGood(split[--i], 2);
                            }
                        }
                    }
                    string = hh + ":" + mm + ":" + ss + "." + SSS;
                }

            }
            return (T) Integer.valueOf(GeneralUtilities.safeHHmmssSSSToMillis(string));
        } catch (ParseException ex) {
            return (T) getValueWhenParseException();
        }
    }

    protected T getValueWhenParseException() {
        return valueWhenParseException.call();
    }

    private static String formatGood(String maybeCorrectNumOfDigits, int numDigits) {
        maybeCorrectNumOfDigits = maybeCorrectNumOfDigits.trim();
        int length = maybeCorrectNumOfDigits.length();
        if (length == 1) {
            return "0" + maybeCorrectNumOfDigits;
        } else if (length > 2) {
            maybeCorrectNumOfDigits = maybeCorrectNumOfDigits.substring(0, numDigits);
        }

        return maybeCorrectNumOfDigits;

    }

    private static class ValueCallable<T> extends MCallable<T> {

        private final T valueWhenParseException;

        public ValueCallable(T valueWhenParseException) {
            this.valueWhenParseException = valueWhenParseException;
        }

        @Override
        public T call() {
            return valueWhenParseException;
        }
    }
}
