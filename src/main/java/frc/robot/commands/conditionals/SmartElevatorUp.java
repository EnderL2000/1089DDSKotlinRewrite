package frc.robot.commands.conditionals;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;
import frc.robot.commands.elevator.AutomaticElevator;
import frc.robot.commands.elevator.SafeElevatorUp;

/**
 * Elevator is SAFE if: Elevator is currently below the danger line and it wants to go above the danger line
 * Otherwise, elevator is UNSAFE
 */
public class SmartElevatorUp extends ConditionalCommand {

    Elevator.ElevatorPosition targetPosition;

    public SmartElevatorUp(Elevator.ElevatorPosition targetPosition) {
        super(new SafeElevatorUp(targetPosition), new AutomaticElevator(targetPosition));
        this.targetPosition = targetPosition;
    }

    @Override
    public boolean condition() {
        return Robot.elevator.getCurrentHeight() <= Elevator.ElevatorPosition.ROCKET_1_C.encPos &&
                targetPosition.encPos >= Elevator.ElevatorPosition.ROCKET_1_C.encPos;
    }
}
