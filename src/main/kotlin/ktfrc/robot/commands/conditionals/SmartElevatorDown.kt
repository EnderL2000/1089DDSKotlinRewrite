package ktfrc.robot.commands.conditionals

import edu.wpi.first.wpilibj.command.ConditionalCommand
import ktfrc.robot.Robot
import ktfrc.robot.commands.elevator.AutomaticElevator
import ktfrc.robot.commands.elevator.SafeElevatorDown
import ktfrc.robot.subsystems.ElevatorPosition

/**
 * Elevator is SAFE if: Elevator is currently above the danger line and it wants to go to BOTTOM
 * Otherwise, elevator is UNSAFE
 */
class SmartElevatorDown(private var targetPosition: ElevatorPosition) :
        ConditionalCommand(SafeElevatorDown(targetPosition), AutomaticElevator(targetPosition)) {

    public override fun condition(): Boolean =
            Robot.elevator.getCurrentHeight() > ElevatorPosition.DANGER_LINE.encPos &&
            targetPosition.encPos < ElevatorPosition.DANGER_LINE.encPos
}