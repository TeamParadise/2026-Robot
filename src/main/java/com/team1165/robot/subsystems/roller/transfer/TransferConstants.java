/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.transfer;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.globalconstants.IDConstants.RIO;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public final class TransferConstants {
  /** Private constructor to prevent instantiation. */
  private TransferConstants() {}

  private static final SparkBaseConfig baseConfig =
      new SparkMaxConfig()
          .smartCurrentLimit(50)
          .idleMode(IdleMode.kCoast)
          .apply(SparkUtils.createEncoderRatio(14.0 / 24.0));
  public static final SparkConfig config =
      SparkConfig.sparkMax("IntakeRoller", RIO.intakeRoller, MotorType.kBrushless, baseConfig);
}
