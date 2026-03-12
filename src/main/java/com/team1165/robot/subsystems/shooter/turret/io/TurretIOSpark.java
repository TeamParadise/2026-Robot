/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret.io;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
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

public class TurretIOSpark implements TurretIO {
  private final SparkBase motor;
  private final SparkMotorData motorData;
  private final SparkClosedLoopController controller;

  public TurretIOSpark(SparkConfig config) {
    motor = SparkUtils.createNewSpark(config);
    motorData = new SparkMotorData(motor, config);
    controller = motor.getClosedLoopController();
  }

  @Override
  public void updateInputs(TurretIOInputs inputs) {
    // Update the motor data
    motorData.update();

    // Put the motor data value in inputs
    inputs.motor = motorData;
  }

  @Override
  public void runVolts(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void runPosition(double position) {
    controller.setSetpoint(position, ControlType.kMAXMotionPositionControl);
  }

  @Override
  public void resetPosition(double position) {
    motor.getEncoder().setPosition(position);
  }

  @Override
  public void setPIDF(Slot0Configs configs) {
    motor.configureAsync(
        new SparkMaxConfig().apply(SparkUtils.createClosedLoopConfig(configs)),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);
  }

  @Override
  public void setMotionProfiling(MotionMagicConfigs configs) {
    // Create temporary config
    var tempConfig = new SparkMaxConfig();

    // Apply motion config to temporary config
    tempConfig.closedLoop.apply(SparkUtils.createMotionConfig(configs));

    // Configure motor
    motor.configureAsync(
        tempConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }


  @Override
  public void setBrakeMode(boolean enabled) {
    // Configure motors
    motor.configureAsync(
        new SparkMaxConfig().idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast), ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }
}
