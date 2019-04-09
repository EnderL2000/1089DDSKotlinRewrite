package ktfrc.robot.commands.elevator

import edu.wpi.first.wpilibj.command.CommandGroup
import ktfrc.robot.commands.cargo.ArticulateMouth
import ktfrc.robot.subsystems.ElevatorPosition
import ktfrc.robot.subsystems.MouthArticulatorPosition

class SafeElevatorUp(targetPosition: ElevatorPosition): CommandGroup() {
    init {
        addSequential(ArticulateMouth(MouthArticulatorPosition.OUT))
        addSequential(AutomaticElevator(ElevatorPosition.ROCKET_1_C, endable = true))
        addParallel(ArticulateMouth(MouthArticulatorPosition.IN))
        addSequential(AutomaticElevator(targetPosition, endable = true))
    }
}