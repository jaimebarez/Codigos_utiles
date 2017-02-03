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

import javafx.beans.binding.StringExpression;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import jbarezlibs.utilities.Disposable;
import jbarezlibs.utilities.javafx.BindsPropsListnrsDisposerHelper;

/**
 * A label with a value inside
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class ValueLabel extends Label implements Disposable {

    protected final BindsPropsListnrsDisposerHelper dHelper;

    public ValueLabel(ObservableValue<?> observableValue, boolean inPlatformRunLater) {
        super();
        dHelper = new BindsPropsListnrsDisposerHelper();
        if (inPlatformRunLater) {
            bindTextPropertyInPlatformRunLater(observableValue);
        } else {
            bindTextProperty(observableValue);
        }
    }

    private void bindTextProperty(ObservableValue<?> observableValue) {

        dHelper.addAndBind(textProperty(), StringExpression.stringExpression(observableValue));
    }

    private void bindTextPropertyInPlatformRunLater(ObservableValue<?> observableValue) {

        dHelper.addAndPlatformRunLaterListen(textProperty(), StringExpression.stringExpression(observableValue));
    }

    @Override
    public void dispose() {
        dHelper.dispose();
    }
}
