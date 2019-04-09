package ktfrc.robot.commands.hatchpanel

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.RobotMap
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ManualStingerControl: Command() {

    init {
        requires(Robot.stinger)
        name = "ManualStingerControl Command"
        LOG?.info("$name Constructed")
    }

    override fun execute() {
        Robot.stinger.ejector.setSpeed(Robot.oi.getGamepadAxis(RobotMap.GAMEPAD_AXIS.leftY))
        LOG?.info("$name Executed")
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end() {
        Robot.stinger.ejector.stop()
        LOG?.info("$name Ended")
    }

    override fun interrupted() {
        LOG?.info("$name Interrupted")
        this.end()
    }

    companion object {
        private val LOG: Logger? = LogManager.getLogger(ManualStingerControl::class.java)
    }
}