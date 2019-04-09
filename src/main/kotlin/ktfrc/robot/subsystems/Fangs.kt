package ktfrc.robot.subsystems

import com.ctre.phoenix.ParamEnum
import edu.wpi.first.wpilibj.command.Subsystem
import frc.robot.RobotMap
import ktfrc.robot.util.PIDGain
import ktfrc.robot.subsystems.FangsPosition.*
import ktfrc.robot.util.motorControllers.MercTalonSRX

class Fangs: Subsystem() {
    val articulator = MercTalonSRX(RobotMap.CAN.FANGS)

    init {
        with(articulator) {
            resetEncoder()
            setInverted(true)
            setSensorPhase(true)
            setPosition(IN_BOT.encTicks)
            configPID(ARTICULATION_PID_SLOT, ARTICULATION_GAIN)
            configAllowableClosedLoopError(ARTICULATION_PID_SLOT, ARTICULATION_THRESHHOLD)
            configVoltage(0.15, 0.65)
            configSetParameter(ParamEnum.eClearPositionOnLimitF, 1.0)
            configSetParameter(ParamEnum.eClearPositionOnLimitR, 1.0)
        }
    }

    override fun initDefaultCommand() {}

    companion object {
        const val ARTICULATION_PID_SLOT = 0
        const val ARTICULATION_THRESHHOLD = 10000
        val ARTICULATION_GAIN = PIDGain(0.1, 0.01, 0.0, 0.0, 0.0)
    }
}

enum class FangsPosition(val encTicks: Double) {
    //Temporary encoder tick values
    DOWN(-100000.0),
    IN_BOT(380000.0)
}