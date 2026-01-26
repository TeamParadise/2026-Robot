/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.hood;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase{

  private final SparkBase motor;
  private final SparkBaseConfig motorConfiguration;
  private final SparkMotorData motorData;

  public HoodSubsystem(SparkConfig motorConfig) {
    motor = SparkUtils.createNewSpark(motorConfig);
    motorConfiguration = motorConfig.configuration();
    motorData = new SparkMotorData(motor, motorConfig);
  }

  public Command setAngle(double angle) {
    return this.runOnce(() -> {
      motor.getEncoder().setPosition(angle);
    });
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
