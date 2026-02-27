/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.io.dualroller;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;
import org.littletonrobotics.junction.AutoLog;

/**
 * A hardware interface/implementation layer for a basic wheel/roller subsystem powered by two
 * motors. These two motors are usually controlled together, but they can be controlled separately
 * if needed.
 */
public interface DualRollerIO {



  /** Class used to store the IO values of a basic roller subsystem. */
  @AutoLog
  class DualRollerIOInputs {
    /**
     * Data from the primary motor of the subsystem. Most of the time, any data needed should be
     * grabbed from here.
     */
    public MotorData primaryMotor = new GenericMotorData();

    /** Data from the secondary motor of the subsystem. */
    public MotorData secondaryMotor = new GenericMotorData();
  }

  /**
   * Updates a {@link DualRollerIOInputs} instance with the latest updates from this {@link
   * DualRollerIO}.
   *
   * @param inputs A {@link DualRollerIOInputs} instance to update.
   */
  default void updateInputs(DualRollerIOInputs inputs) {}

  /**
   * Run the motors together at a specific voltage.
   *
   * @param voltage The voltage to run the rollers at.
   */
  default void runVolts(double voltage) {}

  /**
   * Run the motors at a specific velocity using PID.
   *
   * @param velocity The velocity to run at.
   */
  default void runVelocity(double velocity) {}

  /**
   * Run the motors separately at different voltages. This should only be used if the motors are not
   * physically coupled by any means.
   *
   * @param primaryVoltage The voltage to run the primary motor at.
   * @param secondaryVoltage The voltage to run the secondary motor at.
   */
  default void runVolts(double primaryVoltage, double secondaryVoltage) {}

  /** Sets the PIDF values for the motors. */
  default void setPIDF(Slot0Configs configs) {}

  /** Stops both of the motors (sets the output to zero). */
  default void stop() {}

  default void setMotionProfiling(MotionMagicConfigs configs) {};
  /**
   * Enables or disables brake mode on both of the roller motors.
   *
   * @param enabled Whether to enable brake mode.
   */
  default void setBrakeMode(boolean enabled) {}
}
