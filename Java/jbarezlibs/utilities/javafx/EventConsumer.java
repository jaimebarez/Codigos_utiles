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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * EventConsumer. Consumes events
 *
 * @param <T>
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class EventConsumer<T extends Event> implements EventHandler<T> {

    private final Node childNode;

    /**
     * Constructor. Events will be consumed
     */
    public EventConsumer() {
        this(null);
    }

    /**
     * Constructor. Events will be consumed, before preHandling them
     *
     * @param childNode
     */
    public EventConsumer(Node childNode) {
        this.childNode = childNode;

    }

    @Override
    public void handle(T t) {
        preHandle(t);
        t.consume();
    }

    /**
     * Fires the event to the chidNode's parent before consuming it
     *
     * @param t
     */
    protected void preHandle(T t) {
        if (childNode != null) {
            Parent parent = childNode.getParent();
            if (parent != null) {
                parent.fireEvent(t);
            }
        }
    }
}
