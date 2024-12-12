package com.rowanmcalpin.nextftc.ftc.pedro

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.FollowerNotInitializedException
import com.rowanmcalpin.nextftc.ftc.OpModeData
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain

/**
 * This Command tells the PedroPath follower to follow a specific path or pathchain
 * @param path the path to follow
 * @param holdEnd whether to actively hold position after the path is done being followed
 */
class FollowPathCommand(val path: PathChain, val holdEnd: Boolean = true): Command() {
    
    constructor (path: Path, holdEnd: Boolean = true): this(PathChain(path), holdEnd)
    
    override val isDone: Boolean
        get() = OpModeData.follower!!.isBusy

    override fun start() {
        if (OpModeData.follower == null) {
            throw FollowerNotInitializedException()
        }
        
        OpModeData.follower!!.followPath(path, holdEnd)
    }
}