/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.transfer;

import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIO.RollerIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;

public class Transfer extends OverridableStateMachine<TransferState> {
  private final RollerIO roller;
  private final RollerIOInputs rollerInputs = new RollerIOInputs();

  public Transfer(RollerIO roller) {
    super(TransferState.IDLE);

    this.roller = roller;
  }

  @Override
  protected void update() {
    roller.updateInputs(rollerInputs);
  }

  @Override
  protected void transition() {
    roller.runVolts(getCurrentState().getVoltage());
  }
}
