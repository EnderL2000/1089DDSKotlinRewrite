package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.MercVictorSPX;
import frc.robot.sensors.LIDAR;

public class ClawAndIntake extends Subsystem {

    public static final double CARGO_IN_ROBOT_THRESHOLD = 12.0;

    private ClawState state;

    private MercVictorSPX clawLeft, clawRight, mouthIntaker;
    private LIDAR lidar;

    public ClawAndIntake() {
        clawLeft = new MercVictorSPX(RobotMap.CAN.CLAW_LEFT);
        clawRight = new MercVictorSPX(RobotMap.CAN.CLAW_RIGHT);
        mouthIntaker = new MercVictorSPX(RobotMap.CAN.MOUTH_INTAKE);

        clawLeft.setInverted(true);
        clawRight.setInverted(false);

        clawRight.follow(clawLeft);

        lidar = new LIDAR(Robot.driveTrain.getCanifier(), CANifier.PWMChannel.valueOf(0), LIDAR.PWMOffset.EQUATION_C);
    }

    @Override
    public void initDefaultCommand() {
        //setDefaultCommand(new RunClaw(ClawState.IDLE));
    }

    public LIDAR getLidar() {
        return lidar;
    }

    @Override
    public void periodic() {
        lidar.updatePWMInput();
    }

    public boolean isCargoInRobot() {
        return lidar.getRawDistance() <= CARGO_IN_ROBOT_THRESHOLD;
    }

    public ClawState getClawState() {
        return state;
    }

    public void setClawState(ClawState state) {
        this.state = state;
        switch (state) {
            case INTAKING:
                clawLeft.setSpeed(-0.5);
                mouthIntaker.setSpeed(1.0);
                break;
            case IDLE:
                clawLeft.setSpeed(0.0);
                mouthIntaker.setSpeed(0.0);
                break;
            default: //Ejecting
                clawLeft.setSpeed(1.0);
                mouthIntaker.setSpeed(0.0);
        }
    }

    public enum ClawState {
        INTAKING,
        EJECTING,
        IDLE
    }
}
