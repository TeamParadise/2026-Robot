/*
 * Copyright (c) 2025 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.test;

import com.team1165.util.statemachine.v2.State;
import com.team1165.util.statemachine.v2.StateCommand;

public enum TestState implements State<TestSubsystem> {
  STATEONE(1.0),
  STATETWO(2.0),
  STATETHREE(3.0);

  private final  StateCommand<TestSubsystem> command;

  TestState(double speed) {
    this.command = new TestCommand(new TestSubsystem(), speed);
  }

  TestState(StateCommand<TestSubsystem> command) {
    this.command = command;
  }

  public StateCommand<TestSubsystem> getCommand() {
    return command;
  }
}
