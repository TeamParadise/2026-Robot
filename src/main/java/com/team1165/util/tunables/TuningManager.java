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

/** Class to manage all {@link Tunable} values. */
public final class TuningManager {
  /** Base dashboard key for all Tunables. */
  public static final String tuningKey = "/Tuning/";

  private static final LoggedNetworkBoolean enabled =
      new LoggedNetworkBoolean("Tuning/Enabled", false);
  private static boolean previous = enabled.get();
  private static Tunable[] tunables;

  /** Private constructor to prevent instantization. */
  private TuningManager() {}

  /** Returns whether the code is currently in tuning mode. */
  public static boolean get() {
    return enabled.get();
  }

  /** Updates the tuning mode across all Tunables using the current dashboard status. */
  public static void updateTuningMode() {
    if (previous != enabled.get()) {
      for (Tunable tunable : tunables) {
        tunable.updateTuningMode();
      }
      previous = enabled.get();
    }
  }

  /**
   * Registers {@link Tunable} values with the manager.
   *
   * @param newTunables The new {@link Tunable} values to register.
   */
  static void registerTunables(Tunable... newTunables) {
    tunables =
        Stream.concat(Arrays.stream(tunables), Arrays.stream(newTunables))
            .distinct()
            .toArray(Tunable[]::new);
  }
}
