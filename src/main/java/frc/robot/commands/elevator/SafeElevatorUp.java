package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.MouthArticulator;
import frc.robot.commands.cargo.ArticulateMouth;

/**
 * It has been determined the elevator is moving up and may collide with the cargo intake.
 * Therefore, we will move the intake and the elevator in such a way they don't do that.
 */
public class SafeElevatorUp extends CommandGroup {

    public SafeElevatorUp(Elevator.ElevatorPosition targetPosition) {
        addSequential(new ArticulateMouth(MouthArticulator.MouthPosition.OUT));
        addSequential(new AutomaticElevator(Elevator.ElevatorPosition.ROCKET_1_C, true));
        addParallel(new ArticulateMouth(MouthArticulator.MouthPosition.IN));
        addSequential(new AutomaticElevator(targetPosition, true));
    }
}
