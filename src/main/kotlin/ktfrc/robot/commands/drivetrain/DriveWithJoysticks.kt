package ktfrc.robot.commands.drivetrain

import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.wpilibj.command.Command
import ktfrc.robot.Robot
import ktfrc.robot.RobotMap
import ktfrc.robot.subsystems.DriveTrain
import ktfrc.robot.util.DriveAssist
import org.apache.logging.log4j.LogManager

/**
 * Command that puts the drive train into a manual control mode.
 * This puts the robot in arcade or tank drive.
 */
class DriveWithJoysticks(private val driveType: DriveType = DriveType.ARCADE): Command() {

    private var tDrive: DriveAssist? = Robot.driveTrain.driveAssist

    init {
        requires(Robot.driveTrain)
        name = "DriveWithJoysticks Command"
        log.debug("$name command created")
    }

    // Called just before this Command runs the first time
    override fun initialize() {
        log.info("Set max output to: " + tDrive?.maxOutput)
        Robot.driveTrain.configUniversalVoltage(DriveTrain.NOMINAL_OUT, DriveTrain.PEAK_OUT)
        Robot.driveTrain.setUniversalNeutralMode(NeutralMode.Brake)
        log.info("$name command initialized")
    }

    override fun execute() {
        if (tDrive != null) {
            when (driveType) {
                DriveWithJoysticks.DriveType.TANK -> tDrive!!.tankDrive(Robot.oi.getJoystickY(RobotMap.DS_USB.LEFT_STICK), Robot.oi.getJoystickY(RobotMap.DS_USB.RIGHT_STICK))
                DriveWithJoysticks.DriveType.ARCADE -> tDrive!!.arcadeDrive(-Robot.oi.getJoystickY(RobotMap.DS_USB.LEFT_STICK), Robot.oi.getJoystickX(RobotMap.DS_USB.RIGHT_STICK), true)
            }
        } else {
            log.info("Talon Drive is not initialized!")
        }
    }

    override fun isFinished(): Boolean = false

    override fun end() {
        Robot.driveTrain.setUniversalNeutralMode(NeutralMode.Brake)
        Robot.driveTrain.stop()
        log.info("$name ended")
    }

    override fun interrupted() {
        log.info("$name interrupted")
        end()
    }

    enum class DriveType { //TODO move this to drive assist
        TANK,
        ARCADE
    }

    companion object {
        private val log = LogManager.getLogger(DriveWithJoysticks::class.java)
    }
}