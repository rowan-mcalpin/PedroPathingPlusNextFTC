package com.rowanmcalpin.nextftc.core.control

/**
 * Interface all controllers must inherit from.
 */
interface Controller {
    /**
     * The target for the reference to converge to.
     */
    var target: Double

    /**
     * Given a reference, calculates how to best match the target.
     */
    fun calculate(reference: Double): Double

    /**
     * Resets the control loop
     */
    fun reset()
}