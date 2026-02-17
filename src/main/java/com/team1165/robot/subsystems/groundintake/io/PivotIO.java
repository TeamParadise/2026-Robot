/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.groundintake.io;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;
import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {

  @AutoLog
  class PivotIOInputs {
    /**
     * Data from the primary motor of the subsystem. Most of the time, any data needed should be
     * grabbed from here.
     */
    public MotorData pivotMotor = new GenericMotorData();
  }

  /**
   * Updates a {@link PivotIOInputs} instance with the latest updates from this {@link PivotIO}.
   *
   * @param inputs A {@link PivotIOInputs} instance to update.
   */
  default void updatePivotInputs(PivotIOInputs inputs) {}

  /**
   * Run the motors together at a specific voltage.
   *
   * @param voltage The voltage to run the rollers at.
   */
  default void runPivotVolts(double voltage) {}

  /**
   * Wow my first ever comment. Sets the pivot to either flipped or not flipped.
   *
   * @param pivotPosition Flipped or not, with true being flipped and false being backwards.
   */
  default void runPivotPosition(double pivotPosition) {}

  /** Sets the PID values for the pivot motor */
  default void setPivotPID(Slot0Configs configs) {}

  /** Resets the Pivot to its original position. Doesn't have any reset measures thoughhh :P :D */
  default void resetPivot() {}

  /** Stops the pivot motor (sets the output to zero). */
  default void stop() {}

  /**
   * Enables or disables brake mode on both of the roller motors.
   *
   * @param enabled Whether to enable brake mode.
   */
  default void setBrakeMode(boolean enabled) {}
}
