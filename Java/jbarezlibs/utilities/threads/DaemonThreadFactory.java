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

import java.util.concurrent.ThreadFactory;

/**
 * Thread factory that returns daemon threads
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new DaemonThread(r);
    }
}
