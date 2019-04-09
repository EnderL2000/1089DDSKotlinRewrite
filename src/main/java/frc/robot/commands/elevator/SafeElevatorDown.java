package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.MouthArticulator;
import frc.robot.commands.cargo.ArticulateMouth;

/**
 * It has been determined the elevator is moving down and may collide with the cargo intake.
 * Therefore, we will move the intake and the elevator in such a way they don't do that.
 */
public class SafeElevatorDown extends CommandGroup {

    public SafeElevatorDown(Elevator.ElevatorPosition targetPosition) {
        addParallel(new AutomaticElevator(Elevator.ElevatorPosition.ROCKET_1_C, true));
        addSequential(new ArticulateMouth(MouthArticulator.MouthPosition.OUT));
        addSequential(new AutomaticElevator(targetPosition, true));
        addSequential(new ArticulateMouth(MouthArticulator.MouthPosition.IN));
    }
}
