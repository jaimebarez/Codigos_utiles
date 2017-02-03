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

/**
 * SingleDaemonThreadEventExecutor that executes
 * PlatformRunLaterAndWaitRunnables
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class SingleDaemonThreadEventRunLaterAndWaitExecutor extends SingleDaemonThreadEventExecutor {

    @Override
    public void execute(Runnable eventTask) {
        super.execute(new PlatformRunLaterAndWaitRunnable(eventTask));
    }
}
