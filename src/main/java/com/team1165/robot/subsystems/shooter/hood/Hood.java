/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.hood;

import com.team1165.robot.subsystems.shooter.hood.io.HoodIO;
import com.team1165.robot.subsystems.shooter.hood.io.HoodIOInputsAutoLogged;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import edu.wpi.first.math.MathUtil;
import org.littletonrobotics.junction.Logger;

public class Hood extends OverridableStateMachine<HoodState> {
  private final HoodIO io;
  private final HoodIOInputsAutoLogged inputs = new HoodIOInputsAutoLogged();

  /** Tracking position provided by ShooterManager. */
  private double trackingPosition = 0.0;

  /** Trim in order to make any necessary hood corrections. */
  private double trim = 0.0;

  public Hood(HoodIO io) {
    super(HoodState.IDLE);
    this.io = io;
  }

  /**
   * Returns if the hood is in position based on the provided tolerance.
   *
   * @param tolerance The tolerance allowed from the set position.
   */
  public boolean atGoal(double tolerance) {
    return switch (getCurrentState()) {
      case TRACKING ->
          MathUtil.isNear(trackingPosition, inputs.motor.getPosition() - trim, tolerance);
      case ZERO -> MathUtil.isNear(0.0, inputs.motor.getPosition() - trim, tolerance);
      default -> false;
    };
  }

  /** Returns if the hood is in position based on the default tolerance. */
  public boolean atGoal() {
    return atGoal(HoodConstants.defaultTolerance);
  }

  /**
   * Adjust the trim by a specific amount.
   *
   * @param adjustment The amount to change the trim by.
   */
  public void changeTrim(double adjustment) {
    trim += adjustment;
  }

  /**
   * Sets the tracking position provided by ShooterManager.
   *
   * @param trackingPosition The target hood position (0 to 1 where 1 is full rotation).
   */
  public void setTrackingPosition(double trackingPosition) {
    this.trackingPosition = trackingPosition;
    Logger.recordOutput(name + "/TrackingPosition", trackingPosition);
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
      case TRACKING -> io.runPosition(trackingPosition + trim);
      case ZERO -> io.runPosition(0.0 + trim);
    }
  }
}
