/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.io.dualroller;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

/**
 * A hardware interface/implementation layer for a basic wheel/roller subsystem powered by two
 * motors attached to SPARK MAX/FLEX motor controllers. These two motors are usually controlled
 * together, but they can be controlled separately if needed.
 */
public class DualRollerIOSpark implements DualRollerIO {
  // Save motors and configs, configs are saved for brake mode configuration later
  private final SparkBase primaryMotor;
  private final SparkBase secondaryMotor;
  private final SparkBaseConfig primaryConfiguration;
  private final SparkBaseConfig secondaryConfiguration;

  // Motor data to log
  private final SparkMotorData primaryMotorData;
  private final SparkMotorData secondaryMotorData;

  public DualRollerIOSpark(SparkConfig primaryConfig, SparkConfig secondaryConfig) {
    // Assign motor variables
    primaryMotor = SparkUtils.createNewSpark(primaryConfig);
    secondaryMotor = SparkUtils.createNewSpark(secondaryConfig);

    // Assign the configurations to variables
    primaryConfiguration = primaryConfig.configuration();
    secondaryConfiguration = secondaryConfig.configuration();

    // Create MotorData instances to log motors
    primaryMotorData = new SparkMotorData(primaryMotor, primaryConfig);
    secondaryMotorData = new SparkMotorData(secondaryMotor, secondaryConfig);
  }

  /**
   * Updates a {@link DualRollerIOInputs} instance with the latest updates from this {@link
   * DualRollerIO}.
   *
   * @param inputs A {@link DualRollerIOInputs} instance to update.
   */
  @Override
  public void updateInputs(DualRollerIOInputs inputs) {
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
  @Override
  public void runVolts(double primaryVoltage, double secondaryVoltage) {
    primaryMotor.setVoltage(primaryVoltage);
    secondaryMotor.setVoltage(secondaryVoltage);
  }

  /** Stops both of the motors (sets the output to zero). */
  @Override
  public void stop() {
    primaryMotor.set(0);
    secondaryMotor.set(0);
  }

  /**
   * Enables or disables brake mode on both of the roller motors.
   *
   * @param enabled Whether to enable brake mode.
   */
  @Override
  public void setBrakeMode(boolean enabled) {
    new Thread(
            () -> {
              primaryMotor.configure(
                  primaryConfiguration.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast),
                  ResetMode.kNoResetSafeParameters,
                  PersistMode.kNoPersistParameters);
              secondaryMotor.configure(
                  secondaryConfiguration.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast),
                  ResetMode.kNoResetSafeParameters,
                  PersistMode.kNoPersistParameters);
            })
        .start();
  }
}
