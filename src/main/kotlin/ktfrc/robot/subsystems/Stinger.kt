package ktfrc.robot.subsystems

import edu.wpi.first.wpilibj.command.Subsystem
import ktfrc.robot.RobotMap
import ktfrc.robot.util.PIDGain
import ktfrc.robot.util.motorControllers.MercTalonSRX

class Stinger: Subsystem() {

    val ejector = MercTalonSRX(RobotMap.CAN.HATCH_EJECTOR)

    init {
        with(ejector) {
            resetEncoder()
            setPosition(0.0)
            configVoltage(0.0, 0.75)
            configPID(EJECTOR_PID_SLOT, EJECTOR_GAIN)
            configAllowableClosedLoopError(EJECTOR_PID_SLOT, EJECTOR_THRESHOLD)
        }
    }

    override fun initDefaultCommand() {}

    companion object {
        const val EJECTOR_PID_SLOT = 0
        const val EJECTOR_THRESHOLD = 500
        val EJECTOR_GAIN = PIDGain(0.1, 0.0, 0.0, 0.0, 0.75)
    }
}