/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.flywheel;

import com.team1165.util.io.dualroller.DualRollerIO;
import com.team1165.util.io.dualroller.DualRollerIOInputsAutoLogged;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import edu.wpi.first.math.MathUtil;
import org.littletonrobotics.junction.Logger;

/** State-machine-based Flywheel subsystem, powered by two motors. */
public class Flywheel extends OverridableStateMachine<FlywheelState> {
  private final DualRollerIO io;
  private final DualRollerIOInputsAutoLogged inputs = new DualRollerIOInputsAutoLogged();

  /** Tracking speed provided by ShooterManager. */
  private double trackingSpeed = 0.0;

  public Flywheel(DualRollerIO io) {
    // For now the Idle state in the enum will be 0, but it will change as building progresses
    super(FlywheelState.IDLE);
    this.io = io;
  }

  /**
   * Returns if the shooter is at speed based on the provided tolerance.
   *
   * @param tolerance The tolerance allowed from the set soeed.
   */
  public boolean atGoal(double tolerance) {
    return switch (getCurrentState()) {
      case TRACKING -> MathUtil.isNear(trackingSpeed, inputs.primaryMotor.getVelocity(), tolerance);
      default -> false;
    };
  }

  /** Returns if the hood is in position based on the default tolerance. */
  public boolean atGoal() {
    return atGoal(FlywheelConstants.defaultTolerance);
  }

  public void updateState() {
    transition();
  }

  @Override
  protected void update() {
    io.updateInputs(inputs);
    Logger.processInputs(name, inputs);
  }

  @Override
  protected void transition() {
    switch (getCurrentState()) {
      case IDLE -> io.stop();
      case TRACKING -> {
        if (MathUtil.isNear(
            trackingSpeed,
            inputs.primaryMotor.getVelocity(),
            trackingSpeed * FlywheelConstants.currentTolerance)) {
          Logger.recordOutput(name + "/ControlMode", "TorqueCurrentFOC");
          io.runVelocityTorqueCurrent(trackingSpeed);
        } else {
          Logger.recordOutput(name + "/ControlMode", "Voltage");
          io.runVelocityVoltage(trackingSpeed);
        }
      }
    }
  }
}
