/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.threads;
//dd/MM/YYYY
//15/09/2014

import javafx.application.Platform;

/**
 * Runnable that calls Platform.runLater(runnable)
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class RunLaterRunnable implements Runnable {

    private final Runnable runnable;

    public RunLaterRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        Platform.runLater(runnable);
    }

}
