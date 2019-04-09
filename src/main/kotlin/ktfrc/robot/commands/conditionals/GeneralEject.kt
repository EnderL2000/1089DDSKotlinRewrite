import edu.wpi.first.wpilibj.command.ConditionalCommand
import ktfrc.robot.Robot
import ktfrc.robot.commands.cargo.RunClaw
import ktfrc.robot.commands.hatchpanel.RunStinger
import ktfrc.robot.subsystems.ClawState
import ktfrc.robot.util.DriveDirection

/**
 * Ejects a game piece (cargo or hatch panel)
 * depending on which way we are driving
 */
class GeneralEject:
        ConditionalCommand(RunStinger(), RunClaw(ClawState.EJECTING)) {

    public override fun condition(): Boolean =
        Robot.driveTrain.driveAssist.driveDirection == DriveDirection.HATCH
}