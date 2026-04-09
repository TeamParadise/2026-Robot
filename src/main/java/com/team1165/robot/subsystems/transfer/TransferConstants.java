/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.transfer;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.globalconstants.IDConstants.RIO;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public final class TransferConstants {
  /** Private constructor to prevent instantiation. */
  private TransferConstants() {}

  private static final Slot0Configs gains =
      new Slot0Configs()
          .withKP(999999.0)
          .withKI(0.0)
          .withKD(0.0)
          .withKS(0.0)
          .withKV(0.0)
          .withKA(
              0.0); // TODO: Update PID values with real life values, test bang-bang
  private static final SparkBaseConfig baseConfig =
      new SparkMaxConfig()
          .apply(SparkUtils.createClosedLoopConfig(gains).minOutput(0.0))
          .apply(
              SparkUtils.createEncoderRatio(
                  11.0 / 18.0)) // TODO: Double check and make sure this ratio is right
          .apply(new EncoderConfig().uvwAverageDepth(2).uvwMeasurementPeriod(10))
          .closedLoopRampRate(0.1)
          .idleMode(IdleMode.kCoast)
          .inverted(true)
          .openLoopRampRate(0.25)
          .smartCurrentLimit(60);
  public static final SparkConfig config =
      SparkConfig.sparkMax("Transfer", RIO.transfer, MotorType.kBrushless, baseConfig);
}
