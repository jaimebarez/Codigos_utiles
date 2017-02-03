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
 * An interpolator where the rate of change starts out quickly and and then
 * decelerates. Based on android.view.animation.DecelerateInterpolator
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class DecelerateInterpolator extends Interpolator {

    private float mFactor = 1.0f;

    public DecelerateInterpolator() {
    }

    /**
     * Constructor
     *
     * @param factor Degree to which the animation should be eased. Seting
     * factor to 1.0f produces an upside-down y=x^2 parabola. Increasing factor
     * above 1.0f makes exaggerates the ease-out effect (i.e., it starts even
     * faster and ends evens slower)
     */
    public DecelerateInterpolator(float factor) {
        mFactor = factor;
    }

    @Override
    protected double curve(double input) {

        if (mFactor == 1.0d) {
            return (1.0d - (1.0d - input) * (1.0d - input));
        } else {
            return (1.0d - Math.pow((1.0d - input), 2 * mFactor));
        }
    }
}
