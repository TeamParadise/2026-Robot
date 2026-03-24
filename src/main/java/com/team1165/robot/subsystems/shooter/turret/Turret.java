/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret;

import com.team1165.robot.subsystems.shooter.turret.io.TurretIO;
import com.team1165.robot.subsystems.shooter.turret.io.TurretIO.TurretIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import org.littletonrobotics.junction.Logger;

public class Turret extends OverridableStateMachine<TurretState> {

  private final TurretIO io;
  private final TurretIOInputs inputs = new TurretIOInputs();

  /** Target position for SOTM tracking (in rotations). */
  private double sotmTargetPosition = 0.0;

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
  }

  @Override
  protected void transition() {
    switch (getCurrentState()) {
      case IDLE -> io.runVolts(0.0);
      case SOTM_TRACKING -> io.runPosition(sotmTargetPosition);
      default -> {
        // Other states not yet implemented
      }
    }
  }
}
