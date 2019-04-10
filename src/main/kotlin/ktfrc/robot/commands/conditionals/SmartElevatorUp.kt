package ktfrc.robot.commands.conditionals

import edu.wpi.first.wpilibj.command.ConditionalCommand
import ktfrc.robot.Robot
import ktfrc.robot.commands.elevator.AutomaticElevator
import ktfrc.robot.commands.elevator.SafeElevatorUp
import ktfrc.robot.subsystems.ElevatorPosition

/**
 * Elevator is SAFE if: Elevator is currently below the danger line and it wants to go above the danger line
 * Otherwise, elevator is UNSAFE
 */
class SmartElevatorUp(private val targetPosition: ElevatorPosition):
        ConditionalCommand(SafeElevatorUp(targetPosition), AutomaticElevator(targetPosition)) {

    public override fun condition(): Boolean =
            Robot.elevator.getCurrentHeight() <= ElevatorPosition.ROCKET_1_C.encPos &&
            targetPosition.encPos >= ElevatorPosition.ROCKET_1_C.encPos
}