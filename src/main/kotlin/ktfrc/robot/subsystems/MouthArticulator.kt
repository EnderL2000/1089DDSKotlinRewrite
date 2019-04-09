package ktfrc.robot.subsystems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.command.Subsystem
import ktfrc.robot.RobotMap
import ktfrc.robot.subsystems.MouthArticulatorPosition.*

class MouthArticulator: Subsystem() {
    private val mouthArticulator = DoubleSolenoid(RobotMap.CAN.PCM, RobotMap.PCM.MOUTH_ACTUATE, RobotMap.PCM.MOUTH_RETRACT)

    override fun initDefaultCommand() {}

    fun moveMouth(position: MouthArticulatorPosition) {
        mouthArticulator.set( when(position) {
            OUT -> DoubleSolenoid.Value.kForward
            IN -> DoubleSolenoid.Value.kReverse
        })
    }
}

enum class MouthArticulatorPosition {
    IN,
    OUT
}