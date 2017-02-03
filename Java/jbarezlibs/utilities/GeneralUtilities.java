/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities;
//dd/MM/YYYY
//12/09/2014

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Some general utilities
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class GeneralUtilities {

    public static final int SECONDS_IN_HOUR = (int) TimeUnit.HOURS.toSeconds(1);
    public static final int MILLIS_IN_HOUR = (int) TimeUnit.HOURS.toMillis(1);

    /**
     * Compares thwo numbers as Long.compare and Double.compare does
     *
     * @param n1
     * @param n2
     * @return
     */
    public static int compare(final Number n1, final Number n2) {
        int compare = Long.compare(n1.longValue(), n2.longValue());
        if (compare == 0) {
            return Double.compare(n1.doubleValue(), n2.doubleValue());
        } else {
            return compare;
        }
    }

    /**
     * Trims the given value number between a minimum and a maximum
     *
     * @param min
     * @param max
     * @param value
     * @return The trimmed number
     */
    public static double trim(double min, double max, double value) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * Trims the given value number between a minimum and a maximum
     *
     * @param min
     * @param max
     * @param value
     * @return The trimmed number
     */
    public static float trim(float min, float max, float value) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * Trims the given value number between a minimum and a maximum
     *
     * @param min
     * @param max
     * @param value
     * @return The trimmed number
     */
    public static long trim(long min, long max, long value) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * Trims the given value number between a minimum and a maximum
     *
     * @param min
     * @param max
     * @param value
     * @return The trimmed number
     */
    public static int trim(int min, int max, int value) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * ¿Why threadlocal?: The idea behind this is that SimpleDateFormat is not
     * thread-safe so in a mutil-threaded app you cannot share an instance of
     * SimpleDateFormat between multiple threads. But since creation of
     * SimpleDateFormat is an expensive operation we can use a ThreadLocal as
     * workaround
     * http://stackoverflow.com/questions/18589986/date-conversion-with-threadlocal
     */
    private static final ThreadLocal<SimpleDateFormat> hHmmssFormat;
    private static final ThreadLocal<SimpleDateFormat> hHmmssMillisFormat;

    static {
        hHmmssFormat = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("HH:mm:ss");
            }

        };
        hHmmssMillisFormat = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("HH:mm:ss.SSS");
            }

        };
    }

    /**
     * Safely converts a string to seconds
     *
     * @param hHmmss
     * @return
     * @throws ParseException
     */
    public static int safeHHmmssToSeconds(String hHmmss) throws ParseException {
        boolean negative = hHmmss.startsWith("-");
        if (negative) {
            hHmmss = hHmmss.replaceFirst("-", "");
        }
        int seconds = (int) MILLISECONDS.toSeconds(hHmmssFormat.get().parse(hHmmss).getTime());
        int HHmmssToSeconds = seconds + SECONDS_IN_HOUR;
        if (negative) {
            HHmmssToSeconds = -HHmmssToSeconds;
        }
        return HHmmssToSeconds;
    }

    /**
     * Safely converts a string to seconds
     *
     * @param hHmmssSSS
     * @return
     * @throws ParseException
     */
    public static int safeHHmmssSSSToMillis(String hHmmssSSS) throws ParseException {
        boolean negative = hHmmssSSS.startsWith("-");
        if (negative) {
            hHmmssSSS = hHmmssSSS.replaceFirst("-", "");
        }
        int millis = (int) (hHmmssMillisFormat.get().parse(hHmmssSSS).getTime());
        int HHmmssToMillis = millis + MILLIS_IN_HOUR;
        if (negative) {
            HHmmssToMillis = -HHmmssToMillis;
        }
        return HHmmssToMillis;
    }

    /**
     * Safely converts seconds to HH:mm:ss or mm:ss
     *
     * @param secs
     * @return
     */
    public static String secondsToHHOptionalmmss(Number secs) {
        return innerSecondsToHHmmss(secs, true);
    }

    public static String millisToHHOptionalmmssAndMillis(Number millis) {
        return innerSecondsToHHmmssAndMillis(millis, true);
    }

    public static String millisToHHmmssAndMillis(Number millis) {
        return innerSecondsToHHmmssAndMillis(millis, false);
    }

    public static String secondsToHHmmss(Number secs) {
        return innerSecondsToHHmmss(secs, false);
    }

    private static String innerSecondsToHHmmssAndMillis(Number millis, boolean optionalHours) {
        if (millis == null) {
            return (optionalHours ? "--:" : "") + "--:--.---";
        } else {
            int totalMillisInt = millis.intValue();
            String hhmmss = innerSecondsToHHmmss(totalMillisInt / 1000, optionalHours);
            int onlyMillis = totalMillisInt % 1000;

            return hhmmss + String.format(".%03d", onlyMillis);
        }
    }

    private static String innerSecondsToHHmmss(Number secs, boolean optionalHours) {
        if (secs == null) {
            return (optionalHours ? "--:" : "") + "--:--";
        } else {
            int secondsInt = secs.intValue();
            int secondsIntAbs = Math.abs(secondsInt);
            int seconds = secondsIntAbs % 60;
            int minutes = (secondsIntAbs / 60) % 60;
            int hours = secondsIntAbs / 3600;
            String secondsToHHmmss;
            if (optionalHours && hours == 0) {
                secondsToHHmmss = String.format("%02d:%02d", minutes, seconds);
            } else {
                secondsToHHmmss = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            }
            if (secondsInt < 0) {
                secondsToHHmmss = "-" + secondsToHHmmss;
            }
            return secondsToHHmmss;
        }
    }

    /**
     * Creates a reverse array
     *
     * @param <T>
     * @param array
     * @return
     */
    public static <T> T[] createReverseArray(T[] array) {
        List<T> asList = Arrays.asList(array);
        Collections.reverse(asList);
        return (T[]) asList.toArray();
    }

    /**
     * Rounds a number given number of decimals
     *
     * @param number
     * @param numDecimals
     * @return
     */
    public static double round(double number, int numDecimals) {
        int rounder = 10 * numDecimals;
        if (numDecimals == 0) {
            return Math.rint(number);
        } else {
            return Math.rint(number * rounder) / rounder;
        }
    }
}
