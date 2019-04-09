package ktfrc.robot.commands.elevator

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.ElevatorPosition
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AutomaticElevator(private val targetPos: ElevatorPosition, private val endable: Boolean = false): Command() {

    init {
        requires(Robot.elevator)
        name = "AutomaticElevator Command (${targetPos.name})"
        LOG?.info("$name Constructed")
    }

    override fun initialize() {
        Robot.elevator.elevatorLeader.set(ControlMode.MotionMagic, targetPos.encPos)

        LOG?.info("$name Initialized")
    }

    override fun isFinished(): Boolean {
        if (endable && ELEVATOR_THRESHOLD >= Math.abs(targetPos.encPos - Robot.elevator.getCurrentHeight())) {
            LOG?.info("Reached $targetPos")
            return true
        }
        if (targetPos == ElevatorPosition.BOTTOM && Robot.elevator.elevatorLeader.getEncTicks() == 0.0) {
            LOG?.info("Reached Bottom")
            return true
        }
        return false
    }

    override fun end() {
        LOG?.info("$name Ended")
    }

    override fun interrupted() {
        LOG?.info("$name Interrupted")
    }

    companion object {
        private val LOG: Logger? = LogManager.getLogger(AutomaticElevator::class.java)
        private const val ELEVATOR_THRESHOLD = 3000
    }
}