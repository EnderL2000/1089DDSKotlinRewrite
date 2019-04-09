package ktfrc.robot.commands

import edu.wpi.first.wpilibj.command.CommandGroup
import ktfrc.robot.commands.conditionals.UseElevator
import ktfrc.robot.commands.cargo.ArticulateMouth
import ktfrc.robot.commands.climber.ArticulateFangs
import ktfrc.robot.subsystems.ElevatorPosition
import ktfrc.robot.subsystems.FangsPosition
import ktfrc.robot.subsystems.MouthArticulatorPosition

class TurtleMode : CommandGroup() {
    init {
        addParallel(ArticulateFangs(FangsPosition.IN_BOT))
        addSequential(UseElevator(ElevatorPosition.BOTTOM))
        addSequential(ArticulateMouth(MouthArticulatorPosition.IN))
    }
}
