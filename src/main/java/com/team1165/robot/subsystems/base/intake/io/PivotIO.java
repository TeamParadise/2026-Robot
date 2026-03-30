/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.base.intake.io;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;
import org.littletonrobotics.junction.AutoLog;

/** A hardware interface/implementation layer for a basic pivot subsystem powered by two motors. */
public interface PivotIO {
  @AutoLog
  class PivotIOInputs {
    /**
     * Data from the primary motor of the subsystem. Most of the time, any data needed should be
     * grabbed from here.
     */
    public MotorData primaryMotor = new GenericMotorData();

    /** Data from the secondary motor of the subsystem. */
    public MotorData secondaryMotor = new GenericMotorData();
  }

  /**
   * Updates a {@link PivotIOInputs} instance with the latest updates from this {@link PivotIO}.
   *
   * @param inputs A {@link PivotIOInputs} instance to update.
   */
  default void updateInputs(PivotIOInputs inputs) {}

  /**
   * Run the motors together at a specific voltage.
   *
   * @param voltage The voltage to run the pivot at.
   */
  default void runVolts(double voltage) {}

  /**
   * Run the motors to a specific position using PID.
   *
   * @param position The position to run to.
   */
  default void runPosition(double position) {}

  /**
   * Resets the current position to a specific value.
   *
   * @param position The position to reset the position to.
   */
  default void resetPosition(double position) {}

  /** Sets the PIDF values for the motors. */
  default void setPIDF(Slot0Configs configs) {}

  /** Sets the motion profiling configuration for the motors. */
  default void setMotionProfiling(MotionMagicConfigs configs) {}

  /** Stops the pivot motors (sets the output to zero). */
  default void stop() {}

  /**
   * Enables or disables brake mode on both of the pivot motors.
   *
   * @param enabled Whether to enable brake mode.
   */
  default void setBrakeMode(boolean enabled) {}
}
