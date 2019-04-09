package ktfrc.robot.util

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import ktfrc.robot.Robot
import ktfrc.robot.util.motorControllers.LimitSwitchPolarity

class ShuffleDash {
    fun updateDash() {
        SmartDashboard.putString("direction", Robot.driveTrain.driveAssist.driveDirection.name)

        SmartDashboard.putNumber("Left Enc in feet", getEncPosition(Robot.driveTrain.leaderLeft.getEncTicks()))
        SmartDashboard.putNumber("Right Enc in feet",getEncPosition(Robot.driveTrain.leaderRight.getEncTicks()))

        SmartDashboard.putNumber("Left Wheel RPM", ticksPerTenthToRevsPerMinute(Robot.driveTrain.leaderLeft.getEncVelocity()))
        SmartDashboard.putNumber("Right Wheel RPM", ticksPerTenthToRevsPerMinute(Robot.driveTrain.leaderRight.getEncVelocity()))

        SmartDashboard.putNumber("LIDAR Raw Distance (in.)", roundFloat(Robot.clawAndIntake.cargoLIDAR.getRawDistance(), 10))

        SmartDashboard.putNumber("Lime Dist From Vertical", Robot.limelightAssembly.limelight.rawVertDistance)

        SmartDashboard.putNumber("Gyro Angle", Robot.driveTrain.getYaw())

        SmartDashboard.putString("FrontCamera", if (Robot.driveTrain.driveAssist.driveDirection == DriveDirection.HATCH) "Panel" else "Cargo")
        SmartDashboard.putString("BackCamera", if (Robot.driveTrain.driveAssist.driveDirection == DriveDirection.HATCH) "Cargo" else "Panel")

        SmartDashboard.putBoolean("Elevator Limit Switch Closed", Robot.elevator.elevatorLeader.isLimitSwitchClosed(LimitSwitchPolarity.REVERSE))

        SmartDashboard.putNumber("Elevator enc", Robot.elevator.elevatorLeader.getEncTicks())
        SmartDashboard.putNumber("Hatch pos", Robot.stinger.ejector.getEncTicks())

        SmartDashboard.putNumber("Fangs Enc", Robot.fangs.articulator.getEncTicks())

        SmartDashboard.putBoolean("Rev Fangs limit", Robot.fangs.articulator.isLimitSwitchClosed(LimitSwitchPolarity.REVERSE))
    }
}