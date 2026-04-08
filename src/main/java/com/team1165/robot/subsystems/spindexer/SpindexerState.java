/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.spindexer;

import com.team1165.util.statemachine.v1.State;
import com.team1165.util.tunables.TunableNumber;

@SuppressWarnings("ImmutableEnumChecker")
public enum SpindexerState implements State {
  FAST_CW(-12.0),
  FAST_CCW(12.0),
  IDLE(0.0),
  SLOW_CW(-6.0),
  SLOW_CCW(6.0);

  private final TunableNumber voltage;

  SpindexerState(double voltage) {
    this.voltage = new TunableNumber(Spindexer.class.getName() + "/Voltages/" + name(), voltage);
  }

  public double getVoltage() {
    return voltage.get();
  }
}
