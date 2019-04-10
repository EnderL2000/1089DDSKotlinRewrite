package ktfrc.robot.commands.hatchpanel

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.Stinger
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class RunStinger(private val targetPosition: Int = 4096 * 20): Command() {

    private var onTargetCount: Int = 0

    init {
        requires(Robot.stinger)
        name = "RunStinger Command"
        LOG?.info("$name Constructed")
    }

    override fun initialize() {
        with(Robot.stinger.ejector) {
            resetEncoder()
            setPosition(targetPosition.toDouble())
        }
        LOG?.info("$name Initialized")
    }

    override fun isFinished(): Boolean {
        val error = Robot.stinger.ejector.getClosedLoopError()
        var isFinished = false
        val isOnTarget = Math.abs(error) < Stinger.EJECTOR_THRESHOLD

        if (isOnTarget) {
            onTargetCount++
        }
        else {
            if (onTargetCount > 0)
                onTargetCount = 0
        }

        if (onTargetCount > ON_TARGET_MIN_COUNT) {
            isFinished = true
            onTargetCount = 0
        }

        return isFinished
    }

    override fun end() {
        LOG?.info("$name Ended")
    }

    override fun interrupted() {
        LOG?.info("$name Interrupted")
    }

    companion object {
        val LOG: Logger? = LogManager.getLogger(RunStinger::class.java)
        const val ON_TARGET_MIN_COUNT = 5
    }
}