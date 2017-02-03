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

import java.text.Format;
import java.util.concurrent.Executor;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.chart.ValueAxis;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.StringConverter;
import jbarezlibs.bindings.StringBindingConverter;
import jbarezlibs.utilities.threads.SingleDaemonThreadEventRunLaterAndWaitExecutor;

/**
 * Slider with a floating bubble
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class SliderWhithBubble extends HBox {

    private static final int MILLIS_TRANSITION_VISIBLE = 200;
    private static final int MILLIS_TRANSITION_INVISIBLE = 800;
    private final Slider sliderToDecorate;
    private final DoubleProperty valueMouseOver;
    private final TextBubble textBubble;
    private final FadeTransition transitionVisible;
    private final FadeTransition transitionInvisible;
    private final Popup popup;
    private final Executor labelSetterExecutor;
    private ValueAxis<Double> sliderNumberAxis;
    private StackPane sliderTrack;

    /**
     * Creates an SliderWhithBubble whith sliderToDecorate.
     *
     * @param sliderToDecorate
     * @return
     */
    public static SliderWhithBubble create(Slider sliderToDecorate) {
        SliderWhithBubble sliderAndFloatingLabels = new SliderWhithBubble(sliderToDecorate);

        sliderAndFloatingLabels.textBubble.textProperty()
                .bind(Bindings.convert(sliderAndFloatingLabels.valueMouseOver));

        return sliderAndFloatingLabels;
    }

    public static SliderWhithBubble create(Slider sliderToDecorate, StringConverter<? extends Number> stringConverter) {
        SliderWhithBubble sliderAndFloatingLabels = new SliderWhithBubble(sliderToDecorate);

        sliderAndFloatingLabels.textBubble.textProperty()
                .bind(new StringBindingConverter(sliderAndFloatingLabels.valueMouseOver, stringConverter));
        return sliderAndFloatingLabels;
    }

    public static SliderWhithBubble create(Slider sliderToDecorate, Format format) {
        SliderWhithBubble sliderAndFloatingLabels = new SliderWhithBubble(sliderToDecorate);
        //new HHmmssNumberStringConverter<Integer>(
        sliderAndFloatingLabels.textBubble.textProperty().bind(new StringBindingConverter<>(sliderAndFloatingLabels.valueMouseOver, format));
        return sliderAndFloatingLabels;
    }

    private SliderWhithBubble(Slider sliderToDecorate) {
        labelSetterExecutor = new SingleDaemonThreadEventRunLaterAndWaitExecutor();

        valueMouseOver = new SimpleDoubleProperty();

        this.sliderToDecorate = sliderToDecorate;
        textBubble = new TextBubble("");

        transitionVisible = new FadeTransition();
        {
            transitionVisible.setDuration(Duration.millis(MILLIS_TRANSITION_VISIBLE));
            transitionVisible.setNode(textBubble);
            transitionVisible.setToValue(1.0);
            transitionVisible.setInterpolator(Interpolator.EASE_OUT);
        }

        transitionInvisible = new FadeTransition();
        {
            transitionInvisible.setNode(textBubble);
            transitionInvisible.setToValue(0.0);
            transitionInvisible.setDuration(Duration.millis(MILLIS_TRANSITION_INVISIBLE));
            transitionInvisible.setInterpolator(Interpolator.EASE_OUT);
            transitionInvisible.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            popup.hide();
                        }
                    });
                }
            });
        }

        transitionInvisible.playFromStart();

        popup = new Popup();
        {
            popup.setAutoFix(false);
            popup.setAutoHide(false);
            popup.setHideOnEscape(false);
            popup.setConsumeAutoHidingEvents(true);
            popup.getContent().add(textBubble);
        }

        getChildren().addAll(sliderToDecorate);
        HBox.setHgrow(sliderToDecorate, Priority.ALWAYS);
        addListenersAndBindings();

    }

    public StringExpression labelTextProperty() {
        return this.textBubble.textProperty();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        if (sliderTrack == null) {
            sliderTrack = (StackPane) sliderToDecorate.lookup(".track");

            if (sliderTrack != null) {
                MouseMovedEventHandler mouseMovedEventHandler = new MouseMovedEventHandler();
                sliderTrack.setOnMouseMoved(mouseMovedEventHandler);
                sliderTrack.setOnMouseDragged(new MouseDraggedEventHandler(mouseMovedEventHandler));
                sliderTrack.setOnMouseEntered(new LabelVisibleEventHandler(true));
                sliderTrack.setOnMouseExited(new LabelVisibleEventHandler(false));
            }
        }
        if (sliderNumberAxis == null) {
            sliderNumberAxis = (ValueAxis<Double>) sliderToDecorate.lookup(".axis");
        }
    }

    private void addListenersAndBindings() {
        //label.setVisible(false);

    }

    private class MouseDraggedEventHandler implements EventHandler<MouseEvent> {

        private final MouseMovedEventHandler innerHandler;

        private MouseDraggedEventHandler(MouseMovedEventHandler mouseMovedEventHandler) {
            this.innerHandler = mouseMovedEventHandler;
        }

        @Override
        public void handle(MouseEvent t) {
            if (sliderToDecorate.isValueChanging()) {
                innerHandler.handle(t);
            }
        }
    }

    private class LabelVisibleEventHandler implements EventHandler<MouseEvent> {

        private final Runnable runnableEffect;

        private LabelVisibleEventHandler(boolean visible) {
            if (visible) {
                runnableEffect = new Runnable() {

                    @Override
                    public void run() {
                        transitionInvisible.stop();
                        transitionVisible.playFromStart();
                    }
                };
            } else {
                runnableEffect = new Runnable() {

                    @Override
                    public void run() {
                        transitionVisible.stop();
                        transitionInvisible.playFromStart();
                    }
                };
            }
        }

        @Override
        public void handle(MouseEvent t) {
            Platform.runLater(runnableEffect);

        }
    }

    private class MouseMovedEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent t) {

            if (sliderNumberAxis != null && sliderTrack != null) {

                final double mouseEventScreenX = t.getScreenX();
                Point2D scenePoint = new Point2D(t.getSceneX(), t.getSceneY());
                final double numberAxisDisPlayValue
                        = sliderNumberAxis.getValueForDisplay(sliderTrack.sceneToLocal(scenePoint).getX());

                labelSetterExecutor.execute(
                        new Runnable() {

                            @Override
                            public void run() {

                                final double relocateX = mouseEventScreenX
                                - (textBubble.getWidth() / 2);

                                double sliderScreenY = sliderToDecorate
                                .localToScene(sliderToDecorate.getBoundsInLocal())
                                .getMinY();

                                Scene scene = sliderToDecorate.getScene();
                                if (scene != null) {
                                    Window window = scene.getWindow();
                                    if (window != null) {

                                        double relocateY = window.getY()
                                        + sliderScreenY - textBubble.getHeight() * 0.3;

                                        popup.show(sliderToDecorate, relocateX, relocateY);
                                        valueMouseOver.setValue(numberAxisDisPlayValue);
                                    }
                                }
                            }
                        }
                );
            }
        }
    }
}
