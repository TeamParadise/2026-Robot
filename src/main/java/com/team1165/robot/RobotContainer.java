/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.robot.commands.ShootOnTheMoveCommands;
import com.team1165.robot.subsystems.base.intake.GroundIntake;
import com.team1165.robot.subsystems.base.intake.GroundIntakeConstants;
import com.team1165.robot.subsystems.base.intake.GroundIntakeState;
import com.team1165.robot.subsystems.base.intake.io.PivotIO;
import com.team1165.robot.subsystems.base.intake.io.PivotIOSpark;
import com.team1165.robot.subsystems.drive.Drive;
import com.team1165.robot.subsystems.drive.constants.DriveConstants;
import com.team1165.robot.subsystems.drive.io.DriveIO;
import com.team1165.robot.subsystems.drive.io.DriveIOMapleSim;
import com.team1165.robot.subsystems.drive.io.DriveIOReal;
import com.team1165.robot.subsystems.hood.Hood;
import com.team1165.robot.subsystems.hood.io.HoodIO;
import com.team1165.robot.subsystems.shooter.flywheel.Flywheel;
import com.team1165.robot.subsystems.shooter.turret.Turret;
import com.team1165.robot.subsystems.shooter.turret.io.TurretIO;
import com.team1165.util.constants.RobotMode;
import com.team1165.util.io.dualroller.DualRollerIO;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIOSpark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final Drive drive;
  private final GroundIntake groundIntake;
  private final Turret turret;
  private final Hood hood;
  private final Flywheel flywheel;
  private final CommandXboxController driverController = new CommandXboxController(0);

  /** Creates subsystems and IO implementations based on current runtime mode. */
  public RobotContainer() {
    switch (RobotMode.get()) {
      case REAL -> {
        drive =
            new Drive(
                new DriveIOReal(
                    DriveConstants.drivetrainConstants, DriveConstants.getModuleConstants()));
        groundIntake =
            new GroundIntake(
                new PivotIOSpark(
                    GroundIntakeConstants.Pivot.primaryConfig,
                    GroundIntakeConstants.Pivot.secondaryConfig),
                new RollerIOSpark(GroundIntakeConstants.Roller.config),
                GroundIntakeConstants.Pivot.gains,
                GroundIntakeConstants.Pivot.motionProfile);
        turret = new Turret(new TurretIO() {});
        hood = new Hood(new HoodIO() {}, new Slot0Configs());
        flywheel =
            new Flywheel(new DualRollerIO() {}, new Slot0Configs(), new MotionMagicConfigs());
      }
      case SIM -> {
        drive =
            new Drive(
                new DriveIOMapleSim(
                    DriveConstants.drivetrainConstants,
                    DriveConstants.simConfig,
                    DriveConstants.getModuleConstants()));
        groundIntake =
            new GroundIntake(
                new PivotIO() {}, new RollerIO() {}, new Slot0Configs(), new MotionMagicConfigs());
        turret = new Turret(new TurretIO() {});
        hood = new Hood(new HoodIO() {}, new Slot0Configs());
        flywheel =
            new Flywheel(new DualRollerIO() {}, new Slot0Configs(), new MotionMagicConfigs());
      }
      case REPLAY -> {
        drive = new Drive(new DriveIO() {});
        groundIntake =
            new GroundIntake(
                new PivotIO() {}, new RollerIO() {}, new Slot0Configs(), new MotionMagicConfigs());
        turret = new Turret(new TurretIO() {});
        hood = new Hood(new HoodIO() {}, new Slot0Configs());
        flywheel =
            new Flywheel(new DualRollerIO() {}, new Slot0Configs(), new MotionMagicConfigs());
      }
      default -> {
        drive = new Drive(new DriveIO() {});
        groundIntake =
            new GroundIntake(
                new PivotIO() {}, new RollerIO() {}, new Slot0Configs(), new MotionMagicConfigs());
        turret = new Turret(new TurretIO() {});
        hood = new Hood(new HoodIO() {}, new Slot0Configs());
        flywheel =
            new Flywheel(new DualRollerIO() {}, new Slot0Configs(), new MotionMagicConfigs());
      }
    }

    configureButtonBindings();
  }

  /** Configure driver button bindings for ground intake. */
  private void configureButtonBindings() {
    driverController
        .a()
        .onTrue(
            groundIntake
                .overrideState(GroundIntakeState.IDLE)
                .withName("Controller - A - Idle State"));
    driverController
        .b()
        .onTrue(
            groundIntake
                .overrideState(GroundIntakeState.DEPLOY)
                .withName("Controller - B - Deploy State"));

    // Shoot On The Move - hold right bumper to track target while moving
    driverController
        .rightBumper()
        .whileTrue(
            ShootOnTheMoveCommands.trackTarget(drive, turret, hood, flywheel)
                .withName("Controller - RB - Shoot On The Move"));
  }

  /** Returns the autonomous command to run. */
  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
