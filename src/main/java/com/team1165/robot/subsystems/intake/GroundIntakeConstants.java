/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.intake;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.globalconstants.IDConstants.RIO;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public final class GroundIntakeConstants {
  /** Private constructor to prevent instantiation. */
  private GroundIntakeConstants() {}

  /** Pivot constants. */
  public static final class Pivot {
    private Pivot() {}

    private static final SparkBaseConfig baseConfig =
        new SparkMaxConfig()
            .apply(SparkUtils.createEncoderRatio(1.0 / 9.0))
            .idleMode(IdleMode.kBrake)
            .openLoopRampRate(0.15)
            .smartCurrentLimit(75);
    public static final SparkConfig primaryConfig =
        SparkConfig.sparkMax(
            "IntakePivotPrimary", RIO.intakePivotPrimary, MotorType.kBrushless, baseConfig);
    public static final SparkConfig secondaryConfig =
        SparkConfig.sparkMax(
            "IntakePivotSecondary", RIO.intakePivotSecondary, MotorType.kBrushless, baseConfig);
  }

  /** Roller constants. */
  public static final class Roller {
    private Roller() {}

    private static final SparkBaseConfig baseConfig =
        new SparkMaxConfig()
            .apply(SparkUtils.createEncoderRatio(12.0 / 24.0))
            .idleMode(IdleMode.kCoast)
            .inverted(true)
            .openLoopRampRate(0.15)
            .smartCurrentLimit(50);
    public static final SparkConfig config =
        SparkConfig.sparkMax("IntakeRoller", RIO.intakeRoller, MotorType.kBrushless, baseConfig);
  }
}
