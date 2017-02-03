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
 * An interpolator where the rate of change starts out slowly and and then
 * accelerates. Based on android.view.animation.AccelerateInterpolator.
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class AccelerateInterpolator extends Interpolator {

    private final float mFactor;
    private final double mDoubleFactor;

    public AccelerateInterpolator() {
        mFactor = 1.0f;
        mDoubleFactor = 2.0;
    }

    /**
     * Constructor
     *
     * @param factor Degree to which the animation should be eased. Seting
     * factor to 1.0f produces a y=x^2 parabola. Increasing factor above 1.0f
     * exaggerates the ease-in effect (i.e., it starts even slower and ends
     * evens faster)
     */
    public AccelerateInterpolator(float factor) {
        mFactor = factor;
        mDoubleFactor = 2 * mFactor;
    }

    public float getInterpolation(float input) {
        if (mFactor == 1.0f) {
            return input * input;
        } else {
            return (float) Math.pow(input, mDoubleFactor);
        }
    }

    @Override
    protected double curve(double input) {
        if (mFactor == 1.0f) {
            return input * input;
        } else {
            return Math.pow(input, mDoubleFactor);
        }
    }
}
