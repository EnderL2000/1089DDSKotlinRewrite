package ktfrc.robot.commands.conditionals

import edu.wpi.first.wpilibj.command.ConditionalCommand
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.ElevatorPosition
import ktfrc.robot.util.DriveDirection

/**
 * Raises the elevator to the correct 2nd level rocket (cargo or hatch panel)
 * depending on which way we are driving
 */
class ConditionalLevel2Elevator:
        ConditionalCommand(UseElevator(ElevatorPosition.ROCKET_2_HP), UseElevator(ElevatorPosition.ROCKET_2_C)) {

    public override fun condition(): Boolean =
            Robot.driveTrain.driveAssist.driveDirection == DriveDirection.HATCH
}