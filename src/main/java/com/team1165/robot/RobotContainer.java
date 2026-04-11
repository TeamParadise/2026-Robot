/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import com.ctre.phoenix6.swerve.SwerveRequest.SwerveDriveBrake;
import com.team1165.robot.commands.DriveCommands;
import com.team1165.robot.subsystems.drive.Drive;
import com.team1165.robot.subsystems.drive.constants.DriveConstants;
import com.team1165.robot.subsystems.drive.io.DriveIO;
import com.team1165.robot.subsystems.drive.io.DriveIOMapleSim;
import com.team1165.robot.subsystems.drive.io.DriveIOReal;
import com.team1165.robot.subsystems.intake.GroundIntake;
import com.team1165.robot.subsystems.intake.GroundIntakeConstants.Pivot;
import com.team1165.robot.subsystems.intake.GroundIntakeConstants.Roller;
import com.team1165.robot.subsystems.intake.GroundIntakeState;
import com.team1165.robot.subsystems.intake.io.PivotIOSpark;
import com.team1165.robot.subsystems.spindexer.Spindexer;
import com.team1165.robot.subsystems.spindexer.SpindexerConstants;
import com.team1165.robot.subsystems.spindexer.SpindexerState;
import com.team1165.robot.subsystems.transfer.Transfer;
import com.team1165.robot.subsystems.transfer.TransferConstants;
import com.team1165.robot.subsystems.transfer.TransferState;
import com.team1165.robot.subsystems.vision.apriltag.ATVision;
import com.team1165.robot.subsystems.vision.apriltag.ATVision.CameraConfig;
import com.team1165.robot.subsystems.vision.apriltag.constants.ATVisionConstants.Cameras.*;
import com.team1165.robot.subsystems.vision.apriltag.io.ATVisionIO;
import com.team1165.robot.subsystems.vision.apriltag.io.ATVisionIOPhoton;
import com.team1165.util.constants.RobotMode;
import com.team1165.util.io.roller.RollerIOSpark;
import com.team1165.util.io.rollerpid.RollerPIDIOSpark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.lib.BLine.FollowPath;
import frc.robot.lib.BLine.Path;

public class RobotContainer {
  private final Drive drive;
//  private final ATVision vision;
  //  private final All all;

//  private final GroundIntake intake;
//  //  private final Flywheel flywheel;
//  //  private final Hood hood;
//  //  private final Turret turret;
//  private final Spindexer spindexer;
//  private final Transfer transfer;

  //  protected final ShooterManager shooter;

  private final CommandXboxController driverController = new CommandXboxController(0);

  //  public final RobotState robotState;

//  private final FollowPath path;

  /** Creates subsystems and IO implementations based on current runtime mode. */
  public RobotContainer() {
    switch (RobotMode.get()) {
      case REAL -> {
        drive =
            new Drive(
                new DriveIOReal(
                    DriveConstants.drivetrainConstants, DriveConstants.getModuleConstants()));
//        vision =
//            new ATVision(
//                drive::addVisionMeasurement,
//                drive::getRotation,
//                new CameraConfig(new ATVisionIOPhoton(RightCamera.name), RightCamera.robotToCamera),
//                new CameraConfig(new ATVisionIOPhoton(LeftCamera.name), LeftCamera.robotToCamera));

//        intake =
//            new GroundIntake(
//                new PivotIOSpark(Pivot.primaryConfig, Pivot.secondaryConfig),
//                new RollerIOSpark(Roller.config));
//        //        flywheel =
//        //            new Flywheel(
//        //                new DualRollerIOTalonFX(
//        //                    FlywheelConstants.primaryMotorConfig,
//        // FlywheelConstants.secondaryMotorConfig));
//        //        hood = new Hood(new HoodIOSpark(HoodConstants.config));
//        //        turret = new Turret(new TurretIOTalon(Motor.config) {});
//        spindexer = new Spindexer(new RollerIOSpark(SpindexerConstants.config));
//        transfer = new Transfer(new RollerPIDIOSpark(TransferConstants.config));

        //        shooter = new ShooterManager(drive, flywheel, hood, turret);
      }
      case SIM -> {
        drive =
            new Drive(
                new DriveIOMapleSim(
                    DriveConstants.drivetrainConstants,
                    DriveConstants.simConfig,
                    DriveConstants.getModuleConstants()));
//        vision =
//            new ATVision(
//                drive::addVisionMeasurement,
//                drive::getRotation,
//                new CameraConfig(new ATVisionIO() {}, RightCamera.robotToCamera),
//                new CameraConfig(new ATVisionIO() {}, LeftCamera.robotToCamera));
//
//        intake =
//            new GroundIntake(
//                new PivotIOSpark(Pivot.primaryConfig, Pivot.secondaryConfig),
//                new RollerIOSpark(Roller.config));
//        spindexer = new Spindexer(new RollerIOSpark(SpindexerConstants.config));
//        transfer = new Transfer(new RollerPIDIOSpark(TransferConstants.config));
      }
      default -> {
        drive = new Drive(new DriveIO() {});
//        vision =
//            new ATVision(
//                drive::addVisionMeasurement,
//                drive::getRotation,
//                new CameraConfig(new ATVisionIO() {}, RightCamera.robotToCamera),
//                new CameraConfig(new ATVisionIO() {}, LeftCamera.robotToCamera));
//
//        intake =
//            new GroundIntake(
//                new PivotIOSpark(Pivot.primaryConfig, Pivot.secondaryConfig),
//                new RollerIOSpark(Roller.config));
//
//        //        hood = new Hood(new HoodIOSpark(HoodConstants.config));
//
//        spindexer = new Spindexer(new RollerIOSpark(SpindexerConstants.config));
//        transfer = new Transfer(new RollerPIDIOSpark(TransferConstants.config));
      }
    }

    //    all = new All();

    configureButtonBindings();
//    path = drive.buildPath(new Path("tower"));
    RobotModeTriggers.autonomous().whileTrue(getAutonomousCommand());
  }

