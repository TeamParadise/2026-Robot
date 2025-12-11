/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.statemachine;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class StateMachine<S extends Enum<S>> extends SubsystemBase {
  /** The name of this subsystem. */
  protected final String name;

  /** The current state that the subsystem is in. */
  private S currentState;

  /** The last time that a state change occured. */
  private double lastStateChangeTimestamp = 0.0;

  protected StateMachine(S initialState) {
    currentState = initialState;
    name = getName();
  }
}
