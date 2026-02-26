/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.flywheel;

import static com.team1165.robot.globalconstants.CANConstants.IDs.CANivore;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;

/** Constants for the Flywheel subsystem motor configuration. */
public class FlywheelConstants {

  public static final TalonFXConfiguration baseMotorConfig = new TalonFXConfiguration();

  static {
    // Current limits appropriate for flywheel rollers
    baseMotorConfig.CurrentLimits.SupplyCurrentLimit = 60;
    baseMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
    baseMotorConfig.CurrentLimits.StatorCurrentLimit = 80;
    baseMotorConfig.CurrentLimits.StatorCurrentLimitEnable = true;

    // Flywheel motors should coast when idle to reduce wear on the mechanism
    baseMotorConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    baseMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

    // Velocity PID gains for the flywheel — placeholder values to be tuned on the real mechanism
    baseMotorConfig.Slot0 =
        new Slot0Configs().withKP(0.1).withKI(0.0).withKD(0.0).withKS(0.0).withKV(0.12).withKA(0.0);
  }

  public static final TalonFXConfig primaryMotorConfig =
      new TalonFXConfig("FlywheelPrimary", CANivore.flywheelPrimary, CANivore.bus, baseMotorConfig);
  public static final TalonFXConfig secondaryMotorConfig =
      new TalonFXConfig(
          "FlywheelSecondary", CANivore.flywheelSecondary, CANivore.bus, baseMotorConfig);
}
