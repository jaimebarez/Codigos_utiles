/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.view;
//dd/MM/YYYY
//15/09/2014

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

/**
 * A transparent separator. Simple
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class TransparentSeparator extends Separator {

    public TransparentSeparator() {
        super();
    }

    public TransparentSeparator(Orientation o) {
        super(o);
    }

    {
        setMouseTransparent(true);
        setVisible(false);
    }
}
