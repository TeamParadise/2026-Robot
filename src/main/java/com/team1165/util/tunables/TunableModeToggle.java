/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

public final class TunableModeToggle {
  private final static boolean currentStatus;
  private final static LoggedNetworkBoolean futureStatus;

  /** Static initialization to set the current tunable mode status. */
  static {
    // TODO: Actually add the code to find if tunable mode was previously enabled and enable it
    currentStatus = false;
    futureStatus = new LoggedNetworkBoolean("Tuning/Enabled", currentStatus);
  }

  /** Private in order to prevent instantization. */
  private TunableModeToggle() {

  }
}
