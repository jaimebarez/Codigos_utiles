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
 * Delays the call on delayFinished() some duration
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class Delayer extends SimpleAnimationTimerAdapter {

    private final Runnable delayFinishedRunnable;

    public Delayer(Duration delay) {
        super(delay);
        delayFinishedRunnable = new Runnable() {

            @Override
            public void run() {
                delayFinished();
            }
        };
        //super!
        super.setOnFinished(delayFinishedRunnable);

    }

    protected abstract void delayFinished();

    @Override
    public void setOnFinished(Runnable onFinished) {
        super.setOnFinished(new RunAndDelayFinished(onFinished));
    }

    private class RunAndDelayFinished implements Runnable {

        private final Runnable userRunnable;

        public RunAndDelayFinished(Runnable userRunnable) {
            this.userRunnable = userRunnable;
        }

        @Override
        public void run() {
            userRunnable.run();
            delayFinishedRunnable.run();
        }
    }
}
