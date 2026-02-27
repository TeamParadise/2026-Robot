/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.hood.io;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.robot.subsystems.groundintake.io.PivotIO;
import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;

public interface HoodIO {
  class HoodIOInputs {
    /**
     * Data from the primary motor of the subsystem. Most of the time, any data needed should be
     * grabbed from here.
     */
    public MotorData motor = new GenericMotorData();
  }

  /**
   * Updates a {@link HoodIO.HoodIOInputs} instance with the latest updates from this {@link PivotIO}.
   *
   * @param inputs A {@link HoodIO.HoodIOInputs} instance to update.
   */
  default void updateInputs(HoodIO.HoodIOInputs inputs) {}

  /**
   * Run the motors together at a specific voltage.
   *
   * @param voltage The voltage to run the rollers at.
   */
  default void runVolts(double voltage) {}

  /**
   * Sets the hood to either flipped or not flipped.<br> this is definitely not my first comment
   *
   * @param hoodPosition Flipped or not, with true being flipped and false being backwards.
   */
  default void runPosition(double hoodPosition) {}

  /** Sets the PID values for the hood motor */
  default void setPID(Slot0Configs configs) {}

  /** Resets the Hood to its original position */
  default void reset() {}

  void updateHoodInputs(HoodIOInputs inputs);

  void runHoodVolts(double voltage);

  void runHoodPosition(double hoodPosition);

  void setHoodPID(Slot0Configs configs);

  void resetHood();

  /** Stops the hood motor (sets the output to zero). */
  default void stop() {}

  /**
   * Enables or disables brake mode on both of the roller motor- roller motors???
   *
   * @param enabled Whether to enable brake mode.
   */
  default void setBrakeMode(boolean enabled) {}
}