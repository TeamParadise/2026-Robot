/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.intake.io;

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

  /** Stops the pivot motors (sets the output to zero). */
  default void stop() {}

  /**
   * Enables or disables brake mode on both of the pivot motors.
   *
   * @param enabled Whether to enable brake mode.
   */
  default void setBrakeMode(boolean enabled) {}
}