  /** Configure driver button bindings for ground intake. */
  private void configureButtonBindings() {
    drive.setDefaultCommand(
        DriveCommands.teleopManualDrive(
            drive,
            () -> -driverController.getLeftY(),
            () -> -driverController.getLeftX(),
            () -> (driverController.getLeftTriggerAxis() - driverController.getRightTriggerAxis()),
            () -> false,
            true));

    driverController
        .back()
        .onTrue(
            Commands.runOnce(drive::seedFieldCentric).withName("Controller - Back - Reset Gyro"));

    //    driverController.start().onTrue(turret.overrideState(TurretState.IDLE));
    //    driverController.a().whileTrue(all.runTransfer()).onFalse(all.stopSome());
    //    driverController.x().whileTrue(all.runShooter());
    //    driverController.b().whileTrue(all.stop());
    //    driverController.povLeft().whileTrue(all.reverse());
    //    driverController.povDown().whileTrue(all.kickIntakeOut());
    //    driverController.povUp().whileTrue(all.pullIntakeIn());
    //    driverController.y().whileTrue(all.runIntake());
    //    driverController.leftBumper().whileTrue(all.spindexerReverse());
    //    driverController.rightBumper().whileTrue(all.spindexerForward());
//
//    driverController
//        .a()
//        .onTrue(transfer.stateCommand(TransferState.FORWARD))
//        .onFalse(transfer.stateCommand(TransferState.IDLE));
    //    driverController
    //        .x()
    //        .onTrue(shooter.stateCommand(ShooterState.TEST))
    //        .onFalse(new WaitCommand(0.8).andThen(shooter.stateCommand(ShooterState.IDLE)));
    //    driverController
    //        .rightStick()
    //        .onTrue(
    //            intake
    //                .stateCommand(GroundIntakeState.IDLE)
    //                .alongWith(spindexer.stateCommand(SpindexerState.IDLE))
    //                .alongWith(transfer.stateCommand(TransferState.IDLE))
    //                .alongWith(shooter.stateCommand(ShooterState.IDLE)));    driverController
    //        .x()
    //        .onTrue(shooter.stateCommand(ShooterState.TEST))
    //        .onFalse(new WaitCommand(0.8).andThen(shooter.stateCommand(ShooterState.IDLE)));
    //    driverController
    //        .rightStick()
    //        .onTrue(
    //            intake
    //                .stateCommand(GroundIntakeState.IDLE)
    //                .alongWith(spindexer.stateCommand(SpindexerState.IDLE))
    //                .alongWith(transfer.stateCommand(TransferState.IDLE))
    //                .alongWith(shooter.stateCommand(ShooterState.IDLE)));
//    driverController.povLeft().onTrue(intake.stateCommand(GroundIntakeState.REVERSE_ROLLER));
//    driverController.povDown().onTrue(intake.stateCommand(GroundIntakeState.MOVE_DOWN));
//    driverController.povUp().onTrue(intake.stateCommand(GroundIntakeState.MOVE_UP));
//    //    driverController.y().onTrue(intake.stateCommand(GroundIntakeState.HOLD_DOWN_AND_INTAKE));
//    driverController.y().whileTrue(drive.applyRequest(SwerveDriveBrake::new));
//    driverController.leftBumper().onTrue(spindexer.stateCommand(SpindexerState.FAST_CW));
//    driverController.rightBumper().onTrue(spindexer.stateCommand(SpindexerState.FAST_CCW));
    //    driverController
    //        .b()
    //        .onTrue(shooter.stateCommand(ShooterState.TRACK_HUB))
    //        .onFalse(new WaitCommand(0.8).andThen(shooter.stateCommand(ShooterState.IDLE)));
  }

  /** Returns the autonomous command to run. */
  public Command getAutonomousCommand() {
    //    return path.withTimeout(3.5)
    //
    //        .andThen(new WaitCommand(1.0).deadlineFor(all.kickIntakeOut()))
    //        .andThen(new WaitCommand(1.0).deadlineFor(all.runIntake()))
    //        .andThen(new WaitCommand(1.0).deadlineFor(all.runShooter()))
    //        .andThen(all.runTransfer().alongWith(all.spindexerForward()));
    //    return path.withTimeout(3.0)
    ////        .andThen(new
    // WaitCommand(1.0).deadlineFor(intake.stateCommand(GroundIntakeState.MOVE_DOWN)))
    ////        .andThen(
    ////            new WaitCommand(1.0)
    ////                .deadlineFor(intake.stateCommand(GroundIntakeState.HOLD_DOWN_AND_INTAKE))
    //                .andThen(
    //                    new
    // WaitCommand(1.0).deadlineFor(shooter.stateCommand(ShooterState.TRACK_HUB)))
    //                .andThen(
    //                    transfer
    //                        .stateCommand(TransferState.FORWARD)
    //                        .alongWith(spindexer.stateCommand(SpindexerState.FAST_CW)));
    return Commands.none();
  }
}
