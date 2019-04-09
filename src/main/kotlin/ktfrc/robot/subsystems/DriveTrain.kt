package ktfrc.robot.subsystems

import com.ctre.phoenix.CANifier
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.sensors.PigeonIMU
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.command.Subsystem
import frc.robot.util.MercMath
import ktfrc.robot.RobotMap
import ktfrc.robot.commands.drivetrain.DriveWithJoysticks
import ktfrc.robot.sensors.LIDAR
import ktfrc.robot.sensors.LidarPWMOffset
import ktfrc.robot.util.DriveAssist
import ktfrc.robot.util.DriveDirection
import ktfrc.robot.util.PIDGain
import ktfrc.robot.util.motorControllers.IMercMotorController
import ktfrc.robot.util.motorControllers.MercTalonSRX
import ktfrc.robot.util.motorControllers.MercVictorSPX

class DriveTrain(val mCLayout: DriveTrainMCConfig): PIDOutput, Subsystem() {
    val leaderLeft: IMercMotorController
    val leaderRight: IMercMotorController
    val followerLeft: IMercMotorController
    val followerRight: IMercMotorController

    val pigeon = PigeonIMU(RobotMap.CAN.PIGEON)
    val canifier = CANifier(RobotMap.CAN.CANIFIER)
    val hatchLIDAR = LIDAR(canifier, CANifier.PWMChannel.PWMChannel0, LidarPWMOffset.EQUATION_C)

    private var currentLEDColor = LEDColor.BLACK

    private var isInMotionMagicMode = false

    val driveAssist: DriveAssist

    init {
        when(mCLayout) {
            DriveTrainMCConfig.SPARKS -> { TODO() }
            DriveTrainMCConfig.TALON_VICTOR -> {
                leaderLeft = MercTalonSRX(RobotMap.CAN.DRIVETRAIN_ML)
                leaderRight = MercTalonSRX(RobotMap.CAN.DRIVETRAIN_MR)
                followerLeft = MercVictorSPX(RobotMap.CAN.DRIVETRAIN_SL)
                followerRight = MercVictorSPX(RobotMap.CAN.DRIVETRAIN_SR)
            }
            DriveTrainMCConfig.LEGACY -> {
                leaderLeft = MercTalonSRX(RobotMap.CAN.DRIVETRAIN_ML)
                leaderRight = MercTalonSRX(RobotMap.CAN.DRIVETRAIN_MR)
                followerLeft = MercTalonSRX(RobotMap.CAN.DRIVETRAIN_SL)
                followerRight = MercTalonSRX(RobotMap.CAN.DRIVETRAIN_SR)
            }
        }

        driveAssist = DriveAssist(leaderLeft, leaderRight, DriveDirection.HATCH)

        setFollowers()

        configUniversalVoltage(NOMINAL_OUT, PEAK_OUT)
        setMaxOutput(PEAK_OUT)
        setUniversalMotorAndEncoderOrientation()
        setUniversalNeutralMode(NeutralMode.Brake)
        resetEncoders()

        configPID()
        initializeMotionMagicFeedback()

        pigeon.configFactoryDefault()

        stop()
    }

    override fun initDefaultCommand() {
        defaultCommand = DriveWithJoysticks(DriveWithJoysticks.DriveType.ARCADE)
    }

    override fun periodic() {
        hatchLIDAR.updatePWMInput()
    }

    fun setUniversalNeutralMode(neutralMode: NeutralMode) { //enum
        leaderLeft.setNeutralMode(neutralMode)
        leaderRight.setNeutralMode(neutralMode)
        followerLeft.setNeutralMode(neutralMode)
        followerRight.setNeutralMode(neutralMode)
    }

    fun configUniversalVoltage(nominalOutput: Double, peakOutput: Double) {
        leaderLeft.configVoltage(nominalOutput, peakOutput)
        leaderRight.configVoltage(nominalOutput, peakOutput)
    }

    fun stop() {
        leaderLeft.stop()
        leaderRight.stop()
        if(mCLayout == DriveTrainMCConfig.SPARKS) {
            followerLeft.stop()
            followerRight.stop()
        }
    }

    fun getYaw(): Double {
        val currYawPitchRoll = DoubleArray(3)
        pigeon.getYawPitchRoll(currYawPitchRoll)
        return currYawPitchRoll[0]
    }

    override fun pidWrite(output: Double) = driveAssist.arcadeDrive(output, -output) //TODO change to tank once implemented

    /**
     * Sets the canifier LED output to the correct `LEDColor`. The
     * CANifier use BRG (not RGB) for its LED Channels
     */
    private fun setLEDColor(ledColor: LEDColor) {
        currentLEDColor = ledColor
        canifier.setLEDOutput(ledColor.red, CANifier.LEDChannel.LEDChannelB)
        canifier.setLEDOutput(ledColor.blue, CANifier.LEDChannel.LEDChannelA)
        canifier.setLEDOutput(ledColor.green, CANifier.LEDChannel.LEDChannelC)
    }

    private fun setFollowers() {
        followerLeft.follow(leaderLeft)
        followerRight.follow(leaderRight)
    }

    private fun setMaxOutput(maxOutput: Double) {
        driveAssist.maxOutput = maxOutput
    }

