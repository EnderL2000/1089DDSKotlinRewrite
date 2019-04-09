package ktfrc.robot.commands.limelight

import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.sensors.LimelightLEDState

class SetLED(val targetState: LimelightLEDState): Command() {

    init {
        requires(Robot.limelightAssembly)
    }

    override fun initialize() {
        Robot.limelightAssembly.limelight.setLEDState(targetState)
    }

    override fun isFinished(): Boolean = true
}