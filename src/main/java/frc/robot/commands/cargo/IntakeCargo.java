/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.cargo.ArticulateCargoIntake;
import frc.robot.subsystems.CargoIntake.IntakeSpeed;
import frc.robot.subsystems.CargoIntake.ArticulationPosition;
import frc.robot.subsystems.CargoManipulator.ShooterSpeed;

public class IntakeCargo extends CommandGroup {

  public IntakeCargo() {
    addSequential(new ArticulateCargoIntake(ArticulationPosition.OUT));
    addSequential(new RunCargoIntake(IntakeSpeed.FAST_IN));
    addParallel(new RunCargoManipulator(ShooterSpeed.FAST_INTAKE));
  }
}