/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.transfer;

import com.team1165.util.statemachine.v1.State;
import com.team1165.util.tunables.TunableNumber;

@SuppressWarnings("ImmutableEnumChecker")
public enum TransferState implements State {
  IDLE(0.0),
  FORWARD(12.5),
  REVERSE(-12.5);

  private final TunableNumber velocity;

  TransferState(double velocity) {
    this.velocity = new TunableNumber(Transfer.class.getName() + "/Speeds/" + name(), velocity);
  }

  public double getVelocity() {
    return velocity.get();
  }
}
