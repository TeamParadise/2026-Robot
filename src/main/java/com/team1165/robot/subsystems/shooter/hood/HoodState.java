/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.hood;

import com.team1165.util.statemachine.v1.State;

public enum HoodState implements State {
  /** Stay in current position, don't command any speed. */
  IDLE(),
  /** Track values provided by ShooterManager. */
  TRACKING(),
  /** Go to the base/zero position. */
  ZERO()
}
