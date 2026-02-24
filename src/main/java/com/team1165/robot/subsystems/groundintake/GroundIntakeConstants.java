/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.groundintake;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.signals.GravityTypeValue;
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

    public static final Slot0Configs gains =
        new Slot0Configs()
            .withKP(0)
            .withKI(0)
            .withKD(0)
            .withKS(0)
            .withKV(0)
            .withKA(0)
            .withKG(0)
            .withGravityType(GravityTypeValue.Arm_Cosine);
    public static final MotionMagicConfigs motionProfile =
        new MotionMagicConfigs().withMotionMagicAcceleration(0).withMotionMagicCruiseVelocity(0);
    private static final SparkBaseConfig baseConfig =
        new SparkMaxConfig()
            .smartCurrentLimit(40)
            .idleMode(IdleMode.kBrake)
            .apply(SparkUtils.createEncoderRatio(1.0 / 9.0));
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
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kCoast)
            .apply(SparkUtils.createEncoderRatio(14.0 / 24.0));
    public static final SparkConfig config =
        SparkConfig.sparkMax("IntakeRoller", RIO.intakeRoller, MotorType.kBrushless, baseConfig);
  }
}
