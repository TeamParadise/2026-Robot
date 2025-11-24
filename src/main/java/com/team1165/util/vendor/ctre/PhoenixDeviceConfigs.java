/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.vendor.ctre;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CANdleConfiguration;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

/** Class to store record-based configs for CTR Electronics Phoenix 6 devices. */
public final class PhoenixDeviceConfigs {
  /** Private constructor in order to prevent instantiation. */
  private PhoenixDeviceConfigs() {}

  /**
   * Record-based config for a CANcoder, encompassing everything needed to initialize and configure
   * the CANcoder.
   *
   * @param name The name that the CANcoder should be given in logs and alerts. This, ideally,
   *     should match the name given on the CAN bus.
   * @param canId The CAN ID of the CANcoder.
   * @param canBus The {@link CANBus} that the CANcoder is located on.
   * @param configuration The configuration to apply to the CANcoder.
   */
  public record CANcoderConfig(
      String name, int canId, CANBus canBus, CANcoderConfiguration configuration) {}

  /**
   * Record-based config for a CANdle, encompassing everything needed to initialize and configure
   * the CANdle.
   *
   * @param name The name that the CANdle should be given in logs and alerts. This, ideally, should
   *     match the name given on the CAN bus.
   * @param canId The CAN ID of the CANdle.
   * @param canBus The {@link CANBus} that the CANdle is located on.
   * @param configuration The configuration to apply to the CANdle.
   */
  public record CANdleConfig(
      String name, int canId, CANBus canBus, CANdleConfiguration configuration) {}

  /**
   * Record-based config for a CANrange, encompassing everything needed to initialize and configure
   * the CANrange.
   *
   * @param name The name that the CANrange should be given in logs and alerts. This, ideally,
   *     should match the name given on the CAN bus.
   * @param canId The CAN ID of the CANrange.
   * @param canBus The {@link CANBus} that the CANrange is located on.
   * @param configuration The configuration to apply to the CANrange.
   */
  public record CANrangeConfig(
      String name, int canId, CANBus canBus, CANrangeConfiguration configuration) {}

  /**
   * Record-based config for a Pigeon 2.0, encompassing everything needed to initialize and
   * configure the Pigeon.
   *
   * @param name The name that the Pigeon should be given in logs and alerts. This, ideally, should
   *     match the name given on the CAN bus.
   * @param canId The CAN ID of the Pigeon.
   * @param canBus The {@link CANBus} that the Pigeon is located on.
   * @param configuration The configuration to apply to the Pigeon.
   */
  public record PigeonConfig(
      String name, int canId, CANBus canBus, Pigeon2Configuration configuration) {}

  /**
   * Record-based config for a Talon FX, encompassing everything needed to initialize and configure
   * the Talon FX.
   *
   * @param name The name that the Talon FX should be given in logs and alerts. This, ideally,
   *     should match the name given on the CAN bus.
   * @param canId The CAN ID of the Talon FX.
   * @param canBus The {@link CANBus} that the Talon FX is located on.
   * @param configuration The configuration to apply to the Talon FX.
   */
  public record TalonFXConfig(
      String name, int canId, CANBus canBus, TalonFXConfiguration configuration) {}
}
