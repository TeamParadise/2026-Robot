/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

/**
 * Abstract class for a tunable value, which is a value that can be adjusted in real time while the
 * robot is running, provided that {@link TuningManager} is enabled.
 */
public abstract class Tunable {
  protected Tunable() {
    // Register Tunable with TunableManager
    TuningManager.registerTunables(this);
  }

  /** Updates the tuning mode status using the status from {@link TuningManager}. */
  abstract void updateTuningMode();
}
