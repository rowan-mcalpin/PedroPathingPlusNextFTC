package com.rowanmcalpin.nextftc.core

/**
 * A [Subsystem] represents a real-world system (such as a lift, intake, or claw) that cannot be 
 * controlled by multiple commands simultaneously.
 */
interface Subsystem {

    /**
     * Initializes this subsystem. This function is perfect for calling `hardwareMap.get` or
     * otherwise initializing hardware devices. 
     */
    fun initialize() { }
}