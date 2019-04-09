package ktfrc.robot.commands.elevator

import edu.wpi.first.wpilibj.command.CommandGroup
import ktfrc.robot.commands.cargo.ArticulateMouth
import ktfrc.robot.subsystems.ElevatorPosition
import ktfrc.robot.subsystems.MouthArticulatorPosition

class SafeElevatorDown(targetPosition: ElevatorPosition): CommandGroup() {
    init {
        addParallel(AutomaticElevator(ElevatorPosition.ROCKET_1_C, endable = true))
        addSequential(ArticulateMouth(MouthArticulatorPosition.OUT))
        addSequential(AutomaticElevator(targetPosition, endable = true))
        addSequential(ArticulateMouth(MouthArticulatorPosition.IN))
    }
}