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
import org.littletonrobotics.junction.Logger;

public class Hood extends OverridableStateMachine<HoodState> {

  private final TunablePID tunablepid;
  private final HoodIO hoodIO;
  private final HoodIOInputs hoodInputs = new HoodIOInputs();

  /** Target position for SOTM tracking (0 to 1 where 1 is full rotation). */
  private double sotmTargetPosition = 0.0;

  public Hood(HoodIO hoodIO, Slot0Configs slot0Configs) {
    super(HoodState.IDLE);
    this.hoodIO = hoodIO;
    this.tunablepid = new TunablePID(name + "/HoodPID", slot0Configs);
  }

  /**
   * Sets the target position for Shoot On The Move tracking.
   *
   * @param position The target hood position (0 to 1 where 1 is full rotation).
   */
  public void setSotmTargetPosition(double position) {
    this.sotmTargetPosition = position;
    Logger.recordOutput(name + "/SOTMTargetPosition", position);
  }

  public double getRollerCurrent() {
    return hoodInputs.motor.getOutputCurrentAmps();
  }

  @Override
  protected void update() {
    hoodIO.updateInputs(hoodInputs);
    if (tunablepid.hasChanged(hashCode())) hoodIO.setPID(tunablepid.getSlot0Configs());
  }

  @Override
  protected void transition() {
    switch (getCurrentState()) {
      case IDLE -> hoodIO.runVolts(0.0);
      case TRACKING -> hoodIO.runPosition(sotmTargetPosition);
      case FIXED -> hoodIO.runVolts(getCurrentState().getVoltage());
    }
  }
}
