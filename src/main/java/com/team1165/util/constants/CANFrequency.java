/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.constants;

import com.ctre.phoenix6.CANBus;

/** Enum to provide preset CAN bus frequencies, for both CAN FD and CAN 2.0. */
public enum CANFrequency {
  /** Fast frequency for values actually used in the code. */
  FAST(250, 100),
  /** Medium frequency for logged values. */
  MEDIUM(100, 50),
  /** Slow frequency for values like faults. */
  SLOW(25, 10);

  private final double fdFrequency;
  private final double standardFrequency;

  CANFrequency(double fdFrequency, double standardFrequency) {
    this.fdFrequency = fdFrequency;
    this.standardFrequency = standardFrequency;
  }

  /**
   * Get the frequency for the provided CAN bus, based on if it has CAN FD support.
   *
   * @param canBus The {@link CANBus} to get the frequency for.
   * @return The frequency for the provided CAN bus.
   */
  public final double getFrequency(CANBus canBus) {
    return canBus.isNetworkFD() ? fdFrequency : standardFrequency;
  }
}
