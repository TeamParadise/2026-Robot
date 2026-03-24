/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.hood.io;

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

public class HoodIOSpark implements HoodIO {

  private final SparkBase motor;
  private final SparkBaseConfig configuration;
  private final SparkMotorData motorData;
  private final SparkClosedLoopController controller;

  public HoodIOSpark(SparkConfig hoodConfig) {
    motor = SparkUtils.createNewSpark(hoodConfig);

    configuration = hoodConfig.configuration();

    motorData = new SparkMotorData(motor, hoodConfig);

    controller = motor.getClosedLoopController();
  }

  @Override
  public void updateInputs(HoodIOInputs inputs) {
    motorData.update();
    inputs.motor = motorData;
  }

  @Override
  public void runVolts(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void runPosition(double hoodPosition) {
    controller.setSetpoint(hoodPosition, ControlType.kPosition);
  }

  @Override
  public void setPID(Slot0Configs configs) {
    new Thread(
            () -> {
              SparkBaseConfig tempConfig = new SparkMaxConfig();
              tempConfig
                  .closedLoop
                  .p(configs.kP)
                  .i(configs.kI)
                  .d(configs.kD)
                  .feedForward
                  .kS(configs.kS)
                  .kA(configs.kA)
                  .kV(configs.kV);
              motor.configure(
                  tempConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
            })
        .start();
  }

  @Override
  public void reset() {
    motor.getEncoder().setPosition(0);
  }

  @Override
  public void stop() {
    motor.set(0);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    new Thread(
            () -> {
              motor.configure(
                  configuration.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast),
                  ResetMode.kNoResetSafeParameters,
                  PersistMode.kNoPersistParameters);
            })
        .start();
  }
}
