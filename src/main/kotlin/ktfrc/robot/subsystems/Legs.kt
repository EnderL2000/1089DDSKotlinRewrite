package ktfrc.robot.subsystems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.command.Subsystem
import ktfrc.robot.RobotMap
import ktfrc.robot.subsystems.LegsPosition.*

class Legs: Subsystem() {
    private val legs = DoubleSolenoid(RobotMap.CAN.PCM, RobotMap.PCM.HAB_ACTUATE, RobotMap.PCM.HAB_RETRACT)
    private var isInLiftMode = false

    override fun initDefaultCommand() {}

    fun moveLegs(position: LegsPosition) {
        legs.set( when(position) {
            OUT -> DoubleSolenoid.Value.kForward
            IN -> DoubleSolenoid.Value.kReverse
        })

        isInLiftMode = position == OUT
    }
}

enum class LegsPosition {
    IN,
    OUT
}