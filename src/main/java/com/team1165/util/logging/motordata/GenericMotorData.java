/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.logging.motordata;

/**
 * Generic {@link MotorData} class used for simulation and other specific use cases.
 *
 * <p>Values are manually passed in each time the {@link #update} method is called.
 */
public class GenericMotorData extends MotorData {
  /**
   * Updates the motor data using the values passed into this method. If any values do not exist (or
   * for simulation, are not simulated), and are not required for a specific subsystem, pass in
   * <code>0.0</code>.
   *
   * @param appliedVolts The value to set {@link #appliedVolts} to.
   * @param connected The value to set {@link #connected} to.
   * @param faultActive The value to set {@link #faultActive} to.
   * @param faults The value to set {@link #faults} to.
   * @param motorTemperatureCelsius The value to set {@link #motorTemperatureCelsius} to.
   * @param outputCurrentAmps The value to set {@link #outputCurrentAmps} to.
   * @param position The value to set {@link #position} to.
   * @param processorTemperatureCelsius The value to set {@link #processorTemperatureCelsius} to.
   * @param supplyCurrentAmps The value to set {@link #supplyCurrentAmps} to.
   * @param velocity The value to set {@link #velocity} to.
   */
  public void update(
      double appliedVolts,
      boolean connected,
      boolean faultActive,
      String faults,
      double motorTemperatureCelsius,
      double outputCurrentAmps,
      double position,
      double processorTemperatureCelsius,
      double supplyCurrentAmps,
      double velocity) {
    this.appliedVolts = appliedVolts;
    this.connected = connected;
    this.faultActive = faultActive;
    this.faults = faults;
    this.motorTemperatureCelsius = motorTemperatureCelsius;
    this.outputCurrentAmps = outputCurrentAmps;
    this.position = position;
    this.processorTemperatureCelsius = processorTemperatureCelsius;
    this.supplyCurrentAmps = supplyCurrentAmps;
    this.velocity = velocity;
  }

  /**
   * Updates the motor data using the values passed into this method. If any values do not exist (or
   * for simulation, are not simulated), and are not required for a specific subsystem, pass in
   * <code>0.0</code>.
   *
   * @param appliedVolts The value to set {@link #appliedVolts} to.
   * @param outputCurrentAmps The value to set {@link #outputCurrentAmps} to.
   * @param position The value to set {@link #position} to.
   * @param supplyCurrentAmps The value to set {@link #supplyCurrentAmps} to.
   * @param velocity The value to set {@link #velocity} to.
   */
  public void update(
      double appliedVolts,
      double outputCurrentAmps,
      double position,
      double supplyCurrentAmps,
      double velocity) {
    update(
        appliedVolts,
        true,
        false,
        "",
        0.0,
        outputCurrentAmps,
        position,
        0.0,
        supplyCurrentAmps,
        velocity);
  }
}
