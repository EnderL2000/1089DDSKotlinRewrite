package ktfrc.robot.subsystems

import com.ctre.phoenix.CANifier
import edu.wpi.first.wpilibj.command.Subsystem
import ktfrc.robot.Robot
import ktfrc.robot.RobotMap
import ktfrc.robot.sensors.LIDAR
import ktfrc.robot.sensors.LidarPWMOffset
import ktfrc.robot.subsystems.ClawState.*
import ktfrc.robot.util.motorControllers.MercVictorSPX

class ClawAndIntake: Subsystem() {
    var clawState = IDLE
        set(value) {
            field = value
            when(value) {
                INTAKING -> {
                    clawLeft.setSpeed(-0.5)
                    mouthIntaker.setSpeed(1.0)
                }
                IDLE -> {
                    clawLeft.stop()
                    mouthIntaker.stop()
                }
                EJECTING -> {
                    clawLeft.setSpeed(0.5)
                    mouthIntaker.stop()
                }
            }
        }

    val clawLeft = MercVictorSPX(RobotMap.CAN.CLAW_LEFT)
    val clawRight = MercVictorSPX(RobotMap.CAN.CLAW_RIGHT)
    val mouthIntaker = MercVictorSPX(RobotMap.CAN.MOUTH_INTAKE)

    val cargoLIDAR = LIDAR(Robot.driveTrain.canifier, CANifier.PWMChannel.PWMChannel1, LidarPWMOffset.DEFAULT)

    init {
        clawLeft.setInverted(true)
        clawRight.setInverted(false)
        clawRight.follow(clawLeft)
    }

    override fun initDefaultCommand() {}

    override fun periodic() {
        cargoLIDAR.updatePWMInput()
    }

    fun cargoIsInRobot(): Boolean = cargoLIDAR.getDistance() <= CARGO_IN_ROBOT_THRESHOLD

    companion object {
        const val CARGO_IN_ROBOT_THRESHOLD = 12.0
    }
}

enum class ClawState {
    INTAKING,
    EJECTING,
    IDLE
}