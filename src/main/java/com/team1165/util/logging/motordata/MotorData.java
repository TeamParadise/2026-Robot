/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.logging.motordata;

/**
 * Class that provides easy collection of most standard values collected from a motor (controller) through an IO class.
 */
public class MotorData {
  /** The applied voltage to the motor (controller). */
  public double appliedVolts = 0.0;

  /** Whether the motor (controller) is connected. Might be unreliable with a SPARK. */
  public boolean connected = false;

  /** Whether a fault is currently active on the motor (controller). */
  public boolean faultActive = false;

  /** String of the currently active faults on the motor (controller). */
  public String faults = "";

  /** The temperature of the motor. */
  public double motorTemperatureCelsius = 0.0;

  /**
   * The output current of the motor. This is the general output current on a SPARK and
   * the torque current on a Talon.
   */
  public double outputCurrentAmps = 0.0;

  /**
   * The position reported by the motor (controller). Typically motor rotations, but could be
   * different depending on the specific conversion factors on the motor controller.
   */
  public double position = 0.0;

  /** The temperature of the motor controller. Not applicable on a SPARK. */
  public double processorTemperatureCelsius = 0.0;

  /** The supply current provided to the motor controller. */
  public double supplyCurrentAmps = 0.0;

  /**
   * The velocity reported by the motor (controller). Typically reported in motor rotations per
   * minute, but could be different depending on the specific conversion factors on the motor
   * controller.
   */
  public double velocity = 0.0;
}
