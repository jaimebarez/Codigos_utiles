/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.view;
//dd/MM/YYYY
//15/09/2014

import jbarezlibs.utilities.Disposable;
import jbarezlibs.utilities.javafx.BindsPropsListnrsDisposerHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import jbarezlibs.utilities.threads.EventExecutor;
import jbarezlibs.utilities.threads.PlatformTools;
import jbarezlibs.utilities.threads.SingleDaemonThreadEventExecutor;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.beans.value.ObservableNumberValue;

/**
 * Slider that notifies model with a Lag and in FavaFX Application thread
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class SliderLagAndRunlater extends Slider implements Disposable {

    //SMOOTHING positionSlider:
    private final long msDelaySlider;
    private final EventExecutor executorTimeSlider;
    private final ObservableNumberValue valueToObserv;
    private final AtomicBoolean userChangingValue;
    private final AtomicBoolean mediaPlayerTimeChangingValue;
    private final BindsPropsListnrsDisposerHelper helper;

    public SliderLagAndRunlater(long msDelaySlider, ObservableNumberValue valueToObserv) {
        this.msDelaySlider = msDelaySlider;
        this.valueToObserv = valueToObserv;
        userChangingValue = new AtomicBoolean(false);
        mediaPlayerTimeChangingValue = new AtomicBoolean(false);

        executorTimeSlider = new SingleDaemonThreadEventExecutor();
        helper = new BindsPropsListnrsDisposerHelper();

        addListenersAndBindings();
    }

    private void addListenersAndBindings() {
        helper.addAndListen(valueProperty(), new SliderValueChangeListener());
        helper.addAndListen(valueToObserv, new valueToObservChangeListener());
    }

    @Override
    public void dispose() {
        helper.dispose();
    }

    protected abstract void updateValue(double valueToUpdate);

    private class valueToObservChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, final Number t1) {

            if (t1 != null && !isValueChanging() && !userChangingValue.get()) {
                final double t1l = t1.doubleValue();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        if (!isValueChanging() && !userChangingValue.get()) {
                            mediaPlayerTimeChangingValue.set(true);
                            setValue(t1l);
                            mediaPlayerTimeChangingValue.set(false);
                        }
                    }
                });
            }
        }
    }

    private class SliderValueChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> ov, Number oldSliderValue, Number newSliderValue) {

            if (newSliderValue != null && !mediaPlayerTimeChangingValue.get()) {
                final double valueToUpdate = Math.max(0, newSliderValue.doubleValue());
                userChangingValue.set(true);
                if (!isValueChanging()) {
                    setValueChanging(true);

                    Platform.runLater(
                            new Runnable() {

                                @Override
                                public void run() {
                                    if (!mediaPlayerTimeChangingValue.get()) {
                                        updateValue(valueToUpdate);
                                    }
                                    userChangingValue.set(false);
                                    setValueChanging(false);
                                }
                            }
                    );

                } else {
                    executorTimeSlider.execute(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                PlatformTools.runLaterAndWait(
                                        new Runnable() {

                                            @Override
                                            public void run() {
                                                if (!mediaPlayerTimeChangingValue.get()) {
                                                    updateValue(valueToUpdate);
                                                }
                                                userChangingValue.set(false);
                                            }
                                        });

                                Thread.sleep(msDelaySlider);
                            } catch (InterruptedException | ExecutionException ex) {
                                Logger.getLogger(SliderLagAndRunlater.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            }
        }
    }
}
