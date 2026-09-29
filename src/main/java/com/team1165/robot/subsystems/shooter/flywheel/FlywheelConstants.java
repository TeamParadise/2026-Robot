/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.flywheel;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team1165.robot.globalconstants.IDConstants.CANivore;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;

/** Constants for the Flywheel subsystem motor configuration. */
public class FlywheelConstants {
  public static final double defaultTolerance = 0.1; // TODO: Tune this

  public static final double currentTolerance = 0.1;

  public static final TalonFXConfiguration baseMotorConfig = new TalonFXConfiguration();

  static {
    // Current limits
    baseMotorConfig.CurrentLimits.SupplyCurrentLimit = 60;
    baseMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
    baseMotorConfig.CurrentLimits.StatorCurrentLimit = 80;
    baseMotorConfig.CurrentLimits.StatorCurrentLimitEnable = true;

    baseMotorConfig.Feedback.SensorToMechanismRatio = (1.0 / 1.5);

    baseMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

    baseMotorConfig.Slot0 =
        new Slot0Configs()
            .withKP(0.35)
            .withKI(0.0)
            .withKD(0.0)
            .withKS(0.16202)
            .withKV(0.0755)
            .withKA(0.0061278);
  }

  public static final TalonFXConfig primaryMotorConfig =
      new TalonFXConfig("FlywheelPrimary", CANivore.flywheelPrimary, CANivore.bus, baseMotorConfig);
  public static final TalonFXConfig secondaryMotorConfig =
      new TalonFXConfig(
          "FlywheelSecondary", CANivore.flywheelSecondary, CANivore.bus, baseMotorConfig);
}
