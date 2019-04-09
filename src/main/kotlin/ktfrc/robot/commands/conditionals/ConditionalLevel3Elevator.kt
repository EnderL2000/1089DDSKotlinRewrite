package ktfrc.robot.commands.conditionals

import edu.wpi.first.wpilibj.command.ConditionalCommand
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.ElevatorPosition
import ktfrc.robot.util.DriveDirection

/**
 * Raises the elevator to the correct 3rd level rocket (cargo or hatch panel)
 * depending on which way we are driving
 */
class ConditionalLevel3Elevator:
        ConditionalCommand(UseElevator(ElevatorPosition.ROCKET_3_HP), UseElevator(ElevatorPosition.ROCKET_3_C)) {

    public override fun condition(): Boolean =
            Robot.driveTrain.driveAssist.driveDirection == DriveDirection.HATCH
}