/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team1165.robot.globalconstants.IDConstants.CANivore;
import com.team1165.robot.globalconstants.IDConstants.RIO;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.CANcoderConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;

public final class TurretConstants {
  /** Private constructor to prevent instantiation. */
  private TurretConstants() {}

  public static final class Motor {
    /** Private constructor to prevent instantiation. */
    private Motor() {}

    public static final double gearRatio = (14.0 / 44.0) * (10.0 / 100.0);

    public static final Slot0Configs gains =
        new Slot0Configs()
            .withKP(1.5)
            .withKI(0)
            .withKD(0)
            .withKS(0.065663)
            .withKV(0.11673)
            .withKA(0.0022308);
    public static final MotionMagicConfigs motionProfile =
        new MotionMagicConfigs()
            .withMotionMagicAcceleration(200)
            .withMotionMagicCruiseVelocity(100);

    private static final TalonFXConfiguration baseConfig = new TalonFXConfiguration();

    static {
      baseConfig.Slot0 = gains;
      baseConfig.MotionMagic = motionProfile;

      baseConfig.CurrentLimits.StatorCurrentLimit = 60;
      baseConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    }

    public static final TalonFXConfig config =
        new TalonFXConfig("Turret", 20, CANivore.bus, baseConfig);
  }

  public static final class Encoder {
    /** Private constructor to prevent instantiation. */
    private Encoder() {}

    private static final CANcoderConfiguration baseConfig = new CANcoderConfiguration();
    public static final CANcoderConfig config =
        new CANcoderConfig("TurretEncoder", 1, RIO.bus, baseConfig);

    static {
      baseConfig.MagnetSensor.MagnetOffset = 0.0;
    }
  }
}
