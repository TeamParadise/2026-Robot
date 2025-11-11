/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

public final class TuningMode {
  private static final LoggedNetworkBoolean enabled = new LoggedNetworkBoolean("Tuning/Enabled", false);
  private static final Tunable[] tunables;

  /** Private in order to prevent instantization. */
  private TuningMode() {}

  /**
   * Returns whether the code is currently in tuning mode.
   *
   * @return If tuning mode is enabled.
   */
  public static boolean get() {
    return enabled;
  }
}
