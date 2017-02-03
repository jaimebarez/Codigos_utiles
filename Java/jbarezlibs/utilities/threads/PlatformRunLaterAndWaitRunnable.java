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

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runnable that calls PlatformTools.runLaterAndWait(runnable)
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class PlatformRunLaterAndWaitRunnable implements Runnable {

    private final Runnable runnable;

    public PlatformRunLaterAndWaitRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            PlatformTools.runLaterAndWait(runnable);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PlatformRunLaterAndWaitRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
