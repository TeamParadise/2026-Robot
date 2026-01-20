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
  ON(1.0),
  OFF(0.0),
  CUSTOM_MANUAL(0.0);

  private final double voltage;

  GroundIntakeState(double voltage) {this.voltage = voltage;}

  @Override
  public OptionalDouble get() {
    return OptionalDouble.of(voltage);
  }
}