    private fun setUniversalMotorAndEncoderOrientation() {
        leaderLeft.setInverted(false)
        leaderRight.setInverted(true)
        followerLeft.setInverted(false)
        followerRight.setInverted(true)

        leaderLeft.setSensorPhase(true)
        leaderRight.setSensorPhase(true)
    }

    private fun resetEncoders() {
        leaderLeft.resetEncoder()
        leaderRight.resetEncoder()
    }

    private fun configPID() {
        leaderRight.configPID(DRIVE_PID_SLOT, DRIVE_GAINS)
        leaderLeft.configPID(DRIVE_PID_SLOT, DRIVE_GAINS)

        leaderRight.configPID(DRIVE_SMOOTH_MOTION_SLOT, SMOOTH_GAINS)
        leaderLeft.configPID(DRIVE_SMOOTH_MOTION_SLOT, SMOOTH_GAINS)

        leaderRight.configPID(DRIVE_MOTION_PROFILE_SLOT, MOTION_PROFILE_GAINS)
        leaderLeft.configPID(DRIVE_MOTION_PROFILE_SLOT, MOTION_PROFILE_GAINS)

        leaderRight.configPID(DRIVE_SMOOTH_TURN_SLOT, TURN_GAINS)
        leaderLeft.configPID(DRIVE_SMOOTH_TURN_SLOT, TURN_GAINS)
    }

    private fun initializeMotionMagicFeedback() {
        /* Configure left's encoder as left's selected sensor */
        leaderLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, DriveTrain.PRIMARY_LOOP)

        /* Configure the Remote Talon's selected sensor as a remote sensor for the right Talon */
        leaderRight.configRemoteFeedbackFilter(leaderLeft.getPort(), RemoteSensorSource.TalonSRX_SelectedSensor, DriveTrain.REMOTE_DEVICE_0)

        /* Configure the Pigeon IMU to the other remote slot available on the right Talon */
        leaderRight.configRemoteFeedbackFilter(pigeon.deviceID, RemoteSensorSource.Pigeon_Yaw, DriveTrain.REMOTE_DEVICE_1)

        /* Setup Sum signal to be used for Distance */
        leaderRight.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0)
        leaderRight.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.CTRE_MagEncoder_Relative)

        /* Configure Sum [Sum of both QuadEncoders] to be used for Primary PID Index */
        leaderRight.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, DriveTrain.PRIMARY_LOOP)

        /* Scale Feedback by 0.5 to half the sum of Distance */
        leaderRight.configSelectedFeedbackCoefficient(0.5, DriveTrain.PRIMARY_LOOP)

        /* Configure Remote 1 [Pigeon IMU's Yaw] to be used for Auxiliary PID Index */
        leaderRight.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, DriveTrain.AUXILIARY_LOOP)

        /* Scale the Feedback Sensor using a coefficient */
        leaderRight.configSelectedFeedbackCoefficient(1.0, DriveTrain.AUXILIARY_LOOP)

        /* Set status frame periods to ensure we don't have stale data */
        leaderRight.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20)
        leaderRight.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20)
        leaderRight.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20)
        leaderRight.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20)
        leaderLeft.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20)
        pigeon.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR, 5)

        isInMotionMagicMode = true
    }

    companion object {
        const val DRIVE_PID_SLOT = 0
        const val DRIVE_SMOOTH_MOTION_SLOT = 1
        const val DRIVE_MOTION_PROFILE_SLOT = 2
        const val DRIVE_SMOOTH_TURN_SLOT = 3
        const val REMOTE_DEVICE_0 = 0
        const val REMOTE_DEVICE_1 = 1
        const val PRIMARY_LOOP = 0
        const val AUXILIARY_LOOP = 1
        const val MAX_RPM = 545.0
        const val NOMINAL_OUT = 0.0
        const val PEAK_OUT = 1.0
        const val GEAR_RATIO = 1
        const val WHEEL_DIAMETER_INCHES = 5.8
        const val MAG_ENCODER_TICKS_PER_REVOLUTION = 4096
        const val NEO_ENCODER_TICKS_PER_REVOLUTION = 42
        const val PIGEON_NATIVE_UNITS_PER_ROTATION = 8192

        val FEED_FORWARD = MercMath.calculateFeedForward(MAX_RPM)
        val DRIVE_GAINS = PIDGain(0.125, 0.0, 0.05, 0.0, .75)   // .3
        val SMOOTH_GAINS = PIDGain(0.6, 0.00032, 0.45, FEED_FORWARD, 1.0)    //.00032
        val MOTION_PROFILE_GAINS = PIDGain(0.6, 0.0, 0.0, FEED_FORWARD, 1.0)
        val TURN_GAINS = PIDGain(0.35, 0.0, 0.27, 0.0, 1.0)
    }
}

enum class DriveTrainMCConfig {
    SPARKS,
    TALON_VICTOR,
    LEGACY
}

enum class LEDColor(val red: Double, val green: Double, val blue: Double) {
    RED(1.0, 0.0, 0.0),
    GREEN(0.0, 0.0, 1.0),
    BLUE(0.0, 0.0, 1.0),
    YELLOW(1.0, 1.0, 0.0),
    CYAN(0.0, 1.0, 1.0),
    MAGENTA(1.0, 0.0, 1.0),
    WHITE(1.0, 1.0, 1.0),
    BLACK(0.0, 0.0, 0.0)
}
