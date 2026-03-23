/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.groundintake;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.robot.subsystems.groundintake.io.PivotIO;
import com.team1165.robot.subsystems.groundintake.io.PivotIO.PivotIOInputs;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIO.RollerIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import com.team1165.util.tunables.TunableMotionProfile;
import com.team1165.util.tunables.TunablePIDF;

public class GroundIntake extends OverridableStateMachine<GroundIntakeState> {
  private final RollerIO roller;
  private final RollerIOInputs rollerInputs = new RollerIOInputs();
  private final PivotIO pivot;
  private final PivotIOInputs pivotInputs = new PivotIOInputs();
  private final TunablePIDF pidf;
  private final TunableMotionProfile motionProfile;

  public GroundIntake(
      PivotIO pivot, RollerIO roller, Slot0Configs gains, MotionMagicConfigs motionProfile) {
    super(GroundIntakeState.IDLE);
    this.pivot = pivot;
    this.roller = roller;
    this.pidf = new TunablePIDF(name + "Pivot/PIDF", gains);
    this.motionProfile = new TunableMotionProfile(name + "Pivot/MotionProfile", motionProfile);
  }

  @Override
  protected void update() {
    pivot.updateInputs(pivotInputs);
    roller.updateInputs(rollerInputs);
    if (pidf.hasChanged(hashCode())) pivot.setPIDF(pidf.getSlot0Configs());
    if (motionProfile.hasChanged(hashCode())) pivot.setMotionProfiling(motionProfile.getConfigs());
  }

  @Override
  protected void transition() {
    pivot.runPosition(getCurrentState().getPivotPosition());
    roller.runVolts(getCurrentState().getRollerVoltage());
  }
}
