/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.flywheel;

import static com.ctre.phoenix6.Timestamp.TimestampSource.CANivore;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import com.team1165.robot.globalconstants.CANConstants.IDs.CANivore;

import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;

public class FlywheelConstants {
  public static final class Motors{


    public static final TalonFXConfiguration baseMotorConfig = new TalonFXConfiguration();

    static {
      baseMotorConfig.CurrentLimits.SupplyCurrentLimit = 60;
      baseMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

      baseMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
      baseMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;


      baseMotorConfig.Slot0 =
          new Slot0Configs()
              .withKP(10.0)
              .withKI(0.0)
              .withKD(0.0)
              .withKS(0.22)
              .withKG(0.33)
              .withKV(0.0)
              .withKA(0.0)
              .withGravityType(GravityTypeValue.Elevator_Static)
              .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign);

      baseMotorConfig.MotionMagic =
          new MotionMagicConfigs()
              .withMotionMagicCruiseVelocity(15)
              .withMotionMagicAcceleration(30);
    }

    public static final TalonFXConfig primaryMotorConfig =
        new TalonFXConfig(
            "FlywheelPrimary", CANivore.flywheelPrimary, CANivore.name, baseMotorConfig);
    public static final TalonFXConfig secondaryMotorConfig =
        new TalonFXConfig(
            "FlywheelSecondary", CANivore.flywheelSecondary, CANivore.name, baseMotorConfig);
  }

}
