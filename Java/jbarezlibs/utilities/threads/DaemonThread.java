/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.threads;
//dd/MM/YYYY
//24/07/2014

/**
 * Daemon thread.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class DaemonThread extends Thread {

    public DaemonThread() {
    }

    public DaemonThread(Runnable runnable) {
        super(runnable);
    }

    {
        setDaemon(true);
    }

}
