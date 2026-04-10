/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.hood;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.globalconstants.IDConstants.RIO;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public final class HoodConstants {
  /** Private constructor to prevent instantiation. */
  private HoodConstants() {}

  public static final double defaultTolerance = 0.05; // TODO: Tune this

  private static final Slot0Configs gains =
      new Slot0Configs()
          .withKP(1.0)
          .withKI(0.0)
          .withKD(0.0)
          .withKS(0.0)
          .withKV(0.0)
          .withKA(0.0); // TODO: Update PID values with real life values
  private static final SparkBaseConfig baseConfig =
      new SparkMaxConfig()
          .apply(SparkUtils.createClosedLoopConfig(gains).minOutput(-0.6).maxOutput(0.6))
          // .apply(SparkUtils.createEncoderRatio(8.0 / 54.0)) // TODO: add spur gear ratio
          .idleMode(IdleMode.kBrake)
          .inverted(true)
          .smartCurrentLimit(40);
  public static final SparkConfig config =
      SparkConfig.sparkMax("Hood", RIO.hood, MotorType.kBrushless, baseConfig);
}
