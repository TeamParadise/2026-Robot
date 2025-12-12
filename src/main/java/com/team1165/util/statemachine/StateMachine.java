/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.statemachine;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Set;
import org.littletonrobotics.junction.Logger;

public abstract class StateMachine<S extends Enum<S>> extends SubsystemBase {
  /** The name of this subsystem. */
  protected final String name;

  /** The current state that the subsystem is in. */
  private S currentState;

  /** Stores if the initial state has been transitioned to. */
  private boolean initialized = false;

  /** The last time that a state change occurred. */
  private double lastStateChangeTimestamp = 0.0;

  protected StateMachine(S initialState) {
    currentState = initialState;
    name = getName();
  }

  public Command waitForState(S state) {
    return Commands.waitUntil(() -> currentState == state);
  }

  public Command waitForStates(Set<S> states) {
    return Commands.waitUntil(() -> states.contains(currentState));
  }

  public S getCurrentState() {
    return currentState;
  }

  protected void setState(S newState) {
    // Only attempt transition if the new state is not equal to the current state
    if (newState != currentState) {
      // Log the new current state
      Logger.recordOutput(name + "/CurrentState", (currentState = newState).toString());

      // Record the last state change time
      lastStateChangeTimestamp = Timer.getTimestamp();
    }
    // Perform a transition
    transition();
  }

  public boolean timeout(double duration) {
    return (Timer.getTimestamp() - lastStateChangeTimestamp) > duration;
  }

  protected abstract void transition();

  protected void update() {}
}
