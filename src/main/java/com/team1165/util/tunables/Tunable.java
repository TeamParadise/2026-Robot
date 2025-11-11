/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

/**
 * Abstract class for a tunable value, which is a value that can be adjusted in real time while the
 * robot is running, provided that {@link TuningMode} is enabled.
 */
public abstract class Tunable {

  /**
   * Updates the tuning mode status for this value.
   *
   * <p>When tuning mode is enabled for the first time, this tunable will begin logging and
   * outputting its default value and allow tuning. Disabling tuning mode will not stop
   * logging/output, but instead will just ignore any new values provided by the user.
   *
   * @param enabled {@code true} to enable tuning mode, {@code false} to disable tuning mode.
   */
  protected abstract void updateTuningMode(boolean enabled);
}
