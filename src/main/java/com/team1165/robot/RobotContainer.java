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
import com.team1165.robot.subsystems.vision.apriltag.ATVision;
import com.team1165.robot.subsystems.vision.apriltag.ATVision.CameraConfig;
import com.team1165.robot.subsystems.vision.apriltag.constants.ATVisionConstants.Cameras.*;
import com.team1165.robot.subsystems.vision.apriltag.io.ATVisionIO;
import com.team1165.robot.subsystems.vision.apriltag.io.ATVisionIOPhoton;
import com.team1165.robot.subsystems.vision.apriltag.io.ATVisionIOPhotonSim;
import com.team1165.util.constants.RobotMode;
import com.team1165.util.io.dualroller.DualRollerIO;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIOSpark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final Drive drive;
  private final Turret turret;
  private final ATVision vision;
  private final All all;
  private final CommandXboxController driverController = new CommandXboxController(0);

  public final RobotState robotState;

  /** Creates subsystems and IO implementations based on current runtime mode. */
  public RobotContainer() {
    switch (RobotMode.get()) {
      case REAL -> {
        drive =
            new Drive(
                new DriveIOReal(
                    DriveConstants.drivetrainConstants, DriveConstants.getModuleConstants()));
        turret = new Turret(new TurretIO() {});
        vision = new ATVision(drive::addVisionMeasurement, drive::getRotation, new CameraConfig(new ATVisionIOPhoton(
            RightCamera.name), RightCamera.robotToCamera), new CameraConfig(new ATVisionIOPhoton(
            LeftCamera.name), LeftCamera.robotToCamera));
      }
      case SIM -> {
        drive =
            new Drive(
                new DriveIOMapleSim(
                    DriveConstants.drivetrainConstants,
                    DriveConstants.simConfig,
                    DriveConstants.getModuleConstants()));
        turret = new Turret(new TurretIO() {});
        vision =
            new ATVision(
                drive::addVisionMeasurement,
                drive::getRotation,
                new CameraConfig(new ATVisionIO() {}, RightCamera.robotToCamera),
                new CameraConfig(new ATVisionIO() {}, LeftCamera.robotToCamera));
      }
      default -> {
        drive = new Drive(new DriveIO() {});
        turret = new Turret(new TurretIO() {});
        vision =
            new ATVision(
                drive::addVisionMeasurement,
                drive::getRotation,
                new CameraConfig(new ATVisionIO() {}, RightCamera.robotToCamera),
                new CameraConfig(new ATVisionIO() {}, LeftCamera.robotToCamera));
      }
    }

    all = new All();

    robotState = new RobotState(drive, turret);

    configureButtonBindings();
  }

  /** Configure driver button bindings for ground intake. */
  private void configureButtonBindings() {
    driverController.a().whileTrue(all.runShooter());
    driverController.b().onTrue(all.stop());
    driverController.x().whileTrue(all.runTransfer());
    driverController.y().whileTrue(all.runIntake());
    driverController.povUp().whileTrue(all.pullIntakeIn()).onFalse(all.stopIntake());
    driverController.povDown().whileTrue(all.kickIntakeOut()).onFalse(all.stopIntake());
  }

  /** Returns the autonomous command to run. */
  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
