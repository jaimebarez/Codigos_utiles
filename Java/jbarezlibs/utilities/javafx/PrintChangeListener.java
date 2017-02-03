/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.javafx;
//dd/MM/YYYY
//15/09/2014

import java.io.PrintStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Used to prinnt changes. Used for debugging
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class PrintChangeListener implements ChangeListener<Object> {

    private final PrintStream printStream;

    public PrintChangeListener() {
        this(System.out);
    }

    public PrintChangeListener(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void changed(ObservableValue<? extends Object> ov, Object oldV, Object newV) {

        printStream.println("---\\/\\/\\/---");
        printStream.printf("ObservableValue \"%s\" changed!", ov);
        printStream.println();
        printStream.printf("Old value = \"%s\"", oldV);
        printStream.println();
        printStream.printf("New value = \"%s\"", newV);
        printStream.println();
    }

}
