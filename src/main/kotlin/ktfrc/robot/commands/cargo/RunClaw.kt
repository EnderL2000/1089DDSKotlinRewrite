package ktfrc.robot.commands.cargo

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.ClawState
import ktfrc.robot.util.DriveDirection
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class RunClaw(private val targetState: ClawState): Command() {

    private var startTime = 0L

    init {
        requires(Robot.clawAndIntake)
        name = "RunClaw Command"
        LOG?.info("$name Constructed")
    }

    override fun initialize() {
        startTime = System.currentTimeMillis()
        Robot.clawAndIntake.clawState = targetState
        LOG?.info("$name Initialized")
    }

    override fun isFinished(): Boolean {
        val cargoAcquired = Robot.clawAndIntake.cargoIsInRobot()

        return when(targetState) {
            ClawState.INTAKING -> {
                if(cargoAcquired) {
                    Robot.driveTrain.driveAssist.driveDirection = DriveDirection.CARGO
                }
                cargoAcquired
            }
            ClawState.EJECTING -> {
                if (System.currentTimeMillis() - startTime > TIME_THRESHOLD) {
                    Robot.driveTrain.driveAssist.driveDirection = DriveDirection.HATCH
                    return true
                }
                false
            }
            else -> true
        }
    }

    override fun end() {
        Robot.clawAndIntake.clawState = ClawState.IDLE
        LOG?.info("$name Ended")
    }

    override fun interrupted() {
        end()
        LOG?.info("$name Interrupted")
    }

    companion object {
        val LOG: Logger? = LogManager.getLogger(RunClaw::class.java)
        const val TIME_THRESHOLD = 550L
    }
}