package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.ClawAndIntake;
import frc.robot.subsystems.MouthArticulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntakeCargo extends CommandGroup {

    private final Logger LOG = LogManager.getLogger(IntakeCargo.class);

    public IntakeCargo() {
        addParallel(new ArticulateMouth(MouthArticulator.MouthPosition.OUT));
        addSequential(new RunClaw(ClawAndIntake.ClawState.INTAKING));
        setName("IntakeCargo CommandGroup");
        LOG.info(getName() + " Constructed");
    }
}
