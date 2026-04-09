/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.io.rollerpid;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;
import org.littletonrobotics.junction.AutoLog;

/**
 * A hardware interface/implementation layer for a PID-controlled wheel/roller subsystem powered by
 * one motor.
 */
public interface RollerPIDIO {
  /** Class used to store the IO values of a PID-controlled roller subsystem. */
  @AutoLog
  class RollerPIDIOInputs {
    /**
     * Data from the motor of the subsystem. Most of the time, any data needed should be grabbed
     * from here.
     */
    public MotorData motor = new GenericMotorData();
  }

  /**
   * Updates a {@link RollerPIDIOInputs} instance with the latest updates from this {@link
   * RollerPIDIO}.
   *
   * @param inputs A {@link RollerPIDIOInputs} instance to update.
   */
  default void updateInputs(RollerPIDIOInputs inputs) {}

  /**
   * Run the motor at a specific voltage.
   *
   * @param voltage The voltage to run the roller at.
   */
  default void runVolts(double voltage) {}

  /**
   * Run the motor at a specific velocity using PID.
   *
   * @param velocity The velocity to run the roller at.
   */
  default void runVelocity(double velocity) {}

  /** Sets the PIDF values for the motor. */
  default void setPIDF(Slot0Configs configs) {}

  /** Stop the motor (sets the output to zero). */
  default void stop() {}

  /**
   * Enables or disables brake mode.
   *
   * @param enabled Whether to enable brake mode.
   */
  default void setBrakeMode(boolean enabled) {}
}
