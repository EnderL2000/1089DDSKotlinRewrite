package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.HatchManipulator.HatchArticulatorPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Moves the Hatch Panel intake
 */
public class ActuateForks extends Command {
  private final Logger LOG = LogManager.getLogger(ActuateForks.class);
  private HatchArticulatorPosition state;
  private final int POSITION_THRESHOLD = 500;
  private boolean endable = false;

  public ActuateForks(HatchArticulatorPosition state) {
    requires(Robot.hatchManipulator);
    setName("ActuateForks Command");
    LOG.info(getName() + " Constructed");
    this.state = state;
  }

  public ActuateForks(HatchArticulatorPosition state, boolean endable) {
    this(state);
    this.endable = endable;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatchManipulator.setArticulatorPosition(state);
    LOG.info(getName() + " Initialized");
  }

  @Override
  protected boolean isFinished() {
    if (endable && POSITION_THRESHOLD >= Math.abs(state.encPos - Robot.hatchManipulator.getArticulatorPositionTicks())) {
      LOG.info("Reached " + state.toString());
      return true;
    }
    //The below code ends the command if the ArticulatorPosition is InBot
    //We do not want the command to end normally so it's commented out for the moment 
    /**
     * if (state == ArticulatorPosition.IN_BOT) {
     *   if (Robot.hatchManipulator.isArticulatorLimitSwitchClosedReverse() || Robot.hatchManipulator.isArticulatorLimitSwitchClosedForward()) {
     *     Robot.hatchManipulator.getArticulator().setPosition(state.encPos);
     *     LOG.info("Reached!");
     *     return true;
     *   }
     * }
     */
    return false;
  }

  @Override
  protected void end() {
    LOG.info(getName() + " Ended");
  }

  @Override
  protected void interrupted() {
    LOG.info(getName() + " Interrupted");
  }
}