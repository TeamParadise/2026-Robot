/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret;

import com.team1165.robot.RobotState;
import com.team1165.robot.globalconstants.FieldConstants;
import com.team1165.robot.subsystems.shooter.turret.io.TurretIO;
import com.team1165.robot.subsystems.shooter.turret.io.TurretIO.TurretIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import org.littletonrobotics.junction.Logger;

public class Turret extends OverridableStateMachine<TurretState> {
  private static final double minAngle = Units.degreesToRadians(-90.0);
  private static final double maxAngle = Units.degreesToRadians(90.0);

  private final TurretIO io;
  private final TurretIOInputs inputs = new TurretIOInputs();

  /** Target position for SOTM tracking (in rotations). */
  private double sotmTargetPosition = 0.0;

  private double targetPosition = 0.0;

  public Turret(TurretIO io) {
    super(TurretState.IDLE);
    this.io = io;
  }

  /**
   * Sets the target position for Shoot On The Move tracking.
   *
   * @param position The target turret position in rotations.
   */
  public void setSotmTargetPosition(double position) {
    this.sotmTargetPosition = position;
    Logger.recordOutput(name + "/SOTMTargetPosition", position);
  }

  public void setSimpleTargetPosition(double position) {
    this.targetPosition =
        position > (0.375 / TurretConstants.Motor.gearRatio)
            ? 0.375 / TurretConstants.Motor.gearRatio
            : position < -0.375 / TurretConstants.Motor.gearRatio
                ? -0.375 / TurretConstants.Motor.gearRatio
                : position;
    Logger.recordOutput(name + "/TargetPosition", position);
  }

  /**
   * Gets the current turret position.
   *
   * @return The current position of the turret.
   */
  public double getPosition() {
    return inputs.motor.getPosition();
  }

  @Override
  protected void update() {
    io.updateInputs(inputs);
    Logger.processInputs(name, inputs.motor);
    Logger.recordOutput("Turret/Pose", RobotState.getTurretPose());
    Logger.recordOutput(
        "Turret/DistanceFromHub",
        RobotState.getTurretPose()
            .relativeTo(
                new Pose3d(FieldConstants.Hub.oppTopCenterPoint, Rotation3d.kZero).toPose2d())
            .getTranslation()
            .getNorm());
  }

  public void updateState() {
    transition();
  }

  @Override
  protected void transition() {
    switch (getCurrentState()) {
      case IDLE -> io.runVolts(0.0);
      case STRAIGHT -> io.runPosition(0.0);
      case SIMPLE_TRACKING -> io.runPosition(targetPosition);
      case SOTM_TRACKING -> io.runPosition(sotmTargetPosition);
      default -> {
        // Other states not yet implemented
      }
    }
  }
}
