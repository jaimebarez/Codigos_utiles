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
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Class used to facilitate communication of changes between model and view. The
 * view updates in Platform.runlater
 *
 * @param <ViewProp>
 * @param <ModelProp>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class ViewModelManualNotifier<ViewProp, ModelProp> implements Disposable {

    private final BindsPropsListnrsDisposerHelper helper;
    private final Property<ViewProp> viewProperty;
    private final ObservableValue<ModelProp> modelProperty;

    private boolean modelChangingPermitted = true;
    private boolean componentIsChanging = false;
    private boolean bindingsStarted = false;

    public ViewModelManualNotifier(
            Property<ViewProp> componentProp,
            ObservableValue<ModelProp> modelProp) {

        this.viewProperty = componentProp;
        this.modelProperty = modelProp;
        this.helper = new BindsPropsListnrsDisposerHelper();
    }

    public void startBindings() {
        if (!bindingsStarted) {
            bindingsStarted = true;
            changeComponentValue(modelProperty.getValue());
            helper.addAndListen(modelProperty, new ModelListener());
            helper.addAndListen(viewProperty, new ComponentListener());
        } else {
            throw new IllegalArgumentException("You can only call this once");
        }
    }

    @Override
    public void dispose() {
        helper.dispose();
    }

    protected void setComponentProperty(ViewProp componentProp) {
        this.viewProperty.setValue(componentProp);
    }

    protected void invalidViewValueReceived(Exception ex,
            ModelProp oldValue, ViewProp newVal) {
        setComponentValue(oldValue);
    }

    private void setComponentValue(final ModelProp val) {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                // Called by model, so NO while changing modelChangingPermitted
                modelChangingPermitted = false;
                changeComponentValue(val);
                modelChangingPermitted = true;
            }
        });
    }

    /**
     * Called by the view
     *
     * @param newVal
     * @throws Exception when model can't have this value
     */
    protected abstract void tryChangeModel(ViewProp newVal) throws Exception;

    /**
     * Called by the model when its value changes
     *
     * @param val
     */
    protected abstract void changeComponentValue(ModelProp val);

    private class ComponentListener implements ChangeListener<ViewProp> {

        @Override
        public void changed(ObservableValue<? extends ViewProp> ov,
                final ViewProp oldVal, ViewProp newVal) {
            componentIsChanging = true;
            if (modelChangingPermitted) {
                try {
                    tryChangeModel(newVal);
                } catch (Exception ex) {

                    modelChangingPermitted = false;
                    final ModelProp realOldVal = modelProperty.getValue();
                    ViewProp componentVal = viewProperty.getValue();
                    if (!Objects.equals(componentVal, realOldVal)) {
                        invalidViewValueReceived(ex, realOldVal, newVal);
                    }
                    modelChangingPermitted = true;
                }
            }
            componentIsChanging = false;
        }
    }

    private class ModelListener implements ChangeListener<ModelProp> {

        @Override
        public void changed(ObservableValue<? extends ModelProp> ov, ModelProp oldVal, final ModelProp newVal) {

            if (!componentIsChanging && !Objects.equals(viewProperty.getValue(), newVal)) {
                setComponentValue(newVal);
            }
        }
    }
}
