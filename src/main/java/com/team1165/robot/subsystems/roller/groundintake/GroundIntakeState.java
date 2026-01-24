/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.groundintake;

import com.team1165.util.statemachine.v1.State;
import java.util.OptionalDouble;

public enum GroundIntakeState implements State {
  IDLE (false, 0),
  DEPLOY (true, 0),
  DEPLOY_AND_RUN (true, 1),
  DEPLOY_AND_REVERSE (true, -1);

  private final double voltage;
  private final boolean pivotDeployed;

  GroundIntakeState(boolean pivotDeployed, double voltage) {
    this.voltage = voltage;
    this.pivotDeployed = pivotDeployed;
  }

  @Override
  public OptionalDouble get() {
    return OptionalDouble.of(voltage);
  }
}
