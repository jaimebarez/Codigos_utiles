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

import jbarezlibs.utilities.Disposable;
import jbarezlibs.utilities.threads.SingleDaemonThreadEventRunLaterAndWaitExecutor;
import java.util.concurrent.Executor;
import javafx.animation.Interpolator;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;
import jbarezlibs.utilities.GeneralUtilities;
import jbarezlibs.view.interpolators.DecelerateInterpolator;

/**
 * ScrollSmoothHandler. Handles smooth scrolling
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class ScrollSmoothHandler implements Disposable {

    public static final Duration DEFAULT_SCROLL_DURATION = Duration.seconds(1);
    //
    private ScrollAnimation scrollAnim;
    private final ScrollPane scrollPane;
    private final Duration duration;
    private final Executor changeHeightExecutor;
    private final BindsPropsListnrsDisposerHelper disposerHelper;

    public ScrollSmoothHandler(ScrollPane scrollPane) {
        this(scrollPane, DEFAULT_SCROLL_DURATION);
    }

    public ScrollSmoothHandler(ScrollPane scrollPane, Duration duration) {
        this.scrollPane = scrollPane;
        this.duration = duration;
        disposerHelper = new BindsPropsListnrsDisposerHelper();
        changeHeightExecutor = disposerHelper.addDisposable(
                new SingleDaemonThreadEventRunLaterAndWaitExecutor());
    }

    public void scrollSmoothToV(double vValueTo) {
        if (scrollAnim != null) {
            scrollAnim.stop();
        }
        this.scrollAnim = new ScrollAnimation(duration, vValueTo);

        scrollAnim.start();
    }

    @Override
    public void dispose() {
        if (scrollAnim != null) {
            scrollAnim.stop();
        }
        disposerHelper.dispose();
    }

    private class ScrollAnimation extends SimpleAnimationTimer {

        private final Interpolator interpolator;
        private double oldVValue;
        private final double vValueTo;

        private ScrollAnimation(Duration seconds, double vValueTo) {
            super(seconds);
            this.vValueTo = vValueTo;

            interpolator = new DecelerateInterpolator();
        }

        @Override
        public void animationStartIsInminent() {
            oldVValue = scrollPane.getVvalue();
        }

        @Override
        public void handleTimeEvent(long nanosSinceStart) {
            double percentage = GeneralUtilities.trim(0, 1, nanosSinceStart / effectDurationNanos);
            double interpolated = interpolator.interpolate(oldVValue, vValueTo, percentage);
            final double newVValue = GeneralUtilities.trim(0, 1, interpolated);

            changeHeightExecutor.execute(new VvalueSetterRunnable(newVValue));
        }
    }

    private class VvalueSetterRunnable implements Runnable {

        private final double newVvalue;

        public VvalueSetterRunnable(double newVvalue) {
            this.newVvalue = newVvalue;
        }

        @Override
        public void run() {
            scrollPane.setVvalue(newVvalue);
        }
    }
}
