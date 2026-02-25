/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.groundintake.io;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
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

  // PID/closed loop controller
  private final SparkClosedLoopController controller;

  public PivotIOSpark(SparkConfig primaryConfig, SparkConfig secondaryConfig) {
    // Assign motor variables
    primaryMotor = SparkUtils.createNewSpark(primaryConfig);
    secondaryMotor = SparkUtils.createNewSpark(secondaryConfig);

    // Make sure the secondary motor follows the primary motor
    secondaryMotor.configure(
        new SparkMaxConfig().follow(primaryMotor),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);

    // Create MotorData instances to log motors
    primaryMotorData = new SparkMotorData(primaryMotor, primaryConfig);
    secondaryMotorData = new SparkMotorData(secondaryMotor, secondaryConfig);

    // Create closed loop controller
    controller = primaryMotor.getClosedLoopController();
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
  public void runPosition(double pivotPosition) {
    controller.setSetpoint(pivotPosition, ControlType.kMAXMotionPositionControl);
  }

  @Override
  public void resetPosition(double position) {
    primaryMotor.getEncoder().setPosition(position);
    secondaryMotor.getEncoder().setPosition(position);
  }

  @Override
  public void setPIDF(Slot0Configs configs) {
    // Configure primary motor
    primaryMotor.configureAsync(
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

    // Configure primary motor
    primaryMotor.configureAsync(
        tempConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
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
