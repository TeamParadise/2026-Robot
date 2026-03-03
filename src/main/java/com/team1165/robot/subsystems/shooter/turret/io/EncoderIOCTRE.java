/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret.io;

import com.ctre.phoenix6.hardware.CANcoder;
import com.team1165.util.constants.CANFrequency;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.CANcoderConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceUtils;
import com.team1165.util.vendor.ctre.PhoenixSignalUtils;

public class EncoderIOCTRE implements EncoderIO {
  private final CANcoder encoder;

  public EncoderIOCTRE(CANcoderConfig config) {
    encoder = PhoenixDeviceUtils.createNewCANcoder(config);

    PhoenixSignalUtils.setFrequencyAndRegister(
        config.canBus(), CANFrequency.FAST, encoder.getAbsolutePosition(), encoder.getPosition());
  }

  @Override
  public void updateInputs(EncoderIOInputs inputs) {
    // Update relative and absolute positions
    inputs.absolutePosition = encoder.getAbsolutePosition().getValueAsDouble();
    inputs.relativePosition = encoder.getPosition().getValueAsDouble();
  }

  @Override
  public void setRelativePosition(double position) {
    encoder.setPosition(position);
  }
}
