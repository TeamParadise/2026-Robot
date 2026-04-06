/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.io.dualroller;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team1165.util.logging.motordata.TalonMotorData;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceUtils;
import edu.wpi.first.math.MathUtil;

/**
 * A hardware interface/implementation layer for a basic wheel/roller subsystem powered by two
 * motors powered by Talon FX motor controllers. These two motors are usually controlled together,
 * but they can be controlled separately if needed.
 */
public class DualRollerIOTalonFX implements DualRollerIO {
  // Save motors and configs, configs are saved for brake mode configuration later
  private final TalonFX primaryMotor;
  private final TalonFX secondaryMotor;

  // Motor data to log
  private final TalonMotorData primaryMotorData;
  private final TalonMotorData secondaryMotorData;

  // Velocity control
  private final VelocityVoltage velocityVoltage = new VelocityVoltage(0).withUpdateFreqHz(0);
  private final VelocityTorqueCurrentFOC velocityCurrent = new VelocityTorqueCurrentFOC(0).withUpdateFreqHz(0);

  public DualRollerIOTalonFX(TalonFXConfig primaryConfig, TalonFXConfig secondaryConfig) {
    // Assign motor variables
    primaryMotor = PhoenixDeviceUtils.createNewTalonFX(primaryConfig);
    secondaryMotor = PhoenixDeviceUtils.createNewTalonFX(secondaryConfig);
    // Assign the configurations to variables
    secondaryMotor.setControl(
        new Follower(primaryMotor.getDeviceID(), MotorAlignmentValue.Opposed));

    // Create MotorData instances to log motors
    primaryMotorData = new TalonMotorData(primaryMotor, primaryConfig);
    secondaryMotorData = new TalonMotorData(secondaryMotor, secondaryConfig);
  }

  /**
   * Updates a {@link RollerIOInputs} instance with the latest updates from this {@link RollerIO}.
   *
   * @param inputs A {@link RollerIOInputs} instance to update.
   */
  @Override
  public void updateInputs(DualRollerIOInputs inputs) {
    // Update the motor data
    primaryMotorData.update();
    secondaryMotorData.update();

    // Put the motor data values in inputs
    inputs.primaryMotor = primaryMotorData;
    inputs.secondaryMotor = secondaryMotorData;
  }

  /**
   * Run the motors together at a specific voltage.
   *
   * @param voltage The voltage to run the rollers at.
   */
  @Override
  public void runVolts(double voltage) {
    primaryMotor.setVoltage(voltage);
  }

  @Override
  public void runVelocity(double velocity) {
    primaryMotor.setControl(velocityCurrent.withVelocity(velocity));
  }

  @Override
  public void runBangBangVelocity(double velocity) {
    if (MathUtil.isNear(velocity, primaryMotorData.getVelocity(), velocity * 0.2));
  }

  /** Stops ONE of the motors (sets the output to zero). */
  @Override
  public void stop() {
    primaryMotor.set(0);
  }

  @Override
  public void setPIDF(Slot0Configs gains) {
    primaryMotor.getConfigurator().apply(gains);
    secondaryMotor.getConfigurator().apply(gains);
  }

  public void setMotionProfiling(MotionMagicConfigs config) {
    primaryMotor.getConfigurator().apply(config);
    secondaryMotor.getConfigurator().apply(config);
  }

  /**
   * Enables or disables brake mode on the ONE roller motor.
   *
   * @param enabled Whether to enable brake mode.
   */
  @Override
  public void setBrakeMode(boolean enabled) {
    new Thread(
            () ->
                this.primaryMotor.setNeutralMode(
                    enabled ? NeutralModeValue.Brake : NeutralModeValue.Coast))
        .start();
    new Thread(
            () ->
                this.secondaryMotor.setNeutralMode(
                    enabled ? NeutralModeValue.Brake : NeutralModeValue.Coast))
        .start();
  }
}
