/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.io.rollerpid;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

/**
 * A hardware interface/implementation layer for a basic wheel/roller subsystem powered by one motor
 * attached to a SPARK MAX/FLEX motor controller.
 */
public class RollerPIDIOSpark implements RollerPIDIO {
  private final SparkBase motor;
  private final SparkClosedLoopController controller;
  private final SparkMotorData motorData;

  public RollerPIDIOSpark(SparkConfig primaryConfig) {
    // Assign motor variable
    motor = SparkUtils.createNewSpark(primaryConfig);
    controller = motor.getClosedLoopController();

    // Create MotorData instances to log motor
    motorData = new SparkMotorData(motor, primaryConfig);
  }

  @Override
  public void updateInputs(RollerPIDIOInputs inputs) {
    // Update the motor data
    motorData.update();

    // Put the motor data values in inputs
    inputs.motor = motorData;
  }

  @Override
  public void runVolts(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void runVelocity(double velocity) {
    controller.setSetpoint(velocity, ControlType.kVelocity);
  }

  @Override
  public void setPIDF(Slot0Configs configs) {
    motor.configureAsync(
        new SparkMaxConfig().apply(SparkUtils.createClosedLoopConfig(configs)),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);
  }

  @Override
  public void stop() {
    motor.set(0);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    motor.configureAsync(
        new SparkMaxConfig().idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);
  }
}
