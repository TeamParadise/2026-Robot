/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.base.spindexer;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.globalconstants.IDConstants.RIO;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public final class SpindexerConstants {
  /** Private constructor to prevent instantiation. */
  private SpindexerConstants() {}

  private static final SparkBaseConfig baseConfig =
      new SparkMaxConfig()
          .apply(SparkUtils.createEncoderRatio(1.0 / 5.0))
          .idleMode(IdleMode.kCoast)
          .openLoopRampRate(0.5)
          .smartCurrentLimit(40);
  public static final SparkConfig config =
      SparkConfig.sparkMax("Spindexer", RIO.spindexer, MotorType.kBrushless, baseConfig);
}
