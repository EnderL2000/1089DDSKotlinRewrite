/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.ProtoShooter;
import frc.robot.subsystems.ProtoShooter.ShooterSpeed;

public class RunShooter extends Command {
  private ShooterSpeed targetState;
  private double minimumDistance = 8.0;
  private int timeThreshold = 550;
  private long startTimeMillis;

  public RunShooter(ProtoShooter.ShooterSpeed targetState) {
    requires(Robot.protoShooter);
    this.targetState = targetState;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startTimeMillis = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.protoShooter.setClawState(targetState);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (targetState == ProtoShooter.ShooterSpeed.FAST_INTAKE || targetState == ProtoShooter.ShooterSpeed.SLOW_INTAKE)
      return Robot.protoShooter.getLidar().getDistance() - minimumDistance <= 0;

    return System.currentTimeMillis() - startTimeMillis > timeThreshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.protoShooter.setClawState(ShooterSpeed.STOP);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}