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

import jbarezlibs.utilities.Disposable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Executor which will process incoming events. This processing can take a long
 * time. Whilst that event is processing it should record incoming events and
 * then process the last incoming events when it is free to run again. (The
 * other events can be thrown away).
 *
 * Thanks to:
 * http://stackoverflow.com/questions/11306425/executor-queue-process-last-known-task-only
 *
 */
public class EventExecutor implements Executor, Disposable {

    private final Object lockShutdown = new Object();
    private final ExecutorService executor;
    /**
     * the field which keeps track of the latest available event to process
     */
    private final AtomicReference<Runnable> latestEventReference = new AtomicReference<>();
    private final AtomicInteger activeTaskCount = new AtomicInteger(0);

    public EventExecutor(final ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void execute(final Runnable eventTask) {
        // update the latest event
        latestEventReference.set(eventTask);
        // read count _after_ updating event
        final int activeTasks = activeTaskCount.get();

        if (activeTasks == 0) {
            // there is definitely no other task to process this event, create a new task
            final Runnable customTask = new Runnable() {
                @Override
                public void run() {
                    // decrement the count for available tasks _before_ reading event
                    activeTaskCount.decrementAndGet();
                    // find the latest available event to process
                    final Runnable currentTask = latestEventReference.getAndSet(null);
                    if (currentTask != null) {
                        // if such an event exists, process it
                        currentTask.run();
                    } else {
                        // somebody stole away the latest event. Do nothing.
                    }
                }
            };
            // increment tasks count _before_ submitting task
            activeTaskCount.incrementAndGet();
            synchronized (lockShutdown) {
                if (!executor.isShutdown()) {
                    try {
                        // submit the new task to the queue for processing
                        executor.execute(customTask);
                    } catch (RejectedExecutionException ex) {
                        Logger.getLogger(EventExecutor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        synchronized (lockShutdown) {
            executor.shutdown();
        }
    }
}
