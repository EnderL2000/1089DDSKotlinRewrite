package ktfrc.robot.commands.cargo

import edu.wpi.first.wpilibj.command.CommandGroup
import ktfrc.robot.subsystems.ClawState
import ktfrc.robot.subsystems.MouthArticulatorPosition
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class IntakeCargo: CommandGroup() {

    init {
        addParallel(ArticulateMouth(MouthArticulatorPosition.OUT))
        addSequential(RunClaw(ClawState.INTAKING))
        name = "IntakeCargo CommandGroup"
        LOG?.info("$name Constructed")
    }

    companion object {
        val LOG: Logger? = LogManager.getLogger(IntakeCargo::class.java)
    }
}