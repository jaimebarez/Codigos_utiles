/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.threads;
//15/09/2014

import javafx.application.Platform;

/**
 * Runs synchronization thanks to Platform.runLater
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class ListEditHelperSynchronizerInPlatformRunLater extends ListEditHelper {

    @Override
    public void runSynchronized(Runnable runnable) {
        Platform.runLater(runnable);
    }

}
