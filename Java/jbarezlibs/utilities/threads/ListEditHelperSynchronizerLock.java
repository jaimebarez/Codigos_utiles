/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.threads;
//15/09/2014


/**
 * Runs synchronization thanks to a lock
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class ListEditHelperSynchronizerLock extends ListEditHelper {

    private final Object lock;

    public ListEditHelperSynchronizerLock() {
        this.lock = new Object();
    }

    @Override
    public void runSynchronized(Runnable runnable) {
        synchronized (lock) {
            runnable.run();
        }
    }
}
