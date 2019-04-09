package ktfrc.robot.commands.climber

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot

class HoldFangPosition: Command() {

    init {
        requires(Robot.fangs)
    }

    override fun initialize() {
        Robot.fangs.articulator.setPosition(Robot.fangs.articulator.getEncTicks())
    }

    override fun isFinished(): Boolean = false
}