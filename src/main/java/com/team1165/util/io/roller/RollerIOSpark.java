/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.io.roller;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

/**
 * A hardware interface/implementation layer for a basic wheel/roller subsystem powered by one motor
 * attached to a SPARK MAX/FLEX motor controller.
 */
public class RollerIOSpark implements RollerIO {
  // Save motors and configs, configs are saved for brake mode configuration later
  private final SparkBase primaryMotor;
  private final SparkBaseConfig primaryConfiguration;

  // Motor data to log
  private final SparkMotorData primaryMotorData;

  public RollerIOSpark(SparkConfig primaryConfig) {
    // Assign motor variables
    primaryMotor = SparkUtils.createNewSpark(primaryConfig);

    // Assign the configurations to variables
    primaryConfiguration = primaryConfig.configuration();

    // Create MotorData instances to log motors
    primaryMotorData = new SparkMotorData(primaryMotor, primaryConfig);
  }

  @Override
  public void updateInputs(RollerIOInputs inputs) {
    // Update the motor data
    primaryMotorData.update();

    // Put the motor data values in inputs
    inputs.motor = primaryMotorData;
  }

  @Override
  public void runVolts(double voltage) {
    primaryMotor.setVoltage(voltage);
  }

  @Override
  public void stop() {
    primaryMotor.set(0);
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
  public void setBrakeMode(boolean enabled) {
    new Thread(
            () -> {
              primaryMotor.configure(
                  primaryConfiguration.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast),
                  ResetMode.kNoResetSafeParameters,
                  PersistMode.kNoPersistParameters);
            })
        .start();
  }
}
