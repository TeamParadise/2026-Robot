/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.turret;

import com.team1165.util.statemachine.v1.State;
import java.util.OptionalDouble;

public enum TurretState implements State {
  IDLE(0.0),

  ROTATECW(0.5),

  ROTATECCW(-0.5);

  private final double voltage;

  TurretState(double voltage) {
    this.voltage = voltage;
  }

  @Override
  public OptionalDouble get() {
    return OptionalDouble.of(voltage);
  }
}
