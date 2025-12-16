/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.statemachine.v2;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class StateMachine<S extends Enum<S>> extends SubsystemBase {
  protected final String name;
  private S currentState;
  private boolean initialized = false;
  private double lastStateChangeTimestamp = 0.0;

  protected StateMachine(S initialState) {
    currentState = initialState;
    name = getName();
  }

  protected void beforeTransition(S newState) {}

  protected void afterTransition() {}

  protected void whileInState() {}

  protected void setState(S newState) {}
}
