/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.view.interpolators;
//dd/MM/YYYY
//15/09/2014

import javafx.animation.Interpolator;

/**
 * An interpolator where the change bounces at the end. Based on
 * android.view.animation.BounceInterpolator.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class BounceInterpolator extends Interpolator {

    public BounceInterpolator() {
    }

    private static double bounce(double t) {
        return t * t * 8.0d;
    }

    @Override
    protected double curve(double d) {
        d *= 1.1226d;
        if (d < 0.3535d) {
            return bounce(d);
        } else if (d < 0.7408d) {
            return bounce(d - 0.54719d) + 0.7d;
        } else if (d < 0.9644d) {
            return bounce(d - 0.8526d) + 0.9d;
        } else {
            return bounce(d - 1.0435d) + 0.95d;
        }
    }
}
