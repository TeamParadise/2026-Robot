/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

import com.team1165.util.tunables.wrappers.numbers.LoggedNumberWrapper;
import com.team1165.util.tunables.wrappers.numbers.NumberWrapper;
import com.team1165.util.tunables.wrappers.numbers.StaticNumberWrapper;
import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

public class TunableNumber extends Tunable {
  private String key;
  private NumberWrapper value;

  /**
   * Constructs a new {@link TunableNumber}.
   *
   * @param key The key to put the value under in NetworkTables and the log.
   * @param value The default value for the number.
   */
  public TunableNumber(String key, double value) {
    // Assign values
    this.key = "/Tuning/" + key;
    this.value = new StaticNumberWrapper(value);
  }

  protected void updateTuningMode() {
    value = TunableManager.get() ? new LoggedNumberWrapper(key, value.get()) : new StaticNumberWrapper(value.get());
  }
}
