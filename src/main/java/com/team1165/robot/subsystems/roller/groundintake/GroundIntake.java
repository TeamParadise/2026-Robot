/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.groundintake;

import com.team1165.robot.subsystems.roller.io.PivotIO;
import com.team1165.robot.subsystems.roller.io.PivotIO.PivotIOInputs;
import com.team1165.robot.subsystems.roller.io.RollerIO;
import com.team1165.robot.subsystems.roller.io.RollerIO.RollerIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import com.team1165.util.statemachine.v2.StateUtils;

public class GroundIntake extends OverridableStateMachine<GroundIntakeState> {

  private final RollerIO rollerio;
  private final RollerIOInputs rollerinputs = new RollerIOInputs();
  private final PivotIO pivotio;
  private final PivotIOInputs pivotinputs = new PivotIOInputs();

  private final EnumMap<GroundIntakeState, LoggedTunableNumber> tunableMap =
      StateUtils.createTunableNumberMap(name + "/Voltages", GroundIntakeState.class);

  public GroundIntake(RollerIO rollerio, PivotIO pivotio) {
    super(GroundIntakeState.IDLE);
    this.rollerio = rollerio;
    this.pivotio = pivotio;
  }

  public double getRollerCurrent() {
    return rollerinputs.primaryMotor.getOutputCurrentAmps();
  }
  public double getPivotCurrent() { return pivotinputs.pivotMotor.getOutputCurrentAmps(); }

  @Override
  protected void update() {
    rollerio.updateRollerInputs(rollerinputs);
    pivotio.updatePivotInputs(pivotinputs);
  }

  @Override
  protected void transition() {
    rollerio.runRollerVolts(tunableMap.get(getCurrentState()).get());
  }
}
