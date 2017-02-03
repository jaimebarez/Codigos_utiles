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

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import jbarezlibs.utilities.threads.DaemonThread;
import java.util.concurrent.atomic.AtomicReference;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;

/**
 * Helps developers create a simple and easy to implement AnimationTimer
 *
 * @author Jaime Bárez Lobato
 */
public abstract class SimpleAnimationTimer extends AnimationTimer {

    protected double nanosTimeToFinish;
    protected final double effectDurationNanos;
    private final AtomicReference<Runnable> onFinished;
    private long startNanoTime;
    private final AtomicReference<StarterThread> lastThread;

    /**
     * Constructor
     *
     * @param onFinished
     * @param timerDuration
     */
    public SimpleAnimationTimer(Runnable onFinished, Duration timerDuration) {
        this.effectDurationNanos = NANOSECONDS.convert((long) timerDuration.toMillis(), MILLISECONDS);

        this.onFinished = new AtomicReference<>(onFinished);
        lastThread = new AtomicReference<>();
    }

    /**
     * Constructor
     *
     * @param timerDuration
     */
    public SimpleAnimationTimer(Duration timerDuration) {
        this(null, timerDuration);
    }

    /**
     * Replaces current onFinished runnable.
     *
     * @param onFinished
     */
    public void setOnFinished(Runnable onFinished) {
        this.onFinished.set(onFinished);
    }

    /**
     * Called by parent AnimationTimer
     *
     * @param l
     */
    @Override
    public final void handle(long l) {

        if (l >= nanosTimeToFinish) {
            stop();
            Runnable runnable = onFinished.get();
            if (runnable != null) {
                runnable.run();
            }
        } else {
            handleTimeEvent(l - startNanoTime);
        }
    }

    @Override
    public final void stop() {

        StarterThread oldThread = lastThread.getAndSet(null);
        if (oldThread != null) {
            oldThread.tryStop();
        }
        super.stop();
    }

    /**
     * Called by an StarterThread
     */
    private void realStart(StarterThread starterThread) {
        if (!starterThread.tryStop) {
            startNanoTime = System.nanoTime();
            nanosTimeToFinish = effectDurationNanos + System.nanoTime();
            animationStartIsInminent();
            super.start();
        }
    }

    /**
     * Starts with zero delay
     */
    @Override
    public final void start() {
        start(Duration.ZERO);
    }

    public final void start(Duration delay) {
        final long millisDelay = (long) delay.toMillis();

        StarterThread starterThread = new StarterThread(millisDelay);
        lastThread.set(starterThread);
        starterThread.start();
    }

    /**
     * Called each timer time event
     *
     * @param nanosSinceStart
     */
    protected abstract void handleTimeEvent(long nanosSinceStart);

    /**
     * Called just before starting timer
     */
    protected abstract void animationStartIsInminent();

    /**
     * This thread sleeps some milliseconds and then starts the timer
     */
    private class StarterThread extends DaemonThread {

        private final long millisDelay;
        private boolean tryStop;

        private StarterThread(long millisDelay) {
            this.millisDelay = millisDelay;
            tryStop = false;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(millisDelay);
            } catch (InterruptedException ex) {

            }
            if (!tryStop) {
                realStart(this);
            }
        }

        private void tryStop() {
            this.tryStop = true;
        }
    }
}
