/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter;

import static com.team1165.robot.subsystems.shooter.turret.TurretConstants.Motor.gearRatio;

import com.team1165.robot.calculations.BasicShooterLUT;
import com.team1165.robot.calculations.BasicShooterLUT.ShooterParameters;
import com.team1165.robot.globalconstants.FieldConstants;
import com.team1165.robot.globalconstants.FieldConstants.Hub;
import com.team1165.robot.subsystems.drive.Drive;
import com.team1165.robot.subsystems.shooter.flywheel.Flywheel;
import com.team1165.robot.subsystems.shooter.flywheel.FlywheelState;
import com.team1165.robot.subsystems.shooter.hood.Hood;
import com.team1165.robot.subsystems.shooter.hood.HoodState;
import com.team1165.robot.subsystems.shooter.turret.Turret;
import com.team1165.robot.subsystems.shooter.turret.TurretState;
import com.team1165.util.statemachine.v1.StateManager;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

public class ShooterManager extends StateManager<ShooterState> {
  private final Drive drive;
  private final Flywheel flywheel;
  private final Hood hood;
  private final Turret turret;

  public ShooterManager(Drive drive, Flywheel flywheel, Hood hood, Turret turret) {
    super(ShooterState.IDLE);
    this.drive = drive;
    this.flywheel = flywheel;
    this.hood = hood;
    this.turret = turret;

    // TODO: removing testing
    SmartDashboard.putNumber("Shooter/TestSpeed", 4000);
    SmartDashboard.putNumber("Shooter/TestAngle", 1.5);
  }

  public Command stateCommand(ShooterState state) {
    return Commands.runOnce(() -> setState(state), this);
  }

  public Command stateSupplierCommand(Supplier<ShooterState> stateSupplier) {
    return Commands.runOnce(() -> setState(stateSupplier.get()), this);
  }

  public void updateState() {

    transition();
    turret.updateState();
    flywheel.updateState();
    hood.updateState();
  }

  @Override
  protected void transition() {
    switch (getCurrentState()) {
      case IDLE -> {
        setSubsystemState(flywheel, FlywheelState.IDLE);
        setSubsystemState(hood, HoodState.ZERO);
        setSubsystemState(turret, TurretState.STRAIGHT);
      }
      case CLOSE_HUB -> {
        flywheel.setTrackingSpeed(ShooterConstants.hub.rps());
        hood.setTrackingPosition(ShooterConstants.hub.angle());

        setSubsystemState(flywheel, FlywheelState.TRACKING);
        setSubsystemState(hood, HoodState.TRACKING);
        setSubsystemState(turret, TurretState.STRAIGHT);
      }
      case TRACK_HUB -> {
        Pose2d drivePose = drive.getPose();
        Pose2d hub =
            new Pose3d(
                    DriverStation.getAlliance().isPresent()
                            && DriverStation.getAlliance().get().equals(Alliance.Red)
                        ? FieldConstants.Hub.oppTopCenterPoint
                        : Hub.topCenterPoint,
                    Rotation3d.kZero)
                .toPose2d();
        Logger.recordOutput("ShooterManager/Hub", hub);
        double distanceFromHub =
            drivePose
                .plus(new Transform2d(0.192024, 0.0, Rotation2d.kZero))
                .relativeTo(hub)
                .getTranslation()
                .getNorm();
        double angle =
            new Rotation2d(Math.atan2(hub.getY() - drivePose.getY(), hub.getX() - drivePose.getX()))
                .minus(drivePose.getRotation())
                .getRotations();
        Logger.recordOutput("Turret/Angle", angle);
        ShooterParameters parameters = BasicShooterLUT.lut.get(distanceFromHub);

        flywheel.setTrackingSpeed(parameters.rps());
        hood.setTrackingPosition(parameters.angle());
        turret.setSimpleTargetPosition(angle / gearRatio);

        setSubsystemState(flywheel, FlywheelState.TRACKING);
        setSubsystemState(hood, HoodState.TRACKING);
        setSubsystemState(turret, TurretState.SIMPLE_TRACKING);
      }
      case TEST -> {
        flywheel.setTrackingSpeed(SmartDashboard.getNumber("Shooter/TestSpeed", 4000) / 60);
        hood.setTrackingPosition(SmartDashboard.getNumber("Shooter/TestAngle", 1.5));

        setSubsystemState(flywheel, FlywheelState.TRACKING);
        setSubsystemState(hood, HoodState.TRACKING);
        setSubsystemState(turret, TurretState.STRAIGHT);
      }
    }
  }
}
