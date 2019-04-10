package ktfrc.robot.commands.climber

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.FangsPosition
import org.apache.logging.log4j.LogManager

class ArticulateFangs(private val targetPosition: FangsPosition, private val endable: Boolean = false): Command() {

    init {
        requires(Robot.fangs)
        name = "ArticulateFangs Command"
        LOG.info("$name Constructed")
    }

    override fun initialize() {
        Robot.fangs.articulator.setPosition(targetPosition.encTicks)
        LOG.info("$name Initialized")
    }

    override fun isFinished(): Boolean {
        if(endable && POSITION_THRESHOLD >= Math.abs(targetPosition.encTicks - Robot.fangs.articulator.getEncTicks())) {
            LOG.info("Reached ${targetPosition.name}")
            return true
        }
        return false
    }

    override fun end() {
        LOG.info("$name Ended")
    }

    override fun interrupted() {
        LOG.info("$name Interrupted")
    }

    companion object {
        private val LOG = LogManager.getLogger(ArticulateFangs::class.java)
        private const val POSITION_THRESHOLD = 500
    }
}