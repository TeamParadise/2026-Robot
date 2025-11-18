/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

public class TunableNumber extends Tunable {
  private final String key;

  private double baseValue;
  private LoggedNetworkNumber number;
  private boolean tuningMode;

  /**
   * Constructs a new {@link TunableNumber}.
   *
   * @param key The key to put the value under in NetworkTables and the log.
   * @param value The default value for the number.
   */
  public TunableNumber(String key, double value) {
    // Assign values
    this.key = "/Tuning/" + key;
    this.baseValue = value;

    // Register tunable with TuningMode
    TuningMode.registerTunables(this);
  }

  protected void updateTuningMode(boolean enabled) {
    if (tuningMode != enabled) {
      tuningMode = enabled;

      if (tuningMode && number == null) {
        number = new LoggedNetworkNumber(key, baseValue);
      } else if (!tuningMode && number != null) {
        baseValue = number.get();
      }
    }
  }
}
