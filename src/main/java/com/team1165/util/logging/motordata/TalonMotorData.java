/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.logging.motordata;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import com.team1165.util.constants.AlertConstants;
import com.team1165.util.constants.CANFrequency;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;
import com.team1165.util.vendor.ctre.PhoenixSignalUtils;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;

/**
 * {@link MotorData} class that uses status signals from a Talon FX motor controller to log data.
 */
public class TalonMotorData extends MotorData {
  // Status signals providing the data to log
  private final StatusSignal<Voltage> appliedVoltsSignal;
  private final StatusSignal<Integer> faultFieldSignal;
  private final StatusSignal<Boolean> bootDuringEnableFaultSignal;
  private final StatusSignal<Boolean> deviceTempFaultSignal;
  private final StatusSignal<Boolean> hardwareFaultSignal;
  private final StatusSignal<Boolean> procTempFaultSignal;
  private final StatusSignal<Temperature> motorTemperatureSignal;
  private final StatusSignal<Current> outputCurrentSignal;
  private final StatusSignal<Angle> positionSignal;
  private final StatusSignal<Temperature> processorTemperatureSignal;
  private final StatusSignal<Current> supplyCurrentSignal;
  private final StatusSignal<AngularVelocity> velocitySignal;

  // Alerts to send if any issues arise with the Talon FX or motor
  private final Alert connectedAlert;
  private final Alert faultAlert;

  /** CAN bus to use for frequency configuration. */
  private final CANBus canBus;

  /** Debouncer to avoid false disconnection alerts. */
  private final Debouncer connectedDebouncer = new Debouncer(0.2, DebounceType.kFalling);

  /**
   * Creates a {@link TalonMotorData} using the specified constants.
   *
   * @param talon The {@link TalonFX} to log data from.
   * @param config The {@link TalonFXConfig} for this Talon FX. Used for name, CAN ID, and CAN bus.
   */
  public TalonMotorData(TalonFX talon, TalonFXConfig config) {
    // Get status signals from the Talon
    appliedVoltsSignal = talon.getMotorVoltage();
    faultFieldSignal = talon.getFaultField();
    bootDuringEnableFaultSignal = talon.getFault_BootDuringEnable();
    deviceTempFaultSignal = talon.getFault_DeviceTemp();
    hardwareFaultSignal = talon.getFault_Hardware();
    procTempFaultSignal = talon.getFault_ProcTemp();
    motorTemperatureSignal = talon.getDeviceTemp();
    outputCurrentSignal = talon.getTorqueCurrent();
    positionSignal = talon.getPosition();
    processorTemperatureSignal = talon.getProcessorTemp();
    supplyCurrentSignal = talon.getSupplyCurrent();
    velocitySignal = talon.getVelocity();

    // Set the default update frequency and register signals
    PhoenixSignalUtils.setFrequencyAndRegister(
        config.canBus(),
        CANFrequency.MEDIUM,
        appliedVoltsSignal,
        motorTemperatureSignal,
        outputCurrentSignal,
        positionSignal,
        processorTemperatureSignal,
        supplyCurrentSignal,
        velocitySignal);
    PhoenixSignalUtils.setFrequencyAndRegister(
        config.canBus(),
        CANFrequency.SLOW,
        faultFieldSignal,
        bootDuringEnableFaultSignal,
        deviceTempFaultSignal,
        hardwareFaultSignal,
        procTempFaultSignal);

    // Save CAN bus for updating the frequency if needed
    canBus = config.canBus();

    // Create alerts with the name and ID of the Talon FX
    connectedAlert =
        new Alert(
            AlertConstants.general,
            "Talon FX \"" + config.name() + "\" (ID: " + config.canId() + ") is disconnected!",
            AlertType.kError);
    faultAlert =
        new Alert(
            AlertConstants.general,
            "Talon FX \"" + config.name() + "\" (ID: " + config.canId() + ") has active faults!",
            AlertType.kError);
  }

  /**
   * Updates the motor data using the status signals from the Talon FX motor controller linked with
   * this instance.
   */
  public void update() {
    // Check if there are any active faults, if there are, activate an alert and save the faults
    faultAlert.set(faultActive = faultFieldSignal.getValue() != 0);
    if (faultActive) {
      faults =
          (bootDuringEnableFaultSignal.getValue() ? "BootDuringEnable " : "")
              + (deviceTempFaultSignal.getValue() ? "DeviceTemp " : "")
              + (hardwareFaultSignal.getValue() ? "Hardware " : "")
              + (procTempFaultSignal.getValue() ? "ProcTemp " : "");
    } else {
      faults = "";
    }

    // Get values from the status signals and save them
    appliedVolts = appliedVoltsSignal.getValueAsDouble();
    motorTemperatureCelsius = motorTemperatureSignal.getValueAsDouble();
    outputCurrentAmps = outputCurrentSignal.getValueAsDouble();
    position = positionSignal.getValueAsDouble();
    processorTemperatureCelsius = processorTemperatureSignal.getValueAsDouble();
    supplyCurrentAmps = supplyCurrentSignal.getValueAsDouble();
    velocity = velocitySignal.getValueAsDouble();

    // After updating everything, check if there are any reported connection issues
    connectedAlert.set(
        !(connected =
            connectedDebouncer.calculate(BaseStatusSignal.isAllGood(appliedVoltsSignal))));
  }

  @Override
  void setFrequency(MotorField field) {
    // Set the update frequency of motor field
    PhoenixSignalUtils.setUpdateFrequency(
        canBus,
        CANFrequency.FAST,
        switch (field) {
          case APPLIED_VOLTS -> appliedVoltsSignal;
          case FAULT_ACTIVE, FAULTS ->
              new StatusSignal<?>[] {
                faultFieldSignal,
                bootDuringEnableFaultSignal,
                deviceTempFaultSignal,
                hardwareFaultSignal,
                procTempFaultSignal
              };
          case MOTOR_TEMP -> motorTemperatureSignal;
          case OUTPUT_CURRENT -> outputCurrentSignal;
          case POSITION -> positionSignal;
          case PROCESSOR_TEMP -> processorTemperatureSignal;
          case SUPPLY_CURRENT -> supplyCurrentSignal;
          case VELOCITY -> velocitySignal;
        });
  }
}
