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
 * An abstract adapter class for creating a SimpleAnimationTimer. The methods in
 * this class are empty. This class exists as convenience for creating
 * SimpleAnimationTimer objects.
 *
 * @author Jaime Bárez Lobato
 */
public class SimpleAnimationTimerAdapter extends SimpleAnimationTimer {

    public SimpleAnimationTimerAdapter(Duration timerDuration) {
        super(timerDuration);
    }

    public SimpleAnimationTimerAdapter(Runnable onFinished, Duration timerDuration) {
        super(onFinished, timerDuration);
    }

    /**
     * Does nothing
     *
     * @param nanosSinceStart
     */
    @Override
    protected void handleTimeEvent(long nanosSinceStart) {

    }

    /**
     * Does nothing
     */
    @Override
    protected void animationStartIsInminent() {

    }
}
