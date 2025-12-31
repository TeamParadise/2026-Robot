/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.logging.motordata;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/**
 * Class that provides easy collection of most standard values collected from a motor (controller)
 * through an IO class.
 */
public abstract class MotorData implements LoggableInputs {
  /** The applied voltage to the motor. */
  protected double appliedVolts = 0.0;

  /** Whether the motor (controller) is connected. Might be unreliable with a SPARK. */
  protected boolean connected = false;

  /** Whether a fault is currently active on the motor (controller). */
  protected boolean faultActive = false;

  /** String of the currently active faults on the motor (controller). */
  protected String faults = "";

  /** The temperature of the motor. */
  protected double motorTemperatureCelsius = 0.0;

  /**
   * The output current of the motor. This is the general output current on a SPARK and the torque
   * current on a Talon.
   */
  protected double outputCurrentAmps = 0.0;

  /**
   * The position reported by the motor (controller). Typically motor rotations, but could be
   * different depending on the specific conversion factors on the motor controller.
   */
  protected double position = 0.0;

  /** The temperature of the motor controller. Not applicable on a SPARK. */
  protected double processorTemperatureCelsius = 0.0;

  /** The supply current provided to the motor controller. */
  protected double supplyCurrentAmps = 0.0;

  /**
   * The velocity reported by the motor (controller). Typically reported in motor rotations per
   * minute, but could be different depending on the specific conversion factors on the motor
   * controller.
   */
  protected double velocity = 0.0;

  /** Bitmask used to raise the frequency of values that are actually used in the robot code. */
  private int accessed = 0;

  /**
   * Updates a LogTable with the data to log.
   *
   * @param table The table to which data should be written.
   */
  public void toLog(LogTable table) {
    table.put("AppliedVolts", appliedVolts);
    table.put("Connected", connected);
    table.put("FaultActive", faultActive);
    table.put("Faults", faults);
    table.put("MotorTemperatureCelsius", motorTemperatureCelsius);
    table.put("OutputCurrentAmps", outputCurrentAmps);
    table.put("Position", position);
    table.put("ProcessorTemperatureCelsius", processorTemperatureCelsius);
    table.put("SupplyCurrentAmps", supplyCurrentAmps);
    table.put("Velocity", velocity);
  }

  /**
   * Updates data based on a LogTable.
   *
   * @param table The table from which data should be read.
   */
  public void fromLog(LogTable table) {
    appliedVolts = table.get("AppliedVolts", appliedVolts);
    connected = table.get("Connected", connected);
    faultActive = table.get("FaultActive", faultActive);
    faults = table.get("Faults", faults);
    motorTemperatureCelsius = table.get("MotorTemperatureCelsius", motorTemperatureCelsius);
    outputCurrentAmps = table.get("OutputCurrentAmps", outputCurrentAmps);
    position = table.get("Position", position);
    processorTemperatureCelsius =
        table.get("ProcessorTemperatureCelsius", processorTemperatureCelsius);
    supplyCurrentAmps = table.get("SupplyCurrentAmps", supplyCurrentAmps);
    velocity = table.get("Velocity", velocity);
  }

  /**
   * Check if the {@link MotorField} has been accessed and update the frequency if it has not.
   *
   * @param field The {@link MotorField} to check.
   */
  private void markAccessed(MotorField field) {
    int oldAccessed = accessed;
    accessed |= (1 << field.ordinal());
    if (accessed != oldAccessed) { // if the first time being accessed
      setFrequency(field);
    }
  }

  /**
   * Set the frequency for the provided {@link MotorField}.
   *
   * @param field The {@link MotorField} to set the frequency for.
   */
  void setFrequency(MotorField field) {}

  /** Get the applied voltage to the motor. */
  public double getAppliedVolts() {
    markAccessed(MotorField.APPLIED_VOLTS);
    return appliedVolts;
  }

  /**
   * Get the applied voltage to the motor.
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public double getAppliedVolts(boolean raiseFrequency) {
    return raiseFrequency ? getAppliedVolts() : appliedVolts;
  }

  /** Get whether the motor (controller) is connected. Might be unreliable with a SPARK. */
  public boolean getConnected() {
    markAccessed(MotorField.CONNECTED);
    return connected;
  }

