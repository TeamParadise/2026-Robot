/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.groundintake;

import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.util.vendor.rev.SparkConfig;
import edu.wpi.first.math.system.plant.DCMotor;

public class GroundIntakeConstants {
  public static final SparkBaseConfig elmotor =
      new SparkMaxConfig().smartCurrentLimit(40).idleMode(IdleMode.kBrake);

  // Individual SPARK MAX configurations
  public static final SparkConfig primaryMotorConfig =
      SparkConfig.sparkMax("GroundIntakePrimary", 1, MotorType.kBrushless, elmotor);

  /** Sim configs that im too lazy to add rn as they "arent needed"
  public static final SimMotorConfigs simConfig =
      new SimMotorConfigs(DCMotor.getNEO(1), 1, KilogramSquareMeters.of(0.002), Volts.of(0.05));
   */
}
