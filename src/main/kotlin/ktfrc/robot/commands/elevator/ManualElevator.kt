package ktfrc.robot.commands.elevator

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.RobotMap
import ktfrc.robot.util.applyDeadzone
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ManualElevator: Command() {

    init {
        requires(Robot.elevator)
        name = "ManualElevator Command"
        LOG?.info("$name Constructed")
    }

    override fun initialize() {
        LOG?.info("$name Initialized")
    }

    override fun execute() {
        Robot.elevator.elevatorLeader.setSpeed(applyDeadzone(Robot.oi.getGamepadAxis(RobotMap.GAMEPAD_AXIS.rightY), 0.1))
    }

    override fun interrupted() {
        LOG?.info("$name Interrupted")
    }

    override fun end() {
        LOG?.info("$name Ended")
    }

    override fun isFinished(): Boolean = false

    companion object {
        val LOG: Logger? = LogManager.getLogger(ManualElevator::class.java)
    }
}