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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * Some JavaFX utilities
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class FXUtilities {

    /**
     * Turns off pickOnBounds for node and all children in the tree
     *
     * @param node
     */
    public static void turnOffPickOnBoundsFor(Node node) {
        node.setPickOnBounds(false);
        if (node instanceof Parent) {
            for (Node c : ((Parent) node).getChildrenUnmodifiable()) {
                turnOffPickOnBoundsFor(c);
            }
        }
    }

    /**
     * Gets ReadOnlyProperties of an object looking at all the methods of the
     * object that return a readOnlyProperty. It is done by reflection
     *
     * @param anObj
     * @return
     */
    public static List<ReadOnlyProperty> getReadonlyProperties(Object anObj) {
        return getFieldsThroughMethodReturnByReflection(anObj, ReadOnlyProperty.class);
    }

    /**
     * Gets Lists of an object looking at all the methods of the object that
     * return a List. It is done by reflection
     *
     * @param anObj
     * @return
     */
    public static List<List> getLists(Object anObj) {
        return getFieldsThroughMethodReturnByReflection(anObj, List.class);
    }

    /**
     * Gets
     *
     * @param <T>
     * @param anObj
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T> List<T> getFieldsThroughMethodReturnByReflection(Object anObj, Class<T> c) {
        // Create list and get class methods
        List<T> properties = new ArrayList<>();
        Method methods[] = anObj.getClass().getMethods();

        // Iterate over methods, if method returns property, get it
        for (Method method : methods) {
            if (c.isAssignableFrom(method.getReturnType())) {
                try {
                    properties.add((T) method.invoke(anObj, new Object[0]));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
                    //System.err.println("Error: " + e);
                }
            }
        }
        // Return properties
        return properties;
    }
}
