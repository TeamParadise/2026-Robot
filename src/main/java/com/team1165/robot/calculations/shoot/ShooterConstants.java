/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations.shoot;

/** Constants for shooter calculations. */
public final class ShooterConstants {
  /** Conversion factor from meters to feet. */
  public static final double METERS_TO_FEET = 3.28084;

  /** The height of the hub target in feet. */
  public static final double HUB_HEIGHT_FEET = 6.0;

  /** Minimum distance from hub to shoot (in feet). */
  public static final double MIN_SHOOT_DISTANCE_FEET = 4.5;

  /** The maximum angle the hood can obtain (in degrees). */
  public static final double HOOD_MAXIMUM_ANGLE_DEG = 45;

  /** The minimum angle the hood can obtain (in degrees). */
  public static final double HOOD_MINIMUM_ANGLE_DEG = 28;

  /** Default turret height off the ground (in feet). */
  public static final double DEFAULT_TURRET_HEIGHT_FEET = 2.5;

  /** Default desired max path height for ball trajectory (in feet). */
  public static final double DEFAULT_MAX_PATH_HEIGHT_FEET = 10.0;

  private ShooterConstants() {}
}