  /**
   * Get whether the motor (controller) is connected.
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public boolean getConnected(boolean raiseFrequency) {
    return raiseFrequency ? getConnected() : connected;
  }

  /** Get whether a fault is currently active on the motor (controller). */
  public boolean getFaultActive() {
    markAccessed(MotorField.FAULT_ACTIVE);
    return faultActive;
  }

  /**
   * Get whether a fault is currently active on the motor (controller).
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public boolean getFaultActive(boolean raiseFrequency) {
    return raiseFrequency ? getFaultActive() : faultActive;
  }

  /** Get the string of the currently active faults on the motor (controller). */
  public String getFaults() {
    markAccessed(MotorField.FAULTS);
    return faults;
  }

  /**
   * Get the string of the currently active faults on the motor (controller).
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public String getFaults(boolean raiseFrequency) {
    return raiseFrequency ? getFaults() : faults;
  }

  /** Get the temperature of the motor. */
  public double getMotorTemperatureCelsius() {
    markAccessed(MotorField.MOTOR_TEMP);
    return motorTemperatureCelsius;
  }

  /**
   * Get the temperature of the motor.
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public double getMotorTemperatureCelsius(boolean raiseFrequency) {
    return raiseFrequency ? getMotorTemperatureCelsius() : motorTemperatureCelsius;
  }

  /**
   * Get the output current of the motor. This is the general output current on a SPARK and the
   * torque current on a Talon.
   */
  public double getOutputCurrentAmps() {
    markAccessed(MotorField.OUTPUT_CURRENT);
    return outputCurrentAmps;
  }

  /**
   * Get the output current of the motor. This is the general output current on a SPARK and the
   * torque current on a Talon.
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public double getOutputCurrentAmps(boolean raiseFrequency) {
    return raiseFrequency ? getOutputCurrentAmps() : outputCurrentAmps;
  }

  /**
   * Get the position reported by the motor (controller). Typically motor rotations, but could be
   * different depending on the specific conversion factors on the motor controller.
   */
  public double getPosition() {
    markAccessed(MotorField.POSITION);
    return position;
  }

  /**
   * Get the position reported by the motor (controller). Typically motor rotations, but could be
   * different depending on the specific conversion factors on the motor controller.
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public double getPosition(boolean raiseFrequency) {
    return raiseFrequency ? getPosition() : position;
  }

  /** Get the temperature of the motor controller. Not applicable on a SPARK. */
  public double getProcessorTemperatureCelsius() {
    markAccessed(MotorField.PROCESSOR_TEMP);
    return processorTemperatureCelsius;
  }

  /**
   * Get the temperature of the motor controller. Not applicable on a SPARK.
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public double getProcessorTemperatureCelsius(boolean raiseFrequency) {
    return raiseFrequency ? getProcessorTemperatureCelsius() : processorTemperatureCelsius;
  }

  /** Get the supply current provided to the motor controller. */
  public double getSupplyCurrentAmps() {
    markAccessed(MotorField.SUPPLY_CURRENT);
    return supplyCurrentAmps;
  }

  /**
   * Get the supply current provided to the motor controller.
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public double getSupplyCurrentAmps(boolean raiseFrequency) {
    return raiseFrequency ? getSupplyCurrentAmps() : supplyCurrentAmps;
  }

  /**
   * Get the velocity reported by the motor (controller). Typically reported in motor rotations per
   * minute, but could be different depending on the specific conversion factors on the motor
   * controller.
   */
  public double getVelocity() {
    markAccessed(MotorField.VELOCITY);
    return velocity;
  }

  /**
   * Get velocity reported by the motor (controller). Typically reported in motor rotations per
   * minute, but could be different depending on the specific conversion factors on the motor
   *
   * @param raiseFrequency If true, raise the update frequency for the value.
   */
  public double getVelocity(boolean raiseFrequency) {
    return raiseFrequency ? getVelocity() : velocity;
  }
}
