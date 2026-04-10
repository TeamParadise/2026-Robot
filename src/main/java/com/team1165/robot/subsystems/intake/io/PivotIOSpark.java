/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.intake.io;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public class PivotIOSpark implements PivotIO {
  // Motors
  private final SparkBase primaryMotor;
  private final SparkBase secondaryMotor;

  // Motor data to log
  private final SparkMotorData primaryMotorData;
  private final SparkMotorData secondaryMotorData;

  public PivotIOSpark(SparkConfig primaryConfig, SparkConfig secondaryConfig) {
    // Assign motor variables
    primaryMotor = SparkUtils.createNewSpark(primaryConfig);
    secondaryMotor = SparkUtils.createNewSpark(secondaryConfig);

    // Make sure the secondary motor follows the primary motor
    secondaryMotor.configure(
        new SparkMaxConfig().follow(primaryMotor, true),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);

    // Create MotorData instances to log motors
    primaryMotorData = new SparkMotorData(primaryMotor, primaryConfig);
    secondaryMotorData = new SparkMotorData(secondaryMotor, secondaryConfig);
  }

  @Override
  public void updateInputs(PivotIOInputs inputs) {
    // Update the motor data
    primaryMotorData.update();
    secondaryMotorData.update();

    // Put the motor data values in inputs
    inputs.primaryMotor = primaryMotorData;
    inputs.secondaryMotor = secondaryMotorData;
  }

  @Override
  public void runVolts(double voltage) {
    primaryMotor.setVoltage(voltage);
  }

  @Override
  public void stop() {
    primaryMotor.set(0);
    secondaryMotor.set(0);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    // Create temporary config
    SparkBaseConfig tempConfig =
        new SparkMaxConfig().idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast);

    // Configure motors
    primaryMotor.configureAsync(
        tempConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    secondaryMotor.configureAsync(
        tempConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }
}
