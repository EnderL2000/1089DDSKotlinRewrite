package ktfrc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Scheduler
import ktfrc.robot.sensors.LimelightLEDState
import ktfrc.robot.subsystems.*

class Robot: TimedRobot() {

    override fun robotPeriodic() {
        oi.updateDash()
    }

    override fun disabledInit() {
        limelightAssembly.limelight.setLEDState(LimelightLEDState.OFF)
    }

    override fun disabledPeriodic() {
        Scheduler.getInstance().run()
    }

    override fun autonomousInit() {
        limelightAssembly.limelight.setLEDState(LimelightLEDState.PIPELINE_DEFAULT)
    }

    override fun autonomousPeriodic() {
        Scheduler.getInstance().run()
    }

    override fun teleopInit() {
        limelightAssembly.limelight.setLEDState(LimelightLEDState.PIPELINE_DEFAULT)
    }

    override fun teleopPeriodic() {
        Scheduler.getInstance().run()
    }

    companion object {
        val driveTrain = DriveTrain(DriveTrainMCConfig.TALON_VICTOR)

        val mouthArticulator = MouthArticulator()
        val clawAndIntake = ClawAndIntake()

        val elevator = Elevator()

        val stinger = Stinger()

        val fangs = Fangs()
        val legs = Legs()

        val limelightAssembly = LimelightAssembly()

        val oi = OI()
    }
}