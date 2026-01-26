/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.turret;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.util.vendor.rev.SparkConfig;

public class TurretConstants {

  public static final SparkBaseConfig turretMotor =
      new SparkMaxConfig().smartCurrentLimit(40).idleMode(IdleMode.kBrake);

  public static final SparkConfig turretMotorConfig =
      SparkConfig.sparkMax("TurretMotor", 5, MotorType.kBrushless, turretMotor);

  // there should be sim stuff here but that's a later thing hehehe
}
