/*
 * Copyright (c) 2025 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.test;

import com.team1165.util.statemachine.v2.StateCommand;

class TestCommand extends StateCommand<TestSubsystem> {
  private final double speed;

  protected TestCommand(TestSubsystem subsystem, double speed) {
    super(subsystem);
    this.speed = speed;
  }

  @Override
  public void initialize() {
    subsystem.setSpeed(speed);
  }

  @Override
  public void execute() {

  }
}
