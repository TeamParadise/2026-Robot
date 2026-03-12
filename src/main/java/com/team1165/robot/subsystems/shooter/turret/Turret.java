/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret;

import com.team1165.robot.subsystems.shooter.turret.io.TurretIO;
import com.team1165.robot.subsystems.shooter.turret.io.TurretIO.TurretIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import java.util.EnumMap;
import org.littletonrobotics.junction.Logger;

public class Turret extends OverridableStateMachine<TurretState> {

  private final TurretIO io;
  private final TurretIOInputs inputs = new TurretIOInputs();

  private final EnumMap<TurretState, LoggedTunableNumber> tunableMap =
      StateUtils.createTunableNumberMap(name + "/Voltages", TurretState.class);

  public Turret(TurretIO io) {
    super(TurretState.IDLE);
    this.io = io;
  }

  @Override
  protected void update() {
    io.updateInputs(inputs);
    Logger.processInputs(name, inputs.turretMotor);
  }

  @Override
  protected void transition() {
    io.runVolts(tunableMap.get(getCurrentState()).get());
  }
}
