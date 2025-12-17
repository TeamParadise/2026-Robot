/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.constants;

/**
 * Constants for {@link edu.wpi.first.wpilibj.Alert} objects, focusing on names for groups of
 * Alerts.
 */
public final class AlertConstants {
  /** Private constructor to prevent instantiation. */
  private AlertConstants() {}

  /** Debug group, used for code debugging only. */
  public static final String debug = "Debug";

  /** General group, used for issues that might show up while the robot is running. */
  public static final String general = "General";

  /** Initialization group, used for issues that might show up when the robot first turns on. */
  public static final String init = "Initialization";
}
