package ktfrc.robot.subsystems

import edu.wpi.first.wpilibj.command.Subsystem
import ktfrc.robot.sensors.Limelight

class LimelightAssembly: Subsystem() {
    val limelight = Limelight()

    override fun initDefaultCommand() {}
}