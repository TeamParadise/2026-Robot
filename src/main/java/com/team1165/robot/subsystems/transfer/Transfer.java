/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.transfer;

import com.team1165.util.io.rollerpid.RollerPIDIO;
import com.team1165.util.io.rollerpid.RollerPIDIOInputsAutoLogged;
import com.team1165.util.statemachine.v1.StateMachine;
import org.littletonrobotics.junction.Logger;

public class Transfer extends StateMachine<TransferState> {
  private final RollerPIDIO io;
  private final RollerPIDIOInputsAutoLogged inputs = new RollerPIDIOInputsAutoLogged();

  public Transfer(RollerPIDIO io) {
    super(TransferState.IDLE);
    this.io = io;
  }

  @Override
  protected void update() {
    io.updateInputs(inputs);
    Logger.processInputs(name, inputs);
  }

  @Override
  protected void transition() {
    switch (getCurrentState()) {
      case IDLE -> io.stop();
      default -> io.runVelocity(getCurrentState().getVelocity());
    }
  }
}
