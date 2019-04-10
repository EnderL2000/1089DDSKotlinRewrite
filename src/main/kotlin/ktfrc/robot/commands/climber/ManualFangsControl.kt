package ktfrc.robot.commands.climber

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.RobotMap
import ktfrc.robot.Robot
import ktfrc.robot.util.applyDeadzone
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ManualFangsControl: Command() {

    init {
        requires(Robot.fangs)
        name = "ManualFangsControl Command"
        LOG?.info("$name Constructed")
    }

    override fun execute() {
        Robot.fangs.articulator.setSpeed(applyDeadzone(Robot.oi.getGamepadAxis(RobotMap.GAMEPAD_AXIS.leftY), 0.1))
        LOG?.info("$name Executed")
    }

    override fun isFinished(): Boolean = false

    override fun end() {
        LOG?.info("$name Ended")
    }

    override fun interrupted() {
        LOG?.info("$name Interrupted")
        this.end()
    }

    companion object {
        val LOG: Logger? = LogManager.getLogger(ManualFangsControl::class.java)
    }
}