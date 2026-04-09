/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.spindexer;

import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIOInputsAutoLogged;
import com.team1165.util.statemachine.v1.StateMachine;
import org.littletonrobotics.junction.Logger;

public class Spindexer extends StateMachine<SpindexerState> {
  private final RollerIO io;
  private final RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();

  public Spindexer(RollerIO io) {
    super(SpindexerState.IDLE);
    this.io = io;
  }

  @Override
  protected void update() {
    io.updateInputs(inputs);
    Logger.processInputs(name, inputs);
  }

  @Override
  protected void transition() {
    io.runVolts(getCurrentState().getVoltage());
  }
}
