/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret.io;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team1165.util.logging.motordata.TalonMotorData;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceUtils;

public class TurretIOTalon implements TurretIO {
  private final TalonFX motor;
  private final TalonMotorData motorData;

  private final MotionMagicVoltage positionControl = new MotionMagicVoltage(0).withUpdateFreqHz(0);

  public TurretIOTalon(TalonFXConfig config) {
    motor = PhoenixDeviceUtils.createNewTalonFX(config);
    motorData = new TalonMotorData(motor, config);
    motor.setPosition(0);
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
    motor.setControl(positionControl.withPosition(position));
  }

  @Override
  public void resetPosition(double position) {
    motor.setPosition(position);
  }

  @Override
  public void setPIDF(Slot0Configs configs) {
    motor.getConfigurator().apply(configs);
  }

  @Override
  public void setMotionProfiling(MotionMagicConfigs configs) {
    motor.getConfigurator().apply(configs);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    // Configure motors
    motor.setNeutralMode(enabled ? NeutralModeValue.Brake : NeutralModeValue.Coast);
  }
}
