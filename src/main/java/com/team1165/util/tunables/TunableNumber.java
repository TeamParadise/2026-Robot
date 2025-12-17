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

/**
 * Class for a tunable number, which is a number that can be adjusted in real time while the
 * robot is running, if {@link TuningManager} is enabled, or return a static value otherwise.
 */
public class TunableNumber extends Tunable {
  private final String key;
  private NumberWrapper value;

  /**
   * Creates a new {@link TunableNumber}.
   *
   * @param key The key to put the value under in the "Tuning" key on the dashboard.
   * @param value The default value for the number.
   */
  public TunableNumber(String key, double value) {
    this.key = TuningManager.tuningKey + key;
    updateTuningMode(value);
  }

  /** Returns the current value. */
  public double get() {
    return value.get();
  }

  /**
   * Returns whether the value has changed since the last check.
   *
   * @param id Unique identifier for the caller to avoid conflicts when shared between multiple
   *     objects. The recommended approach is to pass the result of {@code hashCode()}.
   */
  public boolean hasChanged(int id) {
    return value.hasChanged(id);
  }

  /**
   * Updates the tuning mode status using the status from {@link TuningManager}.
   *
   * @param value The value to provide to the new {@link NumberWrapper}.
   */
  private void updateTuningMode(double value) {
    this.value = TuningManager.get() ? new LoggedNumberWrapper(key, value) : new StaticNumberWrapper(value);
  }

  void updateTuningMode() {
    updateTuningMode(value.get());
  }
}
