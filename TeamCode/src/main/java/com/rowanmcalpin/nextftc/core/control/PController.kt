package com.rowanmcalpin.nextftc.core.control

import com.rowanmcalpin.nextftc.core.control.coefficients.PDCoefficients
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDFCoefficients
import kotlin.math.abs

/**
 * This is a proportional controller that does all of the calculations internally, so you can just 
 * set the coefficient and target and call calculate(). It does the rest.
 * 
 * @param kP the proportional constant for the proportional controller
 */
open class PController(kP: Double): PDController(PDCoefficients(kP, 0.0))