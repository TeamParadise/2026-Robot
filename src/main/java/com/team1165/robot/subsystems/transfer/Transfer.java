/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.transfer;

import com.team1165.util.io.dualroller.DualRollerIO;
import com.team1165.util.io.dualroller.DualRollerIOInputsAutoLogged;
import com.team1165.util.statemachine.v1.StateMachine;
import edu.wpi.first.wpilibj2.command.Command;
import org.littletonrobotics.junction.Logger;

public class Transfer extends StateMachine<TransferState> {
  private final DualRollerIO io;
  private final DualRollerIOInputsAutoLogged inputs = new DualRollerIOInputsAutoLogged();

  public Transfer(DualRollerIO io) {
    super(TransferState.IDLE);
    this.io = io;
  }

  public Command stateCommand(TransferState state) {
    return this.runOnce(() -> setState(state));
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
      default -> io.runVolts(getCurrentState().getVelocity());
    }
  }
}
