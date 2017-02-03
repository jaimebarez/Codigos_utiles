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

import javafx.util.Duration;

/**
 * Delays the call of the runnable some duration
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class DelayerRun extends Delayer {

    private final Runnable onFinishedRunnable;

    public DelayerRun(Runnable onFinishedRunnable, Duration delay) {
        super(delay);
        this.onFinishedRunnable = onFinishedRunnable;
    }

    @Override
    protected final void delayFinished() {
        onFinishedRunnable.run();
    }
}
