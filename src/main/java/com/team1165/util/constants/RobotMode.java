/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.constants;

import edu.wpi.first.wpilibj.RobotBase;

/** Reusable class to determine the robot {@link Mode}, between REAL, SIM, and REPLAY. */
public final class RobotMode {
  /** The running mode of the robot (REAL, SIM, or REPLAY). */
  private static final Mode robotMode;

  // Static initialization block
  static {
    robotMode =
        RobotBase.isReal()
            ? Mode.REAL
            : (Boolean.getBoolean("replayMode") ? Mode.REPLAY : Mode.SIM);
  }

  /** Private constructor to prevent instantiation. */
  private RobotMode() {}

  /**
   * Get the current robot {@link Mode}.
   *
   * @return The current robot mode.
   */
  public static Mode get() {
    return robotMode;
  }

  /** The possible modes that a robot can run in. */
  public enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }
}
