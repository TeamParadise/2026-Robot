/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.transfer;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIO.RollerIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import com.team1165.util.tunables.TunableMotionProfile;
import com.team1165.util.tunables.TunablePIDF;

public class Transfer extends OverridableStateMachine<TransferState> {
  private final RollerIO roller;
  private final RollerIOInputs rollerInputs = new RollerIOInputs();

  private final TunablePIDF pidf;
  private final TunableMotionProfile motionProfile;

  public Transfer(
      RollerIO roller, Slot0Configs gains, MotionMagicConfigs motionProfile) {
    super(TransferState.IDLE);

    this.roller = roller;
    this.pidf = new TunablePIDF(name + "Transfer/PIDF", gains);
    this.motionProfile = new TunableMotionProfile(name + "Transfer/MotionProfile", motionProfile);
  }

  @Override
  protected void update() {
    roller.updateInputs(rollerInputs);
    if (pidf.hasChanged(hashCode())) roller.setPIDF(pidf.getSlot0Configs());
    if (motionProfile.hasChanged(hashCode())) roller.setMotionProfiling(motionProfile.getConfigs());
  }

  @Override
  protected void transition() {
    roller.runVolts(getCurrentState().getVoltage());
  }

}
