/*
 * Copyright (c) 2025 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.io;

import static com.team1165.util.vendor.ctre.PhoenixSignalUtils.tryUntilOk;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team1165.util.logging.motordata.TalonMotorData;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceUtils;

/**
 * A hardware interface/implementation layer for a basic wheel/roller subsystem powered by two
 * motors attached to SPARK MAX/FLEX motor controllers. These two motors are usually controlled
 * together, but they can be controlled separately if needed.
 */
public class RollerIOTalonFX implements RollerIO {
  // Save motors and configs, configs are saved for brake mode configuration later
  private final TalonFX primaryMotor;
  private final TalonFX secondaryMotor;

  // Motor data to log
  private final TalonMotorData primaryMotorData;
  private final TalonMotorData secondaryMotorData;

  public RollerIOTalonFX(TalonFXConfig primaryConfig, TalonFXConfig secondaryConfig) {
    // Assign motor variables
    primaryMotor = PhoenixDeviceUtils.createNewTalonFX(primaryConfig);
    secondaryMotor = PhoenixDeviceUtils.createNewTalonFX(secondaryConfig);
    // Assign the configurations to variables
    secondaryMotor.setControl(new Follower(primaryMotor.getDeviceID(), true));


    // Create MotorData instances to log motors
    primaryMotorData = new TalonMotorData(primaryMotor, primaryConfig);
    secondaryMotorData = new TalonMotorData(secondaryMotor, secondaryConfig);
  }

  /**
   * Updates a {@link RollerIOInputs} instance with the latest updates from this {@link RollerIO}.
   *
   * @param inputs A {@link RollerIOInputs} instance to update.
   */
  @Override
  public void updateInputs(RollerIOInputs inputs) {
    // Update the motor data
    primaryMotorData.update();
    secondaryMotorData.update();

    // Put the motor data values in inputs
    inputs.primaryMotor = primaryMotorData;
    inputs.secondaryMotor = secondaryMotorData;
  }

  /**
   * Run the motors together at a specific voltage.
   *
   * @param voltage The voltage to run the rollers at.
   */
  @Override
  public void runVolts(double voltage) {
    primaryMotor.setVoltage(voltage);
    secondaryMotor.setVoltage(voltage);
  }

  /**
   * Run the motors separately at different voltages. This should only be used if the motors are not
   * physically coupled by any means.
   *
   * @param primaryVoltage The voltage to run the primary motor at.
   * @param secondaryVoltage The voltage to run the secondary motor at.
   */
  public void runVolts(double primaryVoltage, double secondaryVoltage) {
    primaryMotor.setVoltage(primaryVoltage);
    secondaryMotor.setVoltage(secondaryVoltage);
  }

  /** Stops ONE of the motors (sets the output to zero). */
  @Override
  public void stop() {
    primaryMotor.set(0);
    secondaryMotor.set(0);
  }

  /**
   * Enables or disables brake mode on the ONE roller motor.
   *
   * @param enabled Whether to enable brake mode.
   */
  @Override
  public void setBrakeMode(boolean enabled) {
    new Thread(
        () ->
            this.primaryMotor.setNeutralMode(
                enabled ? NeutralModeValue.Brake : NeutralModeValue.Coast))
        .start();
  }
}
