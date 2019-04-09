package ktfrc.robot.commands.conditionals

import edu.wpi.first.wpilibj.command.ConditionalCommand
import ktfrc.robot.Robot
import ktfrc.robot.subsystems.ElevatorPosition

/**
 * The command that should be used to run the elevator.
 * Runs the correct Safe elevator
 */
class UseElevator(private val targetPosition: ElevatorPosition):
        ConditionalCommand(SmartElevatorUp(targetPosition), SmartElevatorDown(targetPosition)) {

    //true = up; false = down
    public override fun condition(): Boolean = targetPosition.encPos - Robot.elevator.getCurrentHeight() > 0
}