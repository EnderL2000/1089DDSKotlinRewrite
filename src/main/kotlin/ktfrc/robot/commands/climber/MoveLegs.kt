package ktfrc.robot.commands.climber

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.LegsPosition
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class MoveLegs(private val targetPosition: LegsPosition): Command() {

    init {
        requires(Robot.legs)
        name = "MoveLegs Command"
        LOG?.info("$name Constructed")
    }

    override fun initialize() {
        LOG?.info("$name Initialized")
        Robot.legs.moveLegs(targetPosition)
    }

    override fun isFinished(): Boolean = false

    override fun end() {
        LOG?.info("$name Ended")
    }

    override fun interrupted() {
        LOG?.info("$name Interrupted")
    }

    companion object {
        private val LOG: Logger? = LogManager.getLogger(MoveLegs::class.java)
    }
}