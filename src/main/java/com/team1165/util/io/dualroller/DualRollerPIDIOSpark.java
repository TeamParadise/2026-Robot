/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.io.dualroller;

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
 * A hardware interface/implementation layer for two SPARK MAX/FLEX roller motors, with the
 * secondary motor following the primary motor.
 */
public class DualRollerPIDIOSpark implements DualRollerIO {
  private final SparkBase primaryMotor;
  private final SparkBase secondaryMotor;
  private final SparkClosedLoopController primaryController;
  private final SparkMotorData primaryMotorData;
  private final SparkMotorData secondaryMotorData;

  /**
   * Creates a dual-roller SPARK IO layer.
   *
   * @param primaryConfig configuration for the primary motor
   * @param secondaryConfig configuration for the follower motor
   * @param secondaryInverted whether the follower output is inverted relative to the primary
   */
  public DualRollerPIDIOSpark(
      SparkConfig primaryConfig, SparkConfig secondaryConfig, boolean secondaryInverted) {
    primaryMotor = SparkUtils.createNewSpark(primaryConfig);
    secondaryMotor = SparkUtils.createNewSpark(secondaryConfig);
    primaryController = primaryMotor.getClosedLoopController();

    secondaryMotor.configure(
        new SparkMaxConfig().follow(primaryMotor, secondaryInverted),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);

    primaryMotorData = new SparkMotorData(primaryMotor, primaryConfig);
    secondaryMotorData = new SparkMotorData(secondaryMotor, secondaryConfig);
  }

  @Override
  public void updateInputs(DualRollerIOInputs inputs) {
    primaryMotorData.update();
    secondaryMotorData.update();

    inputs.primaryMotor = primaryMotorData;
    inputs.secondaryMotor = secondaryMotorData;
  }

  @Override
  public void runVolts(double voltage) {
    primaryMotor.setVoltage(voltage);
  }

  @Override
  public void runVelocityVoltage(double velocity) {
    primaryController.setSetpoint(velocity, ControlType.kVelocity);
  }

  @Override
  public void setPIDF(Slot0Configs configs) {
    var config = new SparkMaxConfig().apply(SparkUtils.createClosedLoopConfig(configs));
    primaryMotor.configureAsync(
        config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    secondaryMotor.configureAsync(
        config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  @Override
  public void stop() {
    primaryMotor.set(0);
    secondaryMotor.set(0);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    var config = new SparkMaxConfig().idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast);
    primaryMotor.configureAsync(
        config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    secondaryMotor.configureAsync(
        config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }
}
