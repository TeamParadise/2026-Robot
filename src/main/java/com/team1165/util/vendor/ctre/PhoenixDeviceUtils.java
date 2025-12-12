/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.vendor.ctre;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.team1165.util.constants.AlertConstants;
import com.team1165.util.constants.CANFrequency;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.CANcoderConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.CANdleConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.CANrangeConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.PigeonConfig;
import com.team1165.util.vendor.ctre.PhoenixDeviceConfigs.TalonFXConfig;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;

/** Class containing various utilities to interface with CTR Electronics Phoenix 6 devices. */
public final class PhoenixDeviceUtils {
  /** Number of times to attempt configuration before failing. */
  private static final int configAttempts = 5;

  /** Private constructor in order to prevent instantiation. */
  private PhoenixDeviceUtils() {}

  /**
   * Creates and configures a CTRE {@link CANcoder} with the provided config.
   *
   * @param config The full config for the CANcoder.
   * @return The new {@link CANcoder} created.
   */
  public static CANcoder createNewCANcoder(CANcoderConfig config) {
    // Create the CANcoder with the configuration values
    final var cancoder = new CANcoder(config.canId(), config.canBus());

    // Configure the CANcoder
    boolean success =
        PhoenixSignalUtils.tryUntilOk(
            configAttempts, () -> cancoder.getConfigurator().apply(config.configuration()));

    // Alert if the configuration was never successful
    if (!success) {
      new Alert(
              AlertConstants.init,
              "CANcoder \""
                  + config.name()
                  + "\" (ID: "
                  + config.canId()
                  + ") configuration has failed. Unexpected behavior may occur.",
              AlertType.kError)
          .set(true);
    }

    // Explicitly enable required status signals for remote sensors
    PhoenixSignalUtils.setUpdateFrequency(
        config.canBus(),
        CANFrequency.FAST,
        false,
        cancoder.getAbsolutePosition(),
        cancoder.getPosition(),
        cancoder.getVelocity(),
        cancoder.getMagnetHealth());

    // Disable unused status signals (can always be enabled later)
    cancoder.optimizeBusUtilization(0);

    return cancoder;
  }

  /**
   * Creates and configures a CTRE {@link CANdle} with the provided config.
   *
   * @param config The full config for the CANdle.
   * @return The new {@link CANdle} created.
   */
  public static CANdle createNewCANdle(CANdleConfig config) {
    // Create the CANdle with the configuration values
    final var candle = new CANdle(config.canId(), config.canBus());

    // Configure the CANdle
    boolean success =
        PhoenixSignalUtils.tryUntilOk(
            configAttempts, () -> candle.getConfigurator().apply(config.configuration()));

    // Alert if the configuration was never successful
    if (!success) {
      new Alert(
              AlertConstants.init,
              "CANdle \""
                  + config.name()
                  + "\" (ID: "
                  + config.canId()
                  + ") configuration has failed. Unexpected behavior may occur.",
              AlertType.kError)
          .set(true);
    }

    // Disable unused status signals (can always be enabled later)
    candle.optimizeBusUtilization(0);

    return candle;
  }

  /**
   * Creates and configures a CTRE {@link CANrange} with the provided config.
   *
   * @param config The full config for the CANrange.
   * @return The new {@link CANrange} created.
   */
  public static CANrange createNewCANrange(CANrangeConfig config) {
    // Create the CANrange with the configuration values
    final var canrange = new CANrange(config.canId(), config.canBus());

    // Configure the CANrange
    boolean success =
        PhoenixSignalUtils.tryUntilOk(
            configAttempts, () -> canrange.getConfigurator().apply(config.configuration()));

    // Alert if the configuration was never successful
    if (!success) {
      new Alert(
              AlertConstants.init,
              "CANrange \""
                  + config.name()
                  + "\" (ID: "
                  + config.canId()
                  + ") configuration has failed. Unexpected behavior may occur.",
              AlertType.kError)
          .set(true);
    }

    // Explicitly enable required status signals for remote sensors
    PhoenixSignalUtils.setUpdateFrequency(
        config.canBus(), CANFrequency.FAST, false, canrange.getIsDetected());

    // Disable unused status signals (can always be enabled later)
    canrange.optimizeBusUtilization(0);

    return canrange;
  }

  /**
   * Creates and configures a CTRE {@link Pigeon2} with the provided config.
   *
   * @param config The full config for the Pigeon.
   * @return The new {@link Pigeon2} created.
   */
  public static Pigeon2 createNewPigeon(PigeonConfig config) {
    // Create the Pigeon with the configuration values
    final var pigeon = new Pigeon2(config.canId(), config.canBus());

    // Configure the Pigeon
    boolean success =
        PhoenixSignalUtils.tryUntilOk(
            configAttempts, () -> pigeon.getConfigurator().apply(config.configuration()));

    // Alert if the configuration was never successful
    if (!success) {
      new Alert(
              AlertConstants.init,
              "Pigeon \""
                  + config.name()
                  + "\" (ID: "
                  + config.canId()
                  + ") configuration has failed. Unexpected behavior may occur.",
              AlertType.kError)
          .set(true);
    }

    // Explicitly enable required status signals for remote sensors
    PhoenixSignalUtils.setUpdateFrequency(
        config.canBus(),
        CANFrequency.FAST,
        false,
        pigeon.getYaw(),
        pigeon.getPitch(),
        pigeon.getRoll());

    // Disable unused status signals (can always be enabled later)
    pigeon.optimizeBusUtilization(0);

    return pigeon;
  }

  /**
   * Creates and configures a CTRE {@link TalonFX} with the provided config.
   *
   * @param config The full config for the Talon FX.
   * @return The new {@link TalonFX} created.
   */
  public static TalonFX createNewTalonFX(TalonFXConfig config) {
    // Create the Talon FX with the configuration values
    final var talon = new TalonFX(config.canId(), config.canBus());

    // Configure the Talon FX
    boolean success =
        PhoenixSignalUtils.tryUntilOk(
            configAttempts, () -> talon.getConfigurator().apply(config.configuration()));

    // Alert if the configuration was never successful
    if (!success) {
      new Alert(
              AlertConstants.init,
              "Talon FX \""
                  + config.name()
                  + "\" (ID: "
                  + config.canId()
                  + ") configuration has failed. Unexpected behavior may occur.",
              AlertType.kError)
          .set(true);
    }

    // Explicitly enable required status signals for sensors and motors that are being followed
    PhoenixSignalUtils.setUpdateFrequency(
        config.canBus(),
        CANFrequency.FAST,
        false,
        talon.getDutyCycle(),
        talon.getMotorVoltage(),
        talon.getTorqueCurrent(),
        talon.getPosition(),
        talon.getVelocity());

    // Disable unused status signals (can always be enabled later)
    talon.optimizeBusUtilization(0);

    return talon;
  }
}
