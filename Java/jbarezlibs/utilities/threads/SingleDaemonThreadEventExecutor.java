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

import java.util.concurrent.Executors;

/**
 * Event executor that receives one single daemon thread each time.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class SingleDaemonThreadEventExecutor extends EventExecutor {

    private static final DaemonThreadFactory DAEMON_THREAD_FACTORY = new DaemonThreadFactory();

    public SingleDaemonThreadEventExecutor() {
        super(Executors.newSingleThreadExecutor(DAEMON_THREAD_FACTORY));
    }
}
