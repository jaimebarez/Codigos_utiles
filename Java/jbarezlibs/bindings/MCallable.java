/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings;
//dd/MM/YYYY
//12/09/2014

import java.util.concurrent.Callable;

/**
 * Callable that doesn't throw exception.
 *
 * @param <T>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public abstract class MCallable<T> implements Callable<T> {

    @Override
    public abstract T call();
}
