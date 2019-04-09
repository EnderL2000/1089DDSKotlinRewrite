package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.MercMath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manual control for the fangs
 */
public class ManualFangsControl extends Command {
    private final Logger LOG = LogManager.getLogger(ManualFangsControl.class);

    public ManualFangsControl() {
        requires(Robot.fangs);
        setName("ManualFangsControl Command");
        LOG.info(getName() + " Constructed");
    }

    /**
     * Articulate the fangs in accordance with the gamepad axis
     */
    @Override
    protected void execute() {
        Robot.fangs.getArticulator().setSpeed(MercMath.applyDeadzone(Robot.oi.getGamepadAxis(RobotMap.GAMEPAD_AXIS.leftY), 0.1));
        LOG.info(getName() + " Executed");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        LOG.info(getName() + " Ended");
    }

    @Override
    protected void interrupted() {
        LOG.info(getName() + " Interrupted");
        this.end();
    }
}
