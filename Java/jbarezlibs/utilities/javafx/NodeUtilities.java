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

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Window;

/**
 * NodeUtilities
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class NodeUtilities {

    public static Point2D getCoordinatesInScreen(Node node) {
        Scene scene = node.getScene();
        if (scene != null) {
            Window window = scene.getWindow();
            if (window != null) {
                Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
                Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
                double screenX = window.getX() + sceneCoord.getX() + nodeBounds.getMinX();
                double sceenY = window.getY() + sceneCoord.getY() + nodeBounds.getMinY();
                return new Point2D(screenX, sceenY);
            }
        }
        return null;
    }
}
