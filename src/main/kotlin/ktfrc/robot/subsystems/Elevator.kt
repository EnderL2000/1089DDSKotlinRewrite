package ktfrc.robot.subsystems

import com.ctre.phoenix.ParamEnum
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.wpilibj.command.Subsystem
import ktfrc.robot.RobotMap
import ktfrc.robot.util.PIDGain
import ktfrc.robot.util.calculateFeedForward
import ktfrc.robot.util.motorControllers.MercTalonSRX
import ktfrc.robot.util.revsPerMinuteToTicksPerTenth
import org.apache.logging.log4j.LogManager

class Elevator: Subsystem() {

    val elevatorLeader = MercTalonSRX(RobotMap.CAN.ELEVATOR_TALON)

    init {
        elevatorLeader.setNeutralMode(NeutralMode.Brake)

        elevatorLeader.configMotionAcceleration(revsPerMinuteToTicksPerTenth(9000.0).toInt())
        elevatorLeader.configMotionCruiseVelocity(revsPerMinuteToTicksPerTenth(MAX_ELEVATOR_MOTOR_RPM.toDouble()).toInt())

        elevatorLeader.setSensorPhase(false)
        elevatorLeader.setInverted(true)
        elevatorLeader.configVoltage(0.125, 1.0)
        elevatorLeader.configClosedLoopPeriod(0, 1)
        elevatorLeader.configAllowableClosedLoopError(0, 5)
        elevatorLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, frc.robot.RobotMap.PID.PRIMARY_PID_LOOP)
        elevatorLeader.configSetParameter(ParamEnum.eClearPositionOnLimitR, 1.0, 0, 0)
        elevatorLeader.setForwardSoftLimit(ElevatorPosition.MAX_HEIGHT.encPos.toInt())
        elevatorLeader.enableForwardSoftLimit()

        elevatorLeader.configPID(frc.robot.subsystems.Elevator.PRIMARY_PID_LOOP, PIDGain(NORMAL_P_VAL, 0.00005, 0.0, calculateFeedForward(MAX_ELEVATOR_MOTOR_RPM.toDouble())))
    }

    override fun initDefaultCommand() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCurrentHeight(): Double = elevatorLeader.getEncTicks()

    companion object {
        val log = LogManager.getLogger(Elevator::class.java)

        const val NORMAL_P_VAL = 0.12
        const val PRIMARY_PID_LOOP = 0
        const val MAX_ELEVATOR_MOTOR_RPM = 18000
    }
}

/**
 * Creates an elevator position, storing the encoder ticks
 * representing the height that the elevator should be at.
 *
 * @param encPos encoder position, in ticks
 */
enum class ElevatorPosition(val encPos: Double) {
    MAX_HEIGHT(920000.0),  // TEST THIS
    ROCKET_3_C(914641.0),  // 3st level Rocket: Cargo
    ROCKET_2_C(551295.0),  // 2rd level Rocket: Cargo
    ROCKET_1_C(179359.0),  // 1nd level Rocket: Cargo
    ROCKET_3_HP(723000.0), // 3th level Rocket: Hatch Panel
    ROCKET_2_HP(355720.0), // 2st level Rocket: Hatch Panel
    CARGOSHIP_C(380000.0), // Cargo ship: Cargo
    DANGER_LINE(150000.0), // Line where Claw is in danger of hitting the Mouth
    BOTTOM(-5000.0)        // Can do all level 1 hatches
}