/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.intake;

import com.team1165.robot.subsystems.intake.io.PivotIO;
import com.team1165.robot.subsystems.intake.io.PivotIO.PivotIOInputs;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIO.RollerIOInputs;
import com.team1165.util.statemachine.v1.StateMachine;
import edu.wpi.first.wpilibj2.command.Command;

public class GroundIntake extends StateMachine<GroundIntakeState> {
  private final RollerIO roller;
  private final RollerIOInputs rollerInputs = new RollerIOInputs();
  private final PivotIO pivot;
  private final PivotIOInputs pivotInputs = new PivotIOInputs();

  public GroundIntake(PivotIO pivot, RollerIO roller) {
    super(GroundIntakeState.IDLE);
    this.pivot = pivot;
    this.roller = roller;
  }

  public Command stateCommand(GroundIntakeState state) {
    return this.runOnce(() -> setState(state));
  }

  @Override
  protected void update() {
    pivot.updateInputs(pivotInputs);
    roller.updateInputs(rollerInputs);
  }

  @Override
  protected void transition() {
    pivot.runVolts(getCurrentState().getPivotVoltage());
    roller.runVolts(getCurrentState().getRollerVoltage());
  }
}
