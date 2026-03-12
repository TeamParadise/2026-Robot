/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.hood;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.robot.subsystems.hood.io.HoodIO;
import com.team1165.robot.subsystems.hood.io.HoodIO.HoodIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import com.team1165.util.tunables.TunablePID;

public class Hood extends OverridableStateMachine<HoodState> {

  private final TunablePID tunablepid;
  private final HoodIO hoodIO;
  private final HoodIOInputs hoodInputs = new HoodIOInputs();

  public Hood(HoodIO hoodio, Slot0Configs slot0Configs) {
    super(HoodState.IDLE);
    this.hoodIO = hoodio;
    this.tunablepid = new TunablePID(name + "/HoodPID", slot0Configs);
  }

  public double getRollerCurrent() {
    return hoodInputs.motor.getOutputCurrentAmps();
  }

  @Override
  protected void update() {
    hoodIO.updateInputs(hoodInputs);
    if (tunablepid.hasChanged(hashCode()))
      hoodIO.setPID(tunablepid.getSlot0Configs());
  }

  @Override
  protected void transition() {
    hoodIO.runVolts(getCurrentState().getVoltage());
  }


}