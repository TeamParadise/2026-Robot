/*
 * Copyright (c) 2025 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.io;

import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;
import org.littletonrobotics.junction.AutoLog;

/**
 * A hardware interface/implementation layer for a basic wheel/roller subsystem powered by ONE*
 * motors.
 * <br><br>
 * *This IO interface was formerly a 2-motor class, and so will emphasize primary or plural motors. This is a misnomer that I cannot be bothered to fix.
 */
public interface RollerIO {
  /** Class used to store the IO values of a basic roller subsystem. */
  @AutoLog
  class RollerIOInputs {
    /**
     * Data from the  motor of the subsystem. Most of the time, any data needed should be
     * grabbed from here.
     */
    public MotorData primaryMotor = new GenericMotorData();

  }

  /**
   * Updates a {@link RollerIOInputs} instance with the latest updates from this {@link RollerIO}.
   *
   * @param inputs A {@link RollerIOInputs} instance to update.
   */
  default void updateInputs(RollerIOInputs inputs) {}

  /**
   * Run the motors together at a specific voltage.
   *
   * @param voltage The voltage to run the rollers at.
   */
  default void runVolts(double voltage) {}

  /** Stops both of the motors (sets the output to zero). */
  default void stop() {}

  /**
   * Enables or disables brake mode on both of the roller motors.
   *
   * @param enabled Whether to enable brake mode.
   */
  default void setBrakeMode(boolean enabled) {}
}
