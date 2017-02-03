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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;

/**
 * Some platform tools
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class PlatformTools {

    /**
     * A thread that calls this, will wait until JavaFX Application Thread runs
     * the runnable in an unespecified moment in the future
     *
     * @param userRunnable
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void runLaterAndWait(final Runnable userRunnable) throws InterruptedException, ExecutionException {
        FutureTask<Object> future = new FutureTask<>(userRunnable, null);
        Platform.runLater(future);
        //Forces to wait until finished
        future.get();
    }

    /**
     * A thread that calls this, will wait until JavaFX Application Thread runs
     * the runnable in an unespecified moment in the future. Returns the
     * userCallable result
     *
     * @param <T>
     * @param userCallable
     * @throws InterruptedException
     * @throws ExecutionException
     * @return the userCallable result
     */
    public static <T> T runLaterAndWait(final Callable<T> userCallable) throws InterruptedException, ExecutionException {
        FutureTask<T> future = new FutureTask<>(userCallable);
        Platform.runLater(future);
        return future.get();
    }
}
