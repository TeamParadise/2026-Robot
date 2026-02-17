/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.groundintake;

import static com.team1165.robot.subsystems.groundintake.GroundIntakeConstants.pivotMotorConfig;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.robot.subsystems.groundintake.io.PivotIO;
import com.team1165.robot.subsystems.groundintake.io.PivotIO.PivotIOInputs;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIO.RollerIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import com.team1165.util.tunables.TunablePID;

public class GroundIntake extends OverridableStateMachine<GroundIntakeState> {

  private final TunablePID tunablepid;
  private final RollerIO rollerio;
  private final RollerIOInputs rollerinputs = new RollerIOInputs();
  private final PivotIO pivotio;
  private final PivotIOInputs pivotinputs = new PivotIOInputs();

  private final EnumMap<GroundIntakeState, LoggedTunableNumber> tunableMap =
      StateUtils.createTunableNumberMap(name + "/Voltages", GroundIntakeState.class);

  public GroundIntake(RollerIO rollerio, PivotIO pivotio, Slot0Configs slot0Configs) {
    super(GroundIntakeState.IDLE);
    this.rollerio = rollerio;
    this.pivotio = pivotio;
    this.tunablepid = new TunablePID(name + "/PivotPID", slot0Configs);
  }

  public double getRollerCurrent() {
    return rollerinputs.motor.getOutputCurrentAmps();
  }

  public double getPivotCurrent() {
    return pivotinputs.pivotMotor.getOutputCurrentAmps();
  }

  @Override
  protected void update() {
    rollerio.updateInputs(rollerinputs);
    pivotio.updatePivotInputs(pivotinputs);
    if (tunablepid.hasChanged(pivotMotorConfig.canId()))
      pivotio.setPivotPID(tunablepid.getSlot0Configs());
  }

  @Override
  protected void transition() {
    rollerio.runVolts(getCurrentState().get().getAsDouble());
    pivotio.runPivotPosition(getCurrentState().getPivotPosition().getAsDouble());
  }
}
