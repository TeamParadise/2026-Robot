/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

import java.util.Arrays;
import java.util.stream.Stream;
import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

public final class TunableManager {
  private static final LoggedNetworkBoolean enabled =
      new LoggedNetworkBoolean("Tuning/Enabled", false);
  private static Tunable[] tunables;

  /** Private constructor to prevent instantization. */
  private TunableManager() {}

  /**
   * Returns whether the code is currently in tuning mode.
   *
   * @return If tuning mode is enabled.
   */
  public static boolean get() {
    return enabled.get();
  }

  static void registerTunables(Tunable... newTunables) {
    tunables =
        Stream.concat(Arrays.stream(tunables), Arrays.stream(newTunables))
            .distinct()
            .toArray(Tunable[]::new);
  }
}
