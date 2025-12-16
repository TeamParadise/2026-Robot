/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.statemachine.v2;

import edu.wpi.first.wpilibj2.command.Command;

public abstract class StateCommand<M extends StateMachine<M, ?>> extends Command {
  protected M subsystem;

  protected StateCommand(M subsystem) {
    addRequirements(subsystem);
  }
}
