/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.groundintake;

import com.team1165.util.statemachine.v1.State;
import java.util.OptionalDouble;

public enum GroundIntakeState implements State {
  // random placeholder values used that should be changed later

  IDLE(0, 0),
  DEPLOY(200, 0),
  DEPLOY_AND_RUN(200, 1),
  DEPLOY_AND_REVERSE(200, -1);

  private final double voltage;
  private final double pivotPosition;

  GroundIntakeState(double pivotPosition, double voltage) {
    this.voltage = voltage;
    this.pivotPosition = pivotPosition;
  }

  @Override
  public OptionalDouble get() {
    return OptionalDouble.of(voltage);
  }

  public OptionalDouble getPivotPosition() {
    return OptionalDouble.of(pivotPosition);
  }
}
