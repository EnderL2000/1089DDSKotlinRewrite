package ktfrc.robot.commands.cargo

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.MouthArticulatorPosition

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ArticulateMouth(val targetPosition: MouthArticulatorPosition): Command() {

    var startTime = 0L

    init {
        requires(Robot.mouthArticulator)
        name = "ArticulateMouth Command"
        LOG?.info("$name Constructed")
    }

    override fun initialize() {
        Robot.mouthArticulator.moveMouth(targetPosition)
        startTime = System.currentTimeMillis()
    }

    override fun isFinished(): Boolean = System.currentTimeMillis() - startTime > TIMEOUT

    override fun end() {
        LOG?.info("$name Interrupted")
        this.end()
    }

    companion object {
        val LOG: Logger? = LogManager.getLogger(ArticulateMouth::class.java)
        const val TIMEOUT = 500L
    }
}