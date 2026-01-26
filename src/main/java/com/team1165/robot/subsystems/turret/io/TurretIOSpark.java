/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.turret.io;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public class TurretIOSpark implements TurretIO {

  private final SparkBase turretMotor;
  private final SparkBaseConfig turretConfiguration;

  private final SparkMotorData turretMotorData;

  public TurretIOSpark(SparkConfig turretConfig) {

    turretMotor = SparkUtils.createNewSpark(turretConfig);

    turretConfiguration = turretConfig.configuration();

    turretMotorData = new SparkMotorData(turretMotor, turretConfig);
  }

  @Override
  public void updateInputs(TurretIOInputs inputs) {
    // Update the motor data
    turretMotorData.update();

    // Put the motor data values in inputs
    inputs.turretMotor = turretMotorData;
  }

  @Override
  public void runVolts(double voltage) {
    turretMotor.setVoltage(voltage);
  }

  @Override
  public void stop() {
    turretMotor.set(0);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    new Thread(
            () -> {
              turretMotor.configure(
                  turretConfiguration.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast),
                  ResetMode.kNoResetSafeParameters,
                  PersistMode.kNoPersistParameters);
            })
        .start();
  }
}
